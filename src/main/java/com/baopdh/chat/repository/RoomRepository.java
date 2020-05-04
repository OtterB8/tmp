/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.repository;

import com.baopdh.chat.connectionpooling.CustomConnectionPool;
import com.baopdh.chat.connectionpooling.connection.RoomConnection;
import com.baopdh.thrift.gen.Room;
import java.net.SocketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public class RoomRepository {
    @Autowired
    @Qualifier("roomPool")
    private CustomConnectionPool connectionPool;
    
    public String getKey() {
        RoomConnection roomConnection = (RoomConnection) connectionPool.getConnection();
        try {
            if (roomConnection == null)
                return null;
            return roomConnection.getKey();
        } finally {
            connectionPool.releaseConnection(roomConnection);
        }
    }
    
    public boolean saveRoom(String key, Room room) {
        RoomConnection roomConnection = (RoomConnection) connectionPool.getConnection();
        try {
            if (roomConnection == null)
                return false;
            return roomConnection.put(key, room);
        } finally {
            connectionPool.releaseConnection(roomConnection);
        }
    }
    
    public boolean removeRoom(String key) {
        RoomConnection roomConnection = (RoomConnection) connectionPool.getConnection();
        try {
            if (roomConnection == null)
                return false;
            return roomConnection.delete(key);
        } finally {
            connectionPool.releaseConnection(roomConnection);
        }
    }
    
    public Room getRoom(String key) throws SocketException {
        RoomConnection roomConnection = (RoomConnection) connectionPool.getConnection();
        Room room = null;
        
        try {
            if (roomConnection != null)
                room = roomConnection.get(key);
        } catch(SocketException exception) {
            throw new SocketException();
        } finally {
            connectionPool.releaseConnection(roomConnection);
        }
        
        return room;
    }
}
