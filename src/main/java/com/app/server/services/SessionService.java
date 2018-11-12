package com.app.server.services;

import com.app.server.util.parser.UserDocumentParser;
import com.app.server.http.exceptions.APPBadRequestException;
import com.app.server.http.exceptions.APPInternalServerException;
import com.app.server.http.exceptions.APPNotFoundException;

import com.app.server.models.Session.Session;
import com.app.server.models.User.User;
import com.app.server.util.APPCrypt;
import com.app.server.util.MongoPool;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.json.JSONObject;

/**
 * Services run as singletons
 */

public class SessionService {

    private static SessionService self;
    private MongoCollection<Document> userCollection = null;
    private ObjectWriter ow;

    private SessionService() {
        this.userCollection = MongoPool.getInstance().getCollection("user");
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    public static SessionService getInstance(){
        if (self == null)
            self = new SessionService();
        return self;
    }

    public Session create(Object request) {

        JSONObject json = null;
        try {
            json = new JSONObject(ow.writeValueAsString(request));
            if (!json.has("emailAddress"))
                throw new APPBadRequestException(55, "missing emailAddress");
            if (!json.has("password"))
                throw new APPBadRequestException(55, "missing password");
            BasicDBObject query = new BasicDBObject();
            query.put("emailAddress", json.getString("emailAddress"));
            //query.put("password", APPCrypt.encrypt(json.getString("password")));
            query.put("password", json.getString("password"));
            Document item = this.userCollection.find(query).first();
            if (item == null) {
                throw new APPNotFoundException(0, "No user found matching credentials");
            }
            User user = UserDocumentParser.convertDocumentToUser(item);
            user.setId(item.getObjectId("_id").toString());
            return new Session(user);
        }
        catch (JsonProcessingException e) {
            throw new APPBadRequestException(33, e.getMessage());
        }
        catch (APPBadRequestException e) {
            throw e;
        }
        catch (APPNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            throw new APPInternalServerException(0, e.getMessage());
        }
    }
} // end of main()
