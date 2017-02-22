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
    private static DbHelper sInstance;
    private MongoDatabase db;

    /* Private constructor, acts as Singleton. Sets up the database connection */
    private DbHelper() {
        // Create uri from Herkoku config variable
        MongoClientURI uri = new MongoClientURI(System.getenv("MONGODB_URI"));
        MongoClient client = new MongoClient(uri);
        db = client.getDatabase(uri.getDatabase());
    }

    public static DbHelper getInstance() {
        if (sInstance == null) {
            sInstance = new DbHelper();
        }
        return sInstance;
    }

    public Price fetchCurrentPriceForId(int id) {
        // Query for given id
        MongoCollection<Document> prices = db.getCollection("prices");
        Document findQuery = new Document("id", id);
        MongoCursor<Document> cursor = prices.find(findQuery).iterator();

        try {
            if (cursor.hasNext()) {
                Document doc = cursor.next();
                return new Price((Double) doc.get("value"), (String) doc.get("currency_code"));
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            cursor.close();
        }
    }

    /**
        Add seed data to prices database, if and only if the
        prices database is empty.
    **/
    public void addSeedData() {
        MongoCollection<Document> prices = db.getCollection("prices");

        // Return early if the database is non-empty
        if (prices.count() != 0) return;

        List<Document> seedData = new ArrayList<Document>();

        seedData.add(new Document("id", 1)
            .append("value", 1.11)
            .append("currency_code", "USD_1")
        );

        seedData.add(new Document("id", 2)
            .append("value", 2.22)
            .append("currency_code", "USD_2")
        );

        seedData.add(new Document("id", 3)
            .append("value", 3.33)
            .append("currency_code", "USD_3")
        );

        prices.insertMany(seedData);
    }
}