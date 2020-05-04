/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.service;

import com.baopdh.chat.repository.ProfileRepository;
import com.baopdh.thrift.gen.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    
    public User getUser(int key) {
        return profileRepository.getUser(key);
    }

    public int getKey() {
        return profileRepository.getKey();
    }

    public boolean saveUser(int key, User user) {
        return profileRepository.saveUser(key, user);
    }

    public boolean removeUser(int key) {
        return profileRepository.removeUser(key);
    }
}
