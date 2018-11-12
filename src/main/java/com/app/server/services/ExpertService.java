package com.app.server.services;

import com.app.server.models.User.Expert;
import com.app.server.models.User.FitnessUser;
import com.app.server.util.MongoPool;
import com.app.server.util.parser.ExpertDocumentParser;
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
public class ExpertService {

    private static ExpertService instance;
    private ObjectWriter ow;
    private MongoCollection<Document> expertCollection = null;

    private ExpertService() {
        this.expertCollection = MongoPool.getInstance().getCollection("expert");
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    public static ExpertService getInstance(){
        if (instance == null) {
            synchronized (ExpertService.class) {
                if (instance == null) {
                    instance = new ExpertService();
                }
            }
        }
        return instance;
    }

    public Expert getFitnessUser(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        Document item = this.expertCollection.find(query).first();
        if (item == null) {
            return null;
        }
        return ExpertDocumentParser.convertDocumentToExpert(item);
    }

    public Expert create(Object request, String userId) {

        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));
            Document doc = FitnessUserDocumentParser.convertJsonToFitnessUserDocument(json, userId);
            expertCollection.insertOne(doc);
            Expert expert = ExpertDocumentParser.convertJsonToExpert(json, userId);
            ObjectId id = (ObjectId)doc.get("_id");
            expert.setId(id.toString());
            return expert;
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
            Document set = new Document("$set",
                    ExpertDocumentParser.convertJsonToExpertDocument(json, json.getString("userId")));
            expertCollection.updateOne(query,set);
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
        expertCollection.deleteOne(query);
        return new JSONObject();
    }


    public Object deleteAll() {
        expertCollection.deleteMany(new BasicDBObject());
        return new JSONObject();
    }
}