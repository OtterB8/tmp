/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.controller;

import com.baopdh.chat.model.ChatBox;
import com.baopdh.chat.model.ChatListResponse;
import com.baopdh.chat.model.socket.ChatMessageRequest;
import com.baopdh.chat.model.CustomPrincipal;
import com.baopdh.chat.model.socket.ChatMessageResponse;
import com.baopdh.chat.model.socket.ChatMessageResponseBody;
import com.baopdh.chat.model.socket.ErrorResponseBody;
import com.baopdh.chat.service.MessageService;
import com.baopdh.chat.service.RoomService;
import com.baopdh.chat.service.TrackingService;
import com.baopdh.chat.util.LoadBalancer;
import com.baopdh.thrift.gen.Message;
import com.baopdh.thrift.gen.Room;
import com.baopdh.thrift.gen.Tracking;
import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author admin
 */
@Controller
public class ChatController {
    private static final int NUM_LOCK = 128;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private TrackingService trackingService;
    
    private final LoadBalancer lockLoadBalancer;
    private final ReentrantLock[] lock;
    
    public ChatController() {
        lockLoadBalancer = new LoadBalancer(NUM_LOCK);
        lock = new ReentrantLock[NUM_LOCK];
        for (int i = 0; i < NUM_LOCK; ++i) {
            lock[i] = new ReentrantLock();
        }
    }
    
    @MessageMapping("/chatting")
    public void handleMessage(ChatMessageRequest chatMessage, Principal principal) {
//        CustomPrincipal customPrincipal = (CustomPrincipal)((Authentication)principal).getPrincipal();
        // check if sender id is valid here

        int lockIndex = getLockIndex(chatMessage.getRoomId());
        lock[lockIndex].lock();
        try {
            long timeStamp = System.currentTimeMillis();

            // save message
            int messageKey =
                    messageService.saveMessage(chatMessage.getMessage(), chatMessage.getSender(), timeStamp);
            if (messageKey == -1) {
                sendError(String.valueOf(chatMessage.getSender()), "Server error!");
                return;
            }

            // save room info (last message, time stamp)
            RoomService.PreviousRoomInfo prevInfo = roomService.saveRoom(chatMessage.getRoomId(),
                    chatMessage.getSender(), chatMessage.getReceiver(),
                    chatMessage.getMessage(), timeStamp);
            if (prevInfo == null) {
                messageService.removeMessage(messageKey);
                sendError(String.valueOf(chatMessage.getSender()), "Server error!");
                return;
            }

            // push tracking
            if (prevInfo.getTimeStamp() == 0) { // if room is new
                if (!trackingService.addList("U" + chatMessage.getSender(), Integer.valueOf(chatMessage.getRoomId()))) {
                    messageService.removeMessage(messageKey);
                    roomService.rollBack(chatMessage.getRoomId(),
                            chatMessage.getSender(), chatMessage.getReceiver(), prevInfo);
                    sendError(String.valueOf(chatMessage.getSender()), "Server error!");
                    return;
                }
                if (chatMessage.getSender() != chatMessage.getReceiver()
                        && !trackingService.addList("U" + chatMessage.getReceiver(), Integer.valueOf(chatMessage.getRoomId()))) {
                    messageService.removeMessage(messageKey);
                    roomService.rollBack(chatMessage.getRoomId(),
                            chatMessage.getSender(), chatMessage.getReceiver(), prevInfo);
                    trackingService.removeLast("U" + chatMessage.getSender());
                    sendError(String.valueOf(chatMessage.getSender()), "Server error!");
                    return;
                }
            }
            if (!trackingService.addList("R" + chatMessage.getRoomId(), messageKey)) {
                messageService.removeMessage(messageKey);
                roomService.rollBack(chatMessage.getRoomId(),
                        chatMessage.getSender(), chatMessage.getReceiver(), prevInfo);
                sendError(String.valueOf(chatMessage.getSender()), "Server error!");
                return;
            }

            ChatMessageResponseBody res = createMessageResponse(messageKey, chatMessage.getSender(), chatMessage.getRoomId(), chatMessage.getMessage(), timeStamp);
            messagingTemplate.convertAndSendToUser(String.valueOf(chatMessage.getSender()),
                    "/queue/message", res);
            if (chatMessage.getSender() != chatMessage.getReceiver())
                messagingTemplate.convertAndSendToUser(String.valueOf(chatMessage.getReceiver()),
                        "/queue/message", res);
        } finally {
            lock[lockIndex].unlock();
        }
    }
    
    private void sendError(String user, String message) {
        messagingTemplate.convertAndSendToUser(user, "/queue/message", new ErrorResponseBody(message));
    }
    
    private int getLockIndex(String key) {
        return lockLoadBalancer.getIndex(key.getBytes());
    }

    private ChatMessageResponseBody createMessageResponse(int id, int sender, String roomId, String text, long timeStamp) {
        ChatMessageResponse chatMessageResponse = new ChatMessageResponse();
        chatMessageResponse.setId(id);
        chatMessageResponse.setSender(sender);
        chatMessageResponse.setRoomId(roomId);
        chatMessageResponse.setText(text);
        chatMessageResponse.setTimeStamp(timeStamp);
        
        return new ChatMessageResponseBody(chatMessageResponse);    
    }
    
    @GetMapping("/messages")
    @ResponseBody
    public List<ChatMessageResponse> getMessages(@RequestParam String id) {
        Tracking profileTracking = trackingService.get("R" + id);
        if (profileTracking == null)
            return new LinkedList<>();
        
        List<Integer> keys = profileTracking.getKeys();
        
        List<ChatMessageResponse> res = new LinkedList<>();
        for (int key: keys) {
            Message message = messageService.getMessage(key);
            if (message != null) {
                res.add(new ChatMessageResponse(key, message.getSender(),
                                                id, message.getText(),
                                                message.getTimestamp()));
            }
        }
        
        return res;
    }
    
    @GetMapping("/chatlist")
    @ResponseBody
    public ChatListResponse getBoxes(Principal principal) {
        CustomPrincipal customPrincipal = (CustomPrincipal)((Authentication) principal).getPrincipal();
        int id = customPrincipal.getId();
        
        Tracking tracking = trackingService.get("U" + id);
        if (tracking == null)
            return new ChatListResponse();
        
        List<Integer> tmp = tracking.getKeys();
        List<String> keys = new LinkedList<>();
        for (int key: tmp) {
            keys.add(String.valueOf(key));
        }
        
        Map<String, ChatBox> map = new HashMap<>();
        for (String key: keys) {
            Room room = roomService.getRoom(key);
            map.put(key,
                new ChatBox(key, room.getUser1(), room.getUser2(),
                        room.getLastMessage(), room.getTimestamp()));
        }
        
        return new ChatListResponse(map, keys);
    }
}
