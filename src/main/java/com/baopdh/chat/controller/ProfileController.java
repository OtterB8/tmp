/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.controller;

import com.baopdh.chat.constants.ResponseMessage;
import com.baopdh.chat.model.CustomPrincipal;
import com.baopdh.chat.model.PublicProfile;
import com.baopdh.chat.model.SignupRequest;
import com.baopdh.chat.model.UserProfile;
import com.baopdh.chat.model.UsersResponse;
import com.baopdh.chat.model.socket.NewUserResponseBody;
import com.baopdh.chat.repository.OnlineUsers;
import com.baopdh.chat.service.ProfileService;
import com.baopdh.chat.service.TrackingService;
import com.baopdh.chat.service.UserIdService;
import com.baopdh.thrift.gen.Tracking;
import com.baopdh.thrift.gen.User;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cpu60019
 */
@RestController
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserIdService userIdService;
    @Autowired
    private TrackingService trackingService;
    @Autowired
    private OnlineUsers onlineUsers;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    
    @GetMapping("/users")
    @ResponseBody
    public UsersResponse getUsers() {
        Tracking profileTracking = trackingService.get("profile");
        if (profileTracking == null)
            return new UsersResponse();
        
        List<Integer> keys = profileTracking.getKeys();
        
        Map<Integer, PublicProfile> map = new HashMap<>();
        for (int key: keys) {
            User user = profileService.getUser(key);
            map.put(key, createPublicProfile(key, user));
        }
        
        return new UsersResponse(keys, map);
    }
    
    @GetMapping("/user")
    @ResponseBody
    public UserProfile getUser(Principal principal) {
        CustomPrincipal customPrincipal = (CustomPrincipal)((Authentication) principal).getPrincipal();
        int id = customPrincipal.getId();
        
        User user = profileService.getUser(id);
        
        UserProfile userProfile = new UserProfile();
        userProfile.setId(id);
        userProfile.setName(user.getUserName());
        userProfile.setEmail(user.getEmail());
        userProfile.setPhone(user.getPhone());
        userProfile.setAvatar(user.getAvatar());
        
        return userProfile;
    }
    
    @PostMapping(path = "/signup")
    ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
        if (userIdService.isUserExist(signupRequest.getUsername()))
            return new ResponseEntity<>(ResponseMessage.USER_ALREADY_EXISTED, HttpStatus.CONFLICT);
        
        int key = profileService.getKey();
        if (key == -1)
            return new ResponseEntity<>(ResponseMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        
        User user = this.createUser(signupRequest);
        if (!profileService.saveUser(key, user))
            return new ResponseEntity<>(ResponseMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        
        if (!userIdService.saveUserId(signupRequest.getUsername(), key) ) {
            profileService.removeUser(key);
            return new ResponseEntity<>(ResponseMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        if (!trackingService.addList("profile", key)) {
            profileService.removeUser(key);
            userIdService.removeUserId(signupRequest.getUsername());
            return new ResponseEntity<>(ResponseMessage.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        messagingTemplate.convertAndSend("/topic/newuser",
                new NewUserResponseBody(new PublicProfile(key, signupRequest.getUsername())));
        
        return new ResponseEntity<>(ResponseMessage.REQUEST_SUCCESS, HttpStatus.OK);
    }

    private User createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setUserName(signupRequest.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setPhone(signupRequest.getPhone());
        return user;
    }

    private PublicProfile createPublicProfile(int key, User user) {
        PublicProfile publicProfile = new PublicProfile();
        publicProfile.setId(key);
        publicProfile.setName(user.getUserName());
        publicProfile.setAvatar(user.getAvatar());
        publicProfile.setOnline(onlineUsers.hasUser(key));
        return publicProfile;
    }
}
