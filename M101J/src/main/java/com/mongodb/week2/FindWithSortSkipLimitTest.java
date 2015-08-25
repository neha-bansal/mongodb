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
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.week2.helpers.Helpers;

public class FindWithSortSkipLimitTest {

	public static void main(String[] args) {
		MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase testDb = client.getDatabase("test");
		MongoCollection<Document> crudTest = testDb.getCollection("crudTest");
		crudTest.drop();
		
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				crudTest.insertOne(new Document().append("name", "Smith" + i)
						.append("age", 30 + i)
						.append("profession", "programmer" + j)); 
			}
		}
		
		Bson projection = Projections.fields(Projections.include("name", "profession"), Projections.excludeId());
		
		//Bson sort = new Document("name", 1).append("profession", -1);
		Bson sort = Sorts.orderBy(Sorts.ascending("name"), Sorts.descending("profession")); 
		
		System.out.println("================= Find all with iteration:");
		MongoCursor<Document> cursor = crudTest.find()
										.projection(projection)
										.sort(sort)
										.skip(10) //firstly skip the result set, then limit it
										.limit(10)
										.iterator(); 
		int count = 0;
		try {
			while(cursor.hasNext()) {
				Helpers.printJson(cursor.next(), true);
				count++;
			}
		} finally {
			cursor.close();
		}
		System.out.println("Count:");
		System.out.println(count);
		
		client.close();

	}

}
