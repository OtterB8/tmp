/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.model.socket;

/**
 *
 * @author admin
 */
public class ChatMessageResponse {
    private int id;
    private int sender;
    private String roomId;
    private String text;
    private long timeStamp;

    public ChatMessageResponse() {}
    
    public ChatMessageResponse(int id, int sender, String roomId, String text, long timeStamp) {
        this.id = id;
        this.sender = sender;
        this.roomId = roomId;
        this.text = text;
        this.timeStamp = timeStamp;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    
    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
