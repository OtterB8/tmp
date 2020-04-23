/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.connectionpooling;

import com.baopdh.chat.connectionpooling.connection.Connection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CustomConnectionPool implements IConnectionPool {
    private String host;
    private String port;
    private int poolSize;
    private ConcurrentLinkedQueue<Connection> pool;
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
        this.poolSize = poolSize;
        this.pool = new ConcurrentLinkedQueue<>();
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
            connection = this.connectionClass.newInstance();
            return connection.open(this.host, this.port) ? connection : null;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public synchronized boolean releaseConnection(Connection connection) {
        if (this.pool == null || connection == null)
            return false;

        // not thread-safe yet
        if (this.pool.size() < this.poolSize) {
            this.pool.add(connection);
        }
        
        return true;
    }

    @Override
    public boolean initPool() {
        if (this.connectionClass == null && this.pool.size() > 0)
            return false;
        
        for (int i = 0; i < poolSize; ++i) {
            try {
                Connection connection = this.connectionClass.newInstance();
                if (!connection.open(this.host, this.port)) {
                    return false;
                }
                this.pool.add(connection);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }
        
        return true;
    }
}
