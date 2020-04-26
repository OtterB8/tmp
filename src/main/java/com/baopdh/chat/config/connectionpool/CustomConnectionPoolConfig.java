/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.config.connectionpool;

import com.baopdh.chat.connectionpooling.CustomConnectionPool;
import com.baopdh.chat.connectionpooling.connection.MessageConnection;
import com.baopdh.chat.connectionpooling.connection.ProfileConnection;
import com.baopdh.chat.connectionpooling.connection.RoomConnection;
import com.baopdh.chat.connectionpooling.connection.TrackingConnection;
import com.baopdh.chat.connectionpooling.connection.UserIdConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CustomConnectionPoolConfig {
    
    @Bean(name="profilePool")
    @Scope("singleton")
    CustomConnectionPool profilePool() {
        CustomConnectionPool customConectionPool = new CustomConnectionPool(ProfileConnection.class);
        customConectionPool.setHost("localhost");
        customConectionPool.setPort("10001");
        customConectionPool.setPoolSize(20);
        if (!customConectionPool.initPool()) {
            System.err.println("Initialize profilePool failed");
        }
        return customConectionPool;
    }
    
    @Bean(name="messagePool")
    @Scope("singleton")
    CustomConnectionPool messagePool() {
        CustomConnectionPool customConectionPool = new CustomConnectionPool(MessageConnection.class);
        customConectionPool.setHost("localhost");
        customConectionPool.setPort("10002");
        customConectionPool.setPoolSize(20);
        if (!customConectionPool.initPool()) {
            System.err.println("Initialize messagePool failed");
        }
        return customConectionPool;
    }
    
    @Bean(name="roomPool")
    @Scope("singleton")
    CustomConnectionPool roomPool() {
        CustomConnectionPool customConectionPool = new CustomConnectionPool(RoomConnection.class);
        customConectionPool.setHost("localhost");
        customConectionPool.setPort("10003");
        customConectionPool.setPoolSize(20);
        if (!customConectionPool.initPool()) {
            System.err.println("Initialize roomPool failed");
        }
        return customConectionPool;
    }
    
    @Bean(name="trackingPool")
    @Scope("singleton")
    CustomConnectionPool trackingPool() {
        CustomConnectionPool customConectionPool = new CustomConnectionPool(TrackingConnection.class);
        customConectionPool.setHost("localhost");
        customConectionPool.setPort("10004");
        customConectionPool.setPoolSize(20);
        if (!customConectionPool.initPool()) {
            System.err.println("Initialize trackingPool failed");
        }
        return customConectionPool;
    }
    
    @Bean(name="userIdPool")
    @Scope("singleton")
    CustomConnectionPool userIdPool() {
        CustomConnectionPool customConectionPool = new CustomConnectionPool(UserIdConnection.class);
        customConectionPool.setHost("localhost");
        customConectionPool.setPort("10005");
        customConectionPool.setPoolSize(20);
        if (!customConectionPool.initPool()) {
            System.err.println("Initialize userIdPool failed");
        }
        return customConectionPool;
    }
}
