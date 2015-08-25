package com.mongodb.week1;


import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class SparkFormHandling {

	public static void main(String[] args) {
		final Configuration config = new Configuration();
		config.setClassForTemplateLoading(SparkFormHandling.class, "/");
		
		Spark.get(new Route("/") {
			@Override
			public Object handle(Request arg0, Response arg1) {
				StringWriter writer = new StringWriter();
				try {
					Template helloTemplate = config.getTemplate("fruitPicker.ftl");
					
					Map<String, Object> fruitMap = new HashMap<String, Object>();
					String[] fruitArray = {"Apple", "Pears", "Peach", "Orange"};
					fruitMap.put("fruits", fruitArray);
					
					helloTemplate.process(fruitMap, writer);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return writer;
			}
		});
		Spark.post(new Route("/favorite_fruit") {
			@Override
			public Object handle(Request req, Response res) {
				String fruit = req.queryParams("fruit");
				if (fruit == null) {
					return "Why you did not choose any fruit";
				} else {
					return "You like " + fruit;
				}
			}
		});
	}

}
