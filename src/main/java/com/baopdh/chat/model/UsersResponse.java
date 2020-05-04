/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public class UsersResponse {
    private Map<Integer, PublicProfile> user;
    private List<Integer> list;

    public UsersResponse() {
        user = new HashMap<>();
        list = new LinkedList<>();
    }
    
    public UsersResponse(List<Integer> list, Map<Integer, PublicProfile> user) {
        this.list = list;
        this.user = user;
    }
    
    public Map<Integer, PublicProfile> getUser() {
        return user;
    }

    public void setUser(Map<Integer, PublicProfile> user) {
        this.user = user;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }
    
    
}
