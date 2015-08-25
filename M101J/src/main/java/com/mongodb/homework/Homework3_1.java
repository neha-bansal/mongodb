package com.mongodb.homework;

import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class Homework3_1 {

	/* Write a program that will remove the lowest homework score for each student. 
	 * Since there is a single document for each student containing an array of scores, 
	 * you will need to update the scores array and remove the homework.
	 * use students.json
	 */
	public static void main(String[] args) {
		MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase testDb = client.getDatabase("school");
		MongoCollection<Document> students = testDb.getCollection("students");
		
		System.out.println("Count:");
		System.out.println(students.count());
		
		MongoCursor<Document> cursor = students.find().sort(Sorts.ascending("_id")).projection(Projections.include("scores")).iterator();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			List<Document> scores = (List<Document>) doc.get("scores");
			Double tempScore = 0.0;
			Document removeScore = null;
			for (Document score : scores) {
				if (score.getString("type").equals("homework")) {
					if (tempScore == 0.0) {
						tempScore = score.getDouble("score");
						removeScore = score;
					} else if (tempScore != 0.0 && (tempScore.compareTo(score.getDouble("score")) > 0)) {
						removeScore = score;
					}
				System.out.println(doc.getInteger("_id") + " : " + removeScore);
			}
		}
			students.updateOne(Filters.eq("_id", doc.getInteger("_id")), new Document("$pull", new Document("scores", removeScore)));
		}
		System.out.println("Count after delete:");
		System.out.println(students.count());
		client.close();


	}

}
