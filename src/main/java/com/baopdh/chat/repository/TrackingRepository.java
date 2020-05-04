/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.repository;

import com.baopdh.chat.connectionpooling.CustomConnectionPool;
import com.baopdh.chat.connectionpooling.connection.TrackingConnection;
import com.baopdh.thrift.gen.Tracking;
import java.net.SocketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public class TrackingRepository {
    @Autowired
    @Qualifier("trackingPool")
    private CustomConnectionPool connectionPool;
    
    public Tracking get(String key) {
        TrackingConnection trackingConnection = (TrackingConnection) connectionPool.getConnection();
        Tracking tracking = null;
        
        try {
            if (trackingConnection != null)
                tracking = trackingConnection.get(key);
        } catch(SocketException exception) {
            exception.printStackTrace();
        } finally {
            connectionPool.releaseConnection(trackingConnection);
        }
        
        return tracking;
    }
    
    public boolean put(String key, Tracking tracking) {
        TrackingConnection trackingConnection = (TrackingConnection) connectionPool.getConnection();
        try {
            if (trackingConnection == null)
                return false;
            return trackingConnection.put(key, tracking);
        } finally {
            connectionPool.releaseConnection(trackingConnection);
        }
    }
}
