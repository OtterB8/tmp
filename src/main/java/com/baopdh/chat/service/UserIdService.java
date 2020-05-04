/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.service;

import com.baopdh.chat.repository.UserIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class UserIdService {
    @Autowired
    private UserIdRepository userIdRepository;
    
    public int getUserId(String username) {
        return userIdRepository.getUserId(username);
    }

    public boolean isUserExist(String username) {
        return this.getUserId(username) != -1;
    }

    public boolean saveUserId(String username, int key) {
        return userIdRepository.saveUserId(username, key);
    }

    public boolean removeUserId(String username) {
        return userIdRepository.removeUserId(username);
    }
}
