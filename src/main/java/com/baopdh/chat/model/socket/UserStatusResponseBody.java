/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.model.socket;

import com.baopdh.chat.constants.SocketResponseType;

/**
 *
 * @author admin
 */
public class UserStatusResponseBody {
    private final int type = SocketResponseType.USER_STATUS;
    private int id;
    private boolean online;

    public int getType() {
        return type;
    }

    public UserStatusResponseBody(int id, boolean online) {
        this.id = id;
        this.online = online;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
