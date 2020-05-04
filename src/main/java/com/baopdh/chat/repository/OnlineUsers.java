/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.repository;

import com.baopdh.chat.util.LoadBalancer;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.stereotype.Component;

/**
 *
 * @author admin
 */
@Component
public class OnlineUsers {
    private static final int NUM_LOCK = 128;
    private final LoadBalancer lockLoadBalancer;
    private final ReentrantLock[] lock;
    
    Map<Integer, Integer> map = new HashMap<>();
    
    public OnlineUsers() {
        lockLoadBalancer = new LoadBalancer(NUM_LOCK);
        lock = new ReentrantLock[NUM_LOCK];
        for (int i = 0; i < NUM_LOCK; ++i) {
            lock[i] = new ReentrantLock();
        }
    }
    
    public void addUser(int id) {
        int lockIndex = getLockIndex(id);
        lock[lockIndex].lock();
        try {
            map.put(id, map.getOrDefault(id, 0) + 1);
        } finally {
            lock[lockIndex].unlock();
        }
    }
    
    public void removeUser(int id) {
        int lockIndex = getLockIndex(id);
        lock[lockIndex].lock();
        try {
            int count = map.get(id);
            if (count == 1)
                map.remove(id);
            else
                map.put(id, count - 1);
        } finally {
            lock[lockIndex].unlock();
        }
    }
    
    public boolean hasUser(int id) {
        int lockIndex = getLockIndex(id);
        lock[lockIndex].lock();
        try {
            return map.containsKey(id);
        } finally {
            lock[lockIndex].unlock();
        }
    }
    
    private int getLockIndex(int id) {
        return lockLoadBalancer.getIndex(ByteBuffer.allocate(4).putInt(id).array());
    }
}
