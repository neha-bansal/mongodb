package com.mongodb.week2.helpers;

import java.io.StringWriter;

import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

public class Helpers {

	public static void printJson(Document document, boolean indent) {
		JsonWriter jsonWriter = new JsonWriter(new StringWriter(), new JsonWriterSettings(indent));
		new DocumentCodec().encode(jsonWriter, document, EncoderContext.builder().isEncodingCollectibleDocument(true).build());
		System.out.println(jsonWriter.getWriter());
		
		System.out.flush();
	}
}
