/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public class ChatListResponse {
    private Map<String, ChatBox> room;
    private List<String> list;

    public ChatListResponse() {
        room = new HashMap<>();
        list = new LinkedList<>();
    }
    
    public ChatListResponse(Map<String, ChatBox> room, List<String> list) {
        this.room = room;
        this.list = list;
    }

    public Map<String, ChatBox> getRoom() {
        return room;
    }

    public void setRoom(Map<String, ChatBox> room) {
        this.room = room;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
