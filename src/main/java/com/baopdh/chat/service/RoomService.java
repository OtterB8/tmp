/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.service;

import com.baopdh.chat.repository.RoomRepository;
import com.baopdh.thrift.gen.Room;
import java.net.SocketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class RoomService {
    public class PreviousRoomInfo {
        String lastMessage;
        long timeStamp;

        public PreviousRoomInfo(String lastMessage, long timeStamp) {
            this.lastMessage = lastMessage;
            this.timeStamp = timeStamp;
        }
        
        public String getLastMessage() {
            return lastMessage;
        }

        public void setLastMessage(String lastMessage) {
            this.lastMessage = lastMessage;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }
    }
    
    @Autowired
    private RoomRepository roomRepository;
    
    public String getKey() {
        return roomRepository.getKey();
    }
    
    public boolean saveRoom(String key, Room room) {
        return roomRepository.saveRoom(key, room);
    }
    
    public PreviousRoomInfo saveRoom(String key, int user1, int user2, String lastMessage, long timeStamp) {
        try {
            PreviousRoomInfo prev = new PreviousRoomInfo("", 0);
            Room room = roomRepository.getRoom(key);
            if (room == null) {
                room = new Room(user1, user2, lastMessage, timeStamp);
            } else {
                prev.setLastMessage(room.getLastMessage());
                prev.setTimeStamp(room.getTimestamp());
                room.setLastMessage(lastMessage);
                room.setTimestamp(timeStamp);
            }

            if (roomRepository.saveRoom(key, room))
                return prev;
        } catch (SocketException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean rollBack(String key, int user1, int user2, PreviousRoomInfo prev) {
        Room room = new Room(user1, user2, prev.getLastMessage(), prev.getTimeStamp());
        return roomRepository.saveRoom(key, room);
    }
    
    public boolean removeRoom(String key) {
        return roomRepository.removeRoom(key);
    }
    
    public Room getRoom(String key) {
        try {
            return roomRepository.getRoom(key);
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }
    }
}
