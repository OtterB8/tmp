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
public class ChatMessageResponseBody {
    private final int type = SocketResponseType.MESSAGE;
    private ChatMessageResponse body;
    
    public ChatMessageResponseBody(ChatMessageResponse body) {
        this.body = body;
    }
    
    public int getType() {
        return type;
    }

    public ChatMessageResponse getBody() {
        return body;
    }

    public void setBody(ChatMessageResponse body) {
        this.body = body;
    }
    
}
