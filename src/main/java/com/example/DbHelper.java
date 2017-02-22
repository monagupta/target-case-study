package com.example;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {
    public Price fetchCurrentPriceForId(int id) {
        // Create uri from Herkoku config variable
        // TODO(mona): Don't open and close connection every time :p
        MongoClientURI uri = new MongoClientURI(System.getenv("MONGODB_URI"));
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());

        // Query for given id
        MongoCollection<Document> prices = db.getCollection("prices");
        Document findQuery = new Document("id", id);
        MongoCursor<Document> cursor = prices.find(findQuery).iterator();

        try {
            if (cursor.hasNext()) {
                Document doc = cursor.next();
                return new Price((Integer) doc.get("value"), (String) doc.get("currency_code"));
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            cursor.close();
            client.close();
        }
    }
}