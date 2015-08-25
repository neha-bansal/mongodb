package com.mongodb.week1;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class SparkRoutes {

	public static void main(String[] args) {
		Spark.get(new Route("/") {
			@Override
			public Object handle(Request arg0, Response arg1) {
				return "Hello from Root";
			}
		});
		Spark.get(new Route("/test") {
			@Override
			public Object handle(Request arg0, Response arg1) {
				return "Hello from Test";
			}
		});
		Spark.get(new Route("/test/:id") {
			@Override
			public Object handle(Request request, Response response) {
				return request.params("id");
			}
		});
	}

}
