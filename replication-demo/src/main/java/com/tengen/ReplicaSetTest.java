package com.tengen;

import java.net.UnknownHostException;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoSocketReadException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;

public class ReplicaSetTest {

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        MongoClient client = new MongoClient(Arrays.asList(
                new ServerAddress("localhost", 27017),
                new ServerAddress("localhost", 27018),
                new ServerAddress("localhost", 27019)));//,
//                MongoClientOptions.builder().requiredReplicaSetName("r11").build());
        

        MongoCollection<Document> test = client.getDatabase("course").getCollection("replica.test");
        test.drop();

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
        	try {
	            test.insertOne(new Document("_id", i));
	            System.out.println("Inserted document: " + i);
        	} catch (MongoSocketReadException ex){
        		System.out.println("Exception while inserting document: " + i);
        	}
            Thread.sleep(500);
        }
        
        client.close();
    }
}
