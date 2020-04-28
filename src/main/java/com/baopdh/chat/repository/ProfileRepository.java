/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.repository;

import com.baopdh.chat.connectionpooling.CustomConnectionPool;
import com.baopdh.chat.connectionpooling.connection.ProfileConnection;
import com.baopdh.thrift.gen.User;
import java.net.SocketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cpu60019
 */
@Repository
public class ProfileRepository {
    @Autowired
    @Qualifier("profilePool")
    private CustomConnectionPool connectionPool;
    
    public int getKey() {
        ProfileConnection profileConnection = (ProfileConnection) connectionPool.getConnection();
        try {
            if (profileConnection == null)
                return -1;
            return profileConnection.getKey();
        } finally {
            connectionPool.releaseConnection(profileConnection);
        }
    }
    
    public boolean saveUser(int key, User value) {
        ProfileConnection profileConnection = (ProfileConnection) connectionPool.getConnection();
        try {
            if (profileConnection == null)
                return false;
            return profileConnection.put(key, value);
        } finally {
            connectionPool.releaseConnection(profileConnection);
        }
    }
    
    public boolean removeUser(int key) {
        ProfileConnection profileConnection = (ProfileConnection) connectionPool.getConnection();
        try {
            if (profileConnection == null)
                return false;
            return profileConnection.delete(key);
        } finally {
            connectionPool.releaseConnection(profileConnection);
        }
    }
    
    public User getUser(int key) {
        ProfileConnection profileConnection = (ProfileConnection) connectionPool.getConnection();
        User user = null;
        
        try {
            if (profileConnection != null)
                user = profileConnection.get(key);
        } catch(SocketException exception) {
            exception.printStackTrace();
        }
        
        connectionPool.releaseConnection(profileConnection);
        
        return user;
    }
}
