/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.util;

import org.apache.commons.codec.digest.MurmurHash3;

/**
 *
 * @author admin
 */
public class LoadBalancer {
    private final int size;

    public LoadBalancer(int size) {
        this.size = size;
    }

    public int getIndex(byte[] key) {
        int res = 0;

        if (key != null)
            res = MurmurHash3.hash32x86(key);

        if (res < 0)
            res = -res;

        return res % this.size;
    }
}