/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.controller;

import com.baopdh.chat.model.UserProfile;
import com.baopdh.chat.repository.UserProfileRepository;
import com.baopdh.chat.thrift.gen.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cpu60019
 */
@RestController
public class UserProfileController {
    @Autowired
    private UserProfileRepository userProfileRepository;
    
    @PostMapping("/user")
    @ResponseBody
    public boolean saveUser() {
        return true;
    }
    
    @GetMapping("/user")
    public UserProfile getUser(@RequestParam int id) {
        User user = userProfileRepository.getUser(id);
        
        UserProfile userProfile = new UserProfile();
        userProfile.setName(user.getUserName());
        userProfile.setEmail(userProfile.getEmail());
        userProfile.setPhone(user.getPhone());
        
        return userProfile;
    }
}
