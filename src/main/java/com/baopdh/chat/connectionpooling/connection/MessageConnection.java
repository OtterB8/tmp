/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baopdh.chat.connectionpooling.connection;

import com.baopdh.thrift.gen.Message;
import com.baopdh.thrift.gen.MessageStoreService;
import java.net.SocketException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author admin
 */
public class MessageConnection implements Connection<Integer, Message> {
    private TTransport transport;
    private MessageStoreService.Client client;
    private String host;
    private String port;

    @Override
    public boolean open(String host, String port) {
        this.host = host;
        this.port = port;
        
        try {
            transport = new TFramedTransport(new TSocket(host, Integer.valueOf(port)));
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            client = new MessageStoreService.Client(protocol);
        } catch (TException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public void ping() {
        try {
            client.ping();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            return client.remove(id);
        } catch (TException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Integer getKey() {
        try {
            return client.getKey();
        } catch (TException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean put(Integer id, Message message) {
        try {
            return client.put(id, message);
        } catch (TException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Message get(Integer id) throws SocketException {
        try {
            return client.get(id);
        } catch (TException e) {
            System.out.println("Client: " + e.getMessage());
            if (!this.open(this.host, this.port)) {
                throw new SocketException();
            }
            return null;
        }
    }

    public void close() {
        if (transport != null)
            transport.close();
    }
}

