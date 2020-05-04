/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.service;

import com.baopdh.chat.repository.TrackingRepository;
import com.baopdh.chat.util.LoadBalancer;
import com.baopdh.thrift.gen.Tracking;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class TrackingService {
    private static final int NUM_LOCK = 128;
    
    private LoadBalancer lockLoadBalancer;
    private ReentrantLock[] lock;
    
    @Autowired
    private TrackingRepository trackingRepository;
    
    public TrackingService() {
        lockLoadBalancer = new LoadBalancer(NUM_LOCK);
        lock = new ReentrantLock[NUM_LOCK];
        for (int i = 0; i < NUM_LOCK; ++i) {
            lock[i] = new ReentrantLock();
        }
    }
    
    public Tracking get(String key) {
        return trackingRepository.get(key);
    }
    
    public boolean put(String key, Tracking tracking) {
        return trackingRepository.put(key, tracking);
    }
    
    public boolean addList(String key, int element) {
        int lockIndex = getLockIndex(key);
        lock[lockIndex].lock();
        try {
            Tracking tracking = trackingRepository.get(key);
            if (tracking == null)
                tracking = new Tracking(new LinkedList<>());

            tracking.getKeys().add(element);

            return trackingRepository.put(key, tracking);
        } finally {
            lock[lockIndex].unlock();
        }
    }
    
    public boolean removeLast(String key) {
        int lockIndex = getLockIndex(key);
        lock[lockIndex].lock();
        try {
            Tracking tracking = trackingRepository.get(key);
            if (tracking == null)
                return false;

            List<Integer> keys = tracking.getKeys();
            keys.remove(keys.size() - 1);

            return trackingRepository.put(key, tracking);
        } finally {
            lock[lockIndex].unlock();
        }
    }
    
    private int getLockIndex(String key) {
        return lockLoadBalancer.getIndex(key.getBytes());
    }
}
