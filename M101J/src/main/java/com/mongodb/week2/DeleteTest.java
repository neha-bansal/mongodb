package com.mongodb.week2;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.week2.helpers.Helpers;

public class DeleteTest {

	public static void main(String[] args) {
		MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase testDb = client.getDatabase("test");
		MongoCollection<Document> crudTest = testDb.getCollection("crudTest");
		crudTest.drop();
		
		for (int i=0; i<10; i++) {
			crudTest.insertOne(new Document().append("_id", "Smith" + i)
										.append("age", 30 + i)
										.append("profession", "programmer" + i)); 
		}
		
		//crudTest.deleteOne(Filters.gt("age", 35)); 	//deletes the first one that matches the criteria
		
		crudTest.deleteMany(Filters.gt("age", 35)); 
		
		System.out.println("================= Find all with iteration:");
		MongoCursor<Document> cursor = crudTest.find()
										.iterator();
		try {
			while(cursor.hasNext()) {
				Helpers.printJson(cursor.next(), false);
			}
		} finally {
			cursor.close();
		}
		client.close();

	}

}
