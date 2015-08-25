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

public class HelloWorldSparkFreemarkerStyle {

	public static void main(String[] args) {
		final Configuration config = new Configuration();
		config.setClassForTemplateLoading(HelloWorldSparkFreemarkerStyle.class, "/");
		
		Spark.get(new Route("/") {
			@Override
			public Object handle(Request arg0, Response arg1) {
				StringWriter writer = new StringWriter();
				try {
					Template helloTemplate = config.getTemplate("hello.ftl");
					
					Map<String, Object> nameMap = new HashMap<String, Object>();
					nameMap.put("name", "Freemarker");
					
					helloTemplate.process(nameMap, writer);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return writer;
			}
		});
	}

}
