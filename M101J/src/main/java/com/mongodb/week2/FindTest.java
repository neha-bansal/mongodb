package com.mongodb.week2;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.week2.helpers.Helpers;

public class FindTest {

	public static void main(String[] args) {
		MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase testDb = client.getDatabase("test");
		MongoCollection<Document> crudTest = testDb.getCollection("crudTest");
		crudTest.drop();
		
		for (int i=0; i<10; i++) {
			crudTest.insertOne(new Document().append("name", "Smith" + i)
										.append("age", 30 + i)
										.append("profession", "programmer" + i)); 
		}
		System.out.println("Find One:");
		Document doc = crudTest.find().first();
		Helpers.printJson(doc, true);
		System.out.println(doc);
		
		System.out.println("Find all with into:");
		List<Document> docList = crudTest.find().into(new ArrayList<Document>());
		for (Document d : docList) {
			Helpers.printJson(d, true);
		}
		System.out.println("================= Find all with iteration:");
		MongoCursor<Document> cursor = crudTest.find().iterator();
		try {
			while(cursor.hasNext()) {
				Helpers.printJson(cursor.next(), true);
			}
		} finally {
			cursor.close();
		}
		System.out.println("Count:");
		System.out.println(crudTest.count());
		
		client.close();

	}

}
