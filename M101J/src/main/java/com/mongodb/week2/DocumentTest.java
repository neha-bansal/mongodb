package com.mongodb.week2;

import java.util.Arrays;
import java.util.Date;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.week2.helpers.Helpers;

public class DocumentTest {

	public static void main(String[] args) {
		Document document = new Document()
							.append("str", "Hello, Mongo!!!")
							.append("int", 1)
							.append("l", 5l)
							.append("double", 1.1)
							.append("b", true)
							.append("date", new Date())
							.append("objectID", new ObjectId())
							.append("null", null)
							.append("embedded", new Document("key", "value"))
							.append("list", Arrays.asList(1, 2, 3));
		
		String str = (String) document.get("str");
		Helpers.printJson(document, true);
	}

}
