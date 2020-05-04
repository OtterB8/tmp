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
public class ErrorResponseBody {
    private final int type = SocketResponseType.ERROR;
    private String message;
    
    public ErrorResponseBody(String message) {
        this.message = message;
    }
    
    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
