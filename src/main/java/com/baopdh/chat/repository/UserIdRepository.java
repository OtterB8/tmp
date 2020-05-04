/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.repository;

import com.baopdh.chat.connectionpooling.CustomConnectionPool;
import com.baopdh.chat.connectionpooling.connection.UserIdConnection;
import com.baopdh.thrift.gen.UserIdentity;
import java.net.SocketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public class UserIdRepository {
    @Autowired
    @Qualifier("userIdPool")
    private CustomConnectionPool connectionPool;
    
    public int getUserId(String username) {
        UserIdConnection userIdConnection = (UserIdConnection) connectionPool.getConnection();
        UserIdentity userIdentity = null;
        
        try {
            if (userIdConnection != null)
                userIdentity = userIdConnection.get(username);
        } catch(SocketException exception) {
            exception.printStackTrace();
        } finally {
            connectionPool.releaseConnection(userIdConnection);
        }
        
        return userIdentity == null ? -1 : userIdentity.getKey();
    }
    
    public boolean saveUserId(String username, int id) {
        UserIdConnection userIdConnection = (UserIdConnection) connectionPool.getConnection();
        try {
            if (userIdConnection == null)
                return false;
            return userIdConnection.put(username, new UserIdentity(id));
        } finally {
            connectionPool.releaseConnection(userIdConnection);
        }
    }
    
    public boolean removeUserId(String username) {
        UserIdConnection userIdConnection = (UserIdConnection) connectionPool.getConnection();
        try {
            if (userIdConnection == null)
                return false;
            return userIdConnection.delete(username);
        } finally {
            connectionPool.releaseConnection(userIdConnection);
        }
    }
}
