/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.service;

import com.baopdh.chat.repository.MessageRepository;
import com.baopdh.thrift.gen.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    
    public int getKey() {
        return messageRepository.getKey();
    }
    
    public boolean saveMessage(int key, Message message) {
        return messageRepository.saveMessage(key, message);
    }
    
    public int saveMessage(String text, int sender, long timeStamp) {
        int key = messageRepository.getKey();
        if (key == -1)
            return -1;
        
        if (!messageRepository.saveMessage(key, new Message(text, sender, timeStamp))) {
            return -1;
        }
        
        return key;
    }
    
    public boolean removeMessage(int key) {
        return messageRepository.removeMessage(key);
    }
    
    public Message getMessage(int key) {
        return messageRepository.getMessage(key);
    }
}
