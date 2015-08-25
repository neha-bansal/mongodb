package com.mongodb.homework;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

/* Write a program that will remove the grade of type "homework" with the lowest score 
 * for each student from the dataset grades.json. 
 * Since each document is one grade, it should remove one document per student.
 */
public class Homework2_3 {

	public static void main(String[] args) {
		MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase testDb = client.getDatabase("students");
		MongoCollection<Document> grades = testDb.getCollection("grades");
		
		System.out.println("Count:");
		System.out.println(grades.count());
		
		MongoCursor<Document> cursor = grades.find(Filters.eq("type", "homework"))
										.sort(Sorts.ascending("student_id", "score"))
										.iterator();
		int studentId = -1;
		int count = 0;
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			if (studentId == -1 || (studentId != -1 && studentId != doc.getInteger("student_id"))) {
				studentId = doc.getInteger("student_id");
				System.out.println(studentId + ":" + doc.getDouble("score"));
				count++;
				grades.deleteOne(doc);
			}
		}
		System.out.println("count after find..." + count);
		
		System.out.println("Count after delete:");
		System.out.println(grades.count());
		client.close();

	}

}
