/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.connectionpooling;

import com.baopdh.chat.connectionpooling.connection.Connection;

/**
 *
 * @author cpu60019
 */
public interface IConnectionPool {
    void setHost(String host);
    void setPort(String port);
    void setPoolSize(int poolSize);
    Connection getConnection();
    boolean releaseConnection(Connection connection);
    boolean initPool();
}
