/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.config.connectionpool;

import com.baopdh.chat.connectionpooling.CustomConnectionPool;
import com.baopdh.chat.connectionpooling.connection.UserProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CustomConnectionPoolConfig {
    
    @Bean(name="userProfilePool")
    @Scope("singleton")
    CustomConnectionPool userProfilePool() {
        CustomConnectionPool customConectionPool = new CustomConnectionPool(UserProfile.class);
        customConectionPool.setHost("localhost");
        customConectionPool.setPort("9090");
        customConectionPool.setPoolSize(4);
        if (!customConectionPool.initPool()) {
            System.err.println("Initialize userProfilePool failed");
        }
        return customConectionPool;
    }
}
