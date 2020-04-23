/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.connectionpooling.connection;

import java.net.SocketException;

/**
 *
 * @author cpu60019
 */
public interface Connection<K, V> {
    boolean open(String host, String port);
    void ping();
    boolean delete(K key);
    K getKey();
    V get(K key) throws SocketException;
    boolean put(K key, V value);
}
