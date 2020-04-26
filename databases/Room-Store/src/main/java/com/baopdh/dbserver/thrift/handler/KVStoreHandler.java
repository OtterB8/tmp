package com.baopdh.dbserver.thrift.handler;

import com.baopdh.dbserver.DatabaseAccessLayer;
import com.baopdh.dbserver.keygen.KeyGenerate;
import com.baopdh.dbserver.profiler.ApiList;
import com.baopdh.thrift.gen.*;

public class KVStoreHandler implements RoomStoreService.Iface {

    private final DatabaseAccessLayer<String, Room> databaseAccessLayer;
    private final ApiList apiList = ApiList.getInstance();

    public KVStoreHandler(String dbName) {
        databaseAccessLayer
                = new DatabaseAccessLayer<>(dbName, KeyGenerate.TYPE.STRING, Room.class);
        databaseAccessLayer.start();
    }

    @Override
    public void ping() {
        System.out.println("Ping");
    }

    @Override
    public Room get(String id) {
        apiList.addPendingRequest(ApiList.API.GET);
        long start = System.currentTimeMillis();
        try {
            return databaseAccessLayer.get(id);
        } finally {
            apiList.saveNewRequest(ApiList.API.GET, System.currentTimeMillis() - start);
        }
    }

    @Override
    public boolean remove(String id) {
        apiList.addPendingRequest(ApiList.API.DELETE);
        long start = System.currentTimeMillis();
        try {
            return databaseAccessLayer.remove(id);
        } finally {
            apiList.saveNewRequest(ApiList.API.DELETE, System.currentTimeMillis() - start);
        }
    }

    @Override
    public boolean put(String id, Room room) {
        apiList.addPendingRequest(ApiList.API.PUT);
        long start = System.currentTimeMillis();
        try {
            return databaseAccessLayer.put(id, room);
        } finally {
            apiList.saveNewRequest(ApiList.API.PUT, System.currentTimeMillis() - start);
        }
    }

    @Override
    public String getKey() {
        return databaseAccessLayer.getKey();
    }
}
