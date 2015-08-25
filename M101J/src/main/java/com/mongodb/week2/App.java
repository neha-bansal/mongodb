package com.mongodb.week2;

import org.bson.BsonDocument;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App {

	public static void main(String[] args) {
		MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase testDb = client.getDatabase("test");
		MongoCollection<User> testColl = testDb.getCollection("testColl", User.class);
		System.err.println(testColl.count());
		client.close();
		//testDb.createCollection("1");
	}

}
