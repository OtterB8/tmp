/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.dbserver;

import com.baopdh.dbserver.profiler.ProfilerServer;
import com.baopdh.dbserver.thrift.handler.KVStoreHandler;
import com.baopdh.dbserver.util.ConfigGetter;
import com.baopdh.thrift.gen.UserIdStoreService;
import org.apache.thrift.server.TServer;

import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;

/**
 *
 * @author cpu60019
 */
public class Main {
    public static KVStoreHandler kvStoreHandler;
    public static UserIdStoreService.Processor<?> processor;

    public static void main(String[] args) {
        // start http profiler server
        ProfilerServer profilerServer = new ProfilerServer();
        System.out.println("Starting profiler server");
        if (!profilerServer.start())
            System.out.println("Profiler server failed to start");
        else
            System.out.println("Profiler server is running");

        // start thrift database server
        try {
            kvStoreHandler = new KVStoreHandler(ConfigGetter.get("db.name", "Storage"));
            processor = new UserIdStoreService.Processor<>(kvStoreHandler);
            
//            TServerTransport serverTransport = new TServerSocket(9090);
            TNonblockingServerTransport serverTransport =
                    new TNonblockingServerSocket(ConfigGetter.getInt("db.port", 10001));

//             TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
            TServer server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the database server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error staIface rting database server: " + e.getMessage());
        }
    }
}
