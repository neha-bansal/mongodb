package com.mongodb.week2;

import java.io.IOException;
import java.io.StringWriter;

import org.bson.Document;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.week2.helpers.Helpers;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class HelloWorldMongoDBSparkFreemarkerStyle {

	public static void main(String[] args) {
		final Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(HelloWorldMongoDBSparkFreemarkerStyle.class, "/freemarker");
		
		MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase testDb = client.getDatabase("test");
		final MongoCollection<Document> crudTest = testDb.getCollection("crudTest");
		crudTest.drop();
		
		Document smith = new Document().append("name", "Smith")
										.append("age", 30)
										.append("profession", "programmer");
		Helpers.printJson(smith, true);
		
		crudTest.insertOne(smith);
		
		Spark.get(new Route("/") {
			
			@Override
			public Object handle(Request arg0, Response arg1) {
				StringWriter writer = new StringWriter();
				try {
					Template helloTemplate = configuration.getTemplate("hello.ftl");
					Document document = crudTest.find().first();
					helloTemplate.process(document, writer);
					System.out.println(writer);
				} catch (Exception e) {
					halt(500);
					e.printStackTrace();
				}
				return writer;
			}
		});
		//client.close();
	}

}
