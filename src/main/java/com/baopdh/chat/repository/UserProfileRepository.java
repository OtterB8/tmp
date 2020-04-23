/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.repository;

import com.baopdh.chat.connectionpooling.CustomConnectionPool;
import com.baopdh.chat.connectionpooling.connection.UserProfile;
import com.baopdh.chat.thrift.gen.User;
import java.net.SocketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cpu60019
 */
@Repository
public class UserProfileRepository {
    @Autowired
    @Qualifier("userProfilePool")
    private CustomConnectionPool connectionPool;
    
    public boolean saveUser(int key, User value) {
        UserProfile userProfile = (UserProfile) connectionPool.getConnection();
        try {
            if (userProfile == null)
                return false;
            return userProfile.put(key, value);
        } finally {
            connectionPool.releaseConnection(userProfile);
        }
    }
    
    public User getUser(int key) {
        UserProfile userProfile = (UserProfile) connectionPool.getConnection();
        User user = null;
        try {
            if (userProfile != null)
                user = userProfile.get(key);
        } catch(SocketException exception) {
            exception.printStackTrace();
            return null;
        }
        
        connectionPool.releaseConnection(userProfile);
        
        return user;
    }
}
