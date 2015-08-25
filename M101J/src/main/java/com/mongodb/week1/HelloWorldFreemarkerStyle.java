package com.mongodb.week1;


import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class HelloWorldFreemarkerStyle {

	public static void main(String[] args) {
		Configuration config = new Configuration();
		config.setClassForTemplateLoading(HelloWorldFreemarkerStyle.class, "/");
		
		try {
			Template helloTemplate = config.getTemplate("hello.ftl");
			
			StringWriter writer = new StringWriter();
			Map<String, Object> nameMap = new HashMap<String, Object>();
			nameMap.put("name", "Freemarker");
			
			helloTemplate.process(nameMap, writer);
			
			System.out.println(writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
