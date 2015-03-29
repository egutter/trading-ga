package com.egutter.trading.repository;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.UnknownHostException;

/**
 * Created by egutter on 3/29/15.
 */
public class MongoRepository {

    protected DB conn(String dbName, String uriEnv) {
        String dbUri = System.getenv().get(uriEnv);
        if (dbUri != null) {
            return externalConn(dbName, dbUri);
        }
        return localConn(dbName);
    }

    protected DB externalConn(String dbName, String dbUri) {
        try {
            MongoClientURI uri = new MongoClientURI(dbUri);
            MongoClient client = new MongoClient(uri);
            return client.getDB(dbName);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    protected DB localConn(String dbName) {
        try {
            MongoClient client = new MongoClient();
            return client.getDB(dbName);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
