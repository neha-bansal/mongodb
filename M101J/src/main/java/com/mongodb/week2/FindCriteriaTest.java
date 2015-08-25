package com.mongodb.week2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.week2.helpers.Helpers;

public class FindCriteriaTest {

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
		
		//filter using Document
		Document filter = new Document("name", "Smith0").append("age", new Document("$lt",35));
		
		//Document filter1 = new Document("$or", Arrays.asList(new Document("name", "Smith0"), new Document("age", new Document("$lt",35))));
		
		
		//filter using Filters API provided by driver
		Bson filter1 = Filters.and(Filters.eq("name", "Smith0"), Filters.lt("age", 35));
		
		//Bson filter = Filters.or(Filters.eq("name", "Smith0"), Filters.lt("age", 35));
		
		System.out.println("Find all with into:");
		List<Document> docList = crudTest.find(filter).into(new ArrayList<Document>());
		for (Document d : docList) {
			Helpers.printJson(d, true);
		}
		System.out.println("================= Find all with iteration:");
		MongoCursor<Document> cursor = crudTest.find(filter).iterator();
		try {
			while(cursor.hasNext()) {
				Helpers.printJson(cursor.next(), true);
			}
		} finally {
			cursor.close();
		}
		System.out.println("Count:");
		System.out.println(crudTest.count(filter));
		
		client.close();


	}

}
