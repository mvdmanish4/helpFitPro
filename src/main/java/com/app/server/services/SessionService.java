package com.app.server.services;

import com.app.server.http.exceptions.APPBadRequestException;
import com.app.server.http.exceptions.APPConflictRequestException;
import com.app.server.http.exceptions.APPInternalServerException;
import com.app.server.http.exceptions.APPNotFoundException;
import com.app.server.models.Session.Session;
import com.app.server.models.User.User;
import com.app.server.models.UserInfo.UserType;
import com.app.server.util.APPCrypt;
import com.app.server.util.MongoPool;
import com.app.server.util.parser.UserDocumentParser;
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
    private static UserService userService;
    private static FitnessUserService fitnessUserService;
    private static EventOrganizerService eventOrganizerService;
    private static ExpertService expertService;

    private MongoCollection<Document> userCollection = null;
    private ObjectWriter ow;

    private SessionService() {
        this.userCollection = MongoPool.getInstance().getCollection("user");
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        userService = UserService.getInstance();
        fitnessUserService = FitnessUserService.getInstance();
        eventOrganizerService = EventOrganizerService.getInstance();
        expertService = ExpertService.getInstance();
    }

    public static SessionService getInstance(){
        if (self == null)
            self = new SessionService();
        return self;
    }

    public Session createUser(Object request) {

        JSONObject json = null;
        try {
            json = new JSONObject(ow.writeValueAsString(request));
            if (!json.has("emailAddress"))
                throw new APPBadRequestException(55, "missing emailAddress");
            if (!json.has("password"))
                throw new APPBadRequestException(55, "missing password");
            BasicDBObject query = new BasicDBObject();
            query.put("emailAddress", json.getString("emailAddress"));
            query.put("password", APPCrypt.encrypt(json.getString("password")));
            //query.put("password", json.getString("password"));
            Document item = this.userCollection.find(query).first();
            if (item == null) {
                throw new APPNotFoundException(0, "No user found matching credentials");
            }
            User user = UserDocumentParser.convertDocumentToUser(item);
            user.setId(item.getObjectId("_id").toString());
            return new Session(user);
        } catch (JsonProcessingException e) {
            throw new APPBadRequestException(33, e.getMessage());
        } catch (APPBadRequestException e) {
            throw e;
        } catch (APPNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new APPInternalServerException(0, e.getMessage());
        }
    }

    public Session signUp(Object request) {
        JSONObject json = null;
        try {
            json = new JSONObject(ow.writeValueAsString(request));

            validateSignUpRequest(json);
            checkIfEmailAlreadyRegistered(json);

            UserType userType = UserType.getUserType(json.getInt("userType"));

            if(userType.equals(UserType.ADMIN)){
                return new Session(createAdminUser(request));
            } else if (userType.equals(UserType.FITNESS_USER)){
                return new Session(createFitnessUser(request));
            } else if (userType.equals(UserType.EXPERT)){
                return new Session(createExpert(request));
            } else if (userType.equals(UserType.EVENT_ORGANIZER)){
                return new Session(createFitnessUser(request));
            }
            return null;
        } catch (JsonProcessingException e) {
            throw new APPBadRequestException(33, e.getMessage());
        } catch (APPBadRequestException e) {
            throw e;
        } catch (APPNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new APPInternalServerException(0, e.getMessage());
        }
    }

    private Boolean validateSignUpRequest(JSONObject json){
        if (!json.has("emailAddress"))
            throw new APPBadRequestException(55, "missing emailAddress");
        if (!json.has("password"))
            throw new APPBadRequestException(55, "missing password");
        if (!json.has("userType"))
            throw new APPBadRequestException(55, "missing userType");
        if (!UserDocumentParser.isUserTypeSupported(json))
            throw new APPBadRequestException(55, "denied userType");
        return true;
    }

    private Boolean checkIfEmailAlreadyRegistered(JSONObject json) throws Exception{
        BasicDBObject query = new BasicDBObject();
        query.put("emailAddress", json.getString("emailAddress"));
        query.put("password", APPCrypt.encrypt(json.getString("password")));
        Document item = this.userCollection.find(query).first();
        if (item != null) {
            throw new APPConflictRequestException(309, "User with the given email Address already exists");
        }
        return true;
    }

    private User createAdminUser(Object request) throws Exception {
        JSONObject json = new JSONObject(ow.writeValueAsString(request));
        if (!json.has("adminCode"))
            throw new APPBadRequestException(55, "Admin code is missing");
        if(json.getString("adminCode") != "76876811") {
            //TODO: The Admin code has to be configured securely in the final submission of the project
            throw new APPBadRequestException(55, "Account creation Denied");
        }
        return userService.create(request, true);
    }

    private User createFitnessUser(Object request) throws Exception {
        JSONObject json = new JSONObject(ow.writeValueAsString(request));
        User user = userService.create(request, false);
        fitnessUserService.create(request,user.getId());
        return user;
    }

    private User createExpert(Object request) throws Exception {
        JSONObject json = new JSONObject(ow.writeValueAsString(request));
        User user = userService.create(request, false);
        expertService.create(request, user.getId());
        return user;
    }

    private User createEventOrganizer(Object request) throws Exception {
        JSONObject json = new JSONObject(ow.writeValueAsString(request));
        User user = userService.create(request, false);
        eventOrganizerService.create(request, user.getId());
        return user;
    }
} // end of main()
