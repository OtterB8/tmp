/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.controller;

import com.baopdh.chat.constants.ResponseMessage;
import com.baopdh.chat.model.SignupRequest;
import com.baopdh.chat.model.UserProfile;
import com.baopdh.chat.repository.ProfileRepository;
import com.baopdh.chat.repository.UserIdRepository;
import com.baopdh.thrift.gen.User;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cpu60019
 */
@RestController
public class ProfileController {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserIdRepository userIdRepository;
    
    @GetMapping("/info")
    public UserProfile getUser(@RequestParam int id) {
        User user = profileRepository.getUser(id);
        
        UserProfile userProfile = new UserProfile();
        userProfile.setName(user.getUserName());
        userProfile.setEmail(userProfile.getEmail());
        userProfile.setPhone(user.getPhone());
        
        return userProfile;
    }
    
    @PostMapping(path = "/signup")
    ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
        if (userIdRepository.getId(signupRequest.getUsername()) != -1)
            return new ResponseEntity<>(ResponseMessage.USER_ALREADY_EXISTED, HttpStatus.CONFLICT);
        
        int key = profileRepository.getKey();
        if (key == -1)
            return new ResponseEntity<>(ResponseMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        
        User user = this.createUser(signupRequest);
        if (!profileRepository.saveUser(key, user))
            return new ResponseEntity<>(ResponseMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        
        if (!userIdRepository.saveUserId(signupRequest.getUsername(), key) ) {
            profileRepository.removeUser(key);
            return new ResponseEntity<>(ResponseMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
            
        return new ResponseEntity<>(ResponseMessage.REQUEST_SUCCESS, HttpStatus.OK);
    }

    private User createUser(SignupRequest signupRequest) {
        System.out.println(signupRequest.getUsername() + " " + signupRequest.getPassword() + " " + signupRequest.getPhone() + " " + signupRequest.getEmail());
        User user = new User();
        user.setUserName(signupRequest.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setPhone(signupRequest.getPhone());
        return user;
    }
}
