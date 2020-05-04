/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.model.socket;

import com.baopdh.chat.constants.SocketResponseType;
import com.baopdh.chat.model.PublicProfile;

/**
 *
 * @author admin
 */
public class NewUserResponseBody {
    private final int type = SocketResponseType.NEW_USER;
    private PublicProfile user;
    
    public NewUserResponseBody(PublicProfile newUser) {
        this.user = newUser;
    }

    public int getType() {
        return type;
    }

    public PublicProfile getUser() {
        return user;
    }

    public void setUser(PublicProfile user) {
        this.user = user;
    }
    
}
