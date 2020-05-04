/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.repository;

import com.baopdh.chat.connectionpooling.CustomConnectionPool;
import com.baopdh.chat.connectionpooling.connection.MessageConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.baopdh.thrift.gen.Message;
import java.net.SocketException;

/**
 *
 * @author admin
 */
@Repository
public class MessageRepository {
    @Autowired
    @Qualifier("messagePool")
    private CustomConnectionPool connectionPool;
    
    public int getKey() {
        MessageConnection messageConnection = (MessageConnection) connectionPool.getConnection();
        try {
            if (messageConnection == null)
                return -1;
            return messageConnection.getKey();
        } finally {
            connectionPool.releaseConnection(messageConnection);
        }
    }
    
    public boolean saveMessage(int key, Message message) {
        MessageConnection messageConnection = (MessageConnection) connectionPool.getConnection();
        try {
            if (messageConnection == null)
                return false;
            return messageConnection.put(key, message);
        } finally {
            connectionPool.releaseConnection(messageConnection);
        }
    }
    
    public boolean removeMessage(int key) {
        MessageConnection messageConnection = (MessageConnection) connectionPool.getConnection();
        try {
            if (messageConnection == null)
                return false;
            return messageConnection.delete(key);
        } finally {
            connectionPool.releaseConnection(messageConnection);
        }
    }
    
    public Message getMessage(int key) {
        MessageConnection messageConnection = (MessageConnection) connectionPool.getConnection();
        Message message = null;
        
        try {
            if (messageConnection != null)
                message = messageConnection.get(key);
        } catch(SocketException exception) {
            exception.printStackTrace();
        } finally {
            connectionPool.releaseConnection(messageConnection);
        }
        
        return message;
    }
}
