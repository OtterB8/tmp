/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.connectionpooling;

import com.baopdh.chat.connectionpooling.connection.Connection;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class CustomConnectionPool implements IConnectionPool {
    private String host;
    private String port;
    private BlockingQueue<Connection> pool;
    private final Class<? extends Connection> connectionClass;
    
    public CustomConnectionPool(Class<? extends Connection> connectionClass) {
        this.connectionClass = connectionClass;
    }
    
    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public void setPoolSize(int poolSize) {
        this.pool = new LinkedBlockingDeque<>(poolSize);
    }
    
    @Override
    public Connection getConnection() {
        if (this.pool == null)
            return null;
        
        Connection connection = this.pool.poll();
        if (connection != null)
            return connection;
        
        //create new connection if there is none available
        try {
            connection = this.connectionClass.getDeclaredConstructor().newInstance();
            return connection.open(this.host, this.port) ? connection : null;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        if (this.pool == null || connection == null)
            return false;

        this.pool.offer(connection);
        return true;
    }

    @Override
    public boolean initPool() {
        if (this.connectionClass == null || this.pool == null)
            return false;
        
        try {
            while (true) {
                Connection connection = this.connectionClass.getDeclaredConstructor().newInstance();
                if (!connection.open(this.host, this.port)) {
                    return false;
                }
                if (!this.pool.offer(connection))
                    break;
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
}
