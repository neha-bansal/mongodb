/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tengen;

import java.net.UnknownHostException;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoSocketReadException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class WriteConcernTest {
    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        MongoClient client = new MongoClient(Arrays.asList(
                new ServerAddress("localhost", 27017),
                new ServerAddress("localhost", 27018),
                new ServerAddress("localhost", 27019)));

        client.setWriteConcern(WriteConcern.JOURNALED);

        MongoDatabase db = client.getDatabase("course");
        //db.withWriteConcern(WriteConcern.JOURNALED);
        MongoCollection<Document> coll = db.getCollection("write.test");
       // coll.withWriteConcern(WriteConcern.JOURNALED);

        coll.drop();

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
        	try {
        		coll.insertOne(new Document("_id", i));
	            System.out.println("Inserted document: " + i);
        	} catch (MongoSocketReadException ex){
        		System.out.println("Exception while inserting document: " + i);
        	}
            Thread.sleep(500);
        }
//        try {
//            coll.insertOne(doc, WriteConcern.UNACKNOWLEDGED);
//        } catch (MongoException e) {
//            System.out.println(e.getMessage());
//        }
        client.close();
    }
}
