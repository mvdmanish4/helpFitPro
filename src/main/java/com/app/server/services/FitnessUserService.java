package com.app.server.services;

import com.app.server.models.User.FitnessUser;
import com.app.server.util.MongoPool;
import com.app.server.util.parser.FitnessUserDocumentParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * EventOrganizerService
 *
 * <a href="mailto:sivashav@andrew.cmu.edu">Siva Shankaran Vasanth</a>
 */
public class FitnessUserService {

    private static FitnessUserService instance;
    private ObjectWriter ow;
    private MongoCollection<Document> fitnessUserCollection = null;

    private FitnessUserService() {
        this.fitnessUserCollection = MongoPool.getInstance().getCollection("fitnessUser");
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    public static FitnessUserService getInstance(){
        if (instance == null) {
            synchronized (FitnessUserService.class) {
                if (instance == null) {
                    instance = new FitnessUserService();
                }
            }
        }
        return instance;
    }

    public FitnessUser getFitnessUser(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        Document item = this.fitnessUserCollection.find(query).first();
        if (item == null) {
            return null;
        }
        return FitnessUserDocumentParser.convertDocumentToFitnessUser(item);
    }

    public FitnessUser create(Object request, String userId) {

        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));
            Document doc = FitnessUserDocumentParser.convertJsonToFitnessUserDocument(json, userId);
            fitnessUserCollection.insertOne(doc);
            FitnessUser fitnessUser = FitnessUserDocumentParser.convertJsonToFitnessUser(json, userId);
            ObjectId id = (ObjectId)doc.get("_id");
            fitnessUser.setId(id.toString());
            return fitnessUser;
        } catch(JsonProcessingException e) {
            System.out.println("Failed to create a document");
            return null;
        }
    }

    public Object update(String id, Object request) {
        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));

            BasicDBObject query = new BasicDBObject();
            query.put("_id", new ObjectId(id));

            Document set = new Document("$set", FitnessUserDocumentParser.
                    convertJsonToFitnessUserDocument(json, json.getString("userId")));
            fitnessUserCollection.updateOne(query,set);
            return request;

        } catch(JSONException e) {
            System.out.println("Failed to update a document");
            return null;
        }
        catch(JsonProcessingException e) {
            System.out.println("Failed to create a document");
            return null;
        }
    }

    public Object delete(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        fitnessUserCollection.deleteOne(query);
        return new JSONObject();
    }


    public Object deleteAll() {
        fitnessUserCollection.deleteMany(new BasicDBObject());
        return new JSONObject();
    }
}