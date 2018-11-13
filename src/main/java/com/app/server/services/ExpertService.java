package com.app.server.services;

import com.app.server.http.exceptions.APPBadRequestException;
import com.app.server.http.exceptions.APPInternalServerException;
import com.app.server.http.exceptions.APPUnauthorizedException;
import com.app.server.models.Preferences.Ailment;
import com.app.server.models.User.Expert;
import com.app.server.models.User.User;
import com.app.server.util.MongoPool;
import com.app.server.util.parser.ExpertDocumentParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * EventOrganizerService
 *
 * <a href="mailto:sivashav@andrew.cmu.edu">Siva Shankaran Vasanth</a>
 */
public class ExpertService {

    private static ExpertService instance;
    private static UserService userServiceInstance;
    private ObjectWriter ow;
    private MongoCollection<Document> expertCollection = null;

    private ExpertService() {
        this.expertCollection = MongoPool.getInstance().getCollection("expert");
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		userServiceInstance = UserService.getInstance();
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

    public Expert getExpert(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        Document item = this.expertCollection.find(query).first();
        if (item == null) {
            return null;
        }
		Expert expert = ExpertDocumentParser.convertDocumentToExpert(item);
		User user = userServiceInstance.getUser(expert.getUserId());
		expert.setUserDetails(user);
		return expert;
    }

    public Expert create(Object request, String userId) {

        try {
        	// TODO Validate for all user type before creating the users (else there will be orphan users)
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));
			validateRequest(json);
            Document doc = ExpertDocumentParser.convertJsonToExpertDocument(json, userId);
            expertCollection.insertOne(doc);
            Expert expert = ExpertDocumentParser.convertJsonToExpert(json, userId);
            ObjectId id = (ObjectId)doc.get("_id");
            expert.setId(id.toString());
            return expert;
        } catch(JsonProcessingException e) {
			System.out.println("Failed to create a document");
			e.printStackTrace();
			return null;
		} catch(APPBadRequestException e) {
			throw new APPBadRequestException(33, e.getMessage());
		} catch(APPUnauthorizedException e) {
			throw new APPUnauthorizedException(34, e.getMessage());
		} catch(Exception e) {
			System.out.println("EXCEPTION!!!!");
			e.printStackTrace();
			throw new APPInternalServerException(99, e.getMessage());
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

        } catch(JsonProcessingException e) {
			System.out.println("Failed to create a document");
			return null;
		} catch(APPBadRequestException e) {
			throw new APPBadRequestException(33, e.getMessage());
		} catch(APPUnauthorizedException e) {
			throw new APPUnauthorizedException(34, e.getMessage());
		} catch(Exception e) {
			System.out.println("EXCEPTION!!!!");
			e.printStackTrace();
			throw new APPInternalServerException(99, e.getMessage());
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

    public Boolean validateRequest(JSONObject json){
		if (!json.has("cityOfService"))
			throw new APPBadRequestException(55, "city Missing");
		if (!json.has("regionOfService"))
			throw new APPBadRequestException(55, "regions Missing");
		if (!json.has("ailmentTags"))
			throw new APPBadRequestException(55, "aliments Missing");
		if (!json.has("termsConsent"))
			throw new APPBadRequestException(55, "terms Consent Missing");
		return true;
	}
}