package com.app.server.services;

import com.app.server.http.exceptions.APPBadRequestException;
import com.app.server.http.exceptions.APPInternalServerException;
import com.app.server.http.exceptions.APPUnauthorizedException;
import com.app.server.models.HealthRegime.RegimeProgram;
import com.app.server.models.Payment.Transaction;
import com.app.server.models.User.User;
import com.app.server.util.APPCrypt;
import com.app.server.util.MongoPool;
import com.app.server.util.parser.UserDocumentParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.core.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

/**
 * UserService
 *
 * <a href="mailto:sivashav@andrew.cmu.edu">Siva Shankaran Vasanth</a>
 */
public class UserService {

    private static UserService userInstance;
    private ObjectWriter ow;
    private MongoCollection<Document> userCollection = null;
    private TransactionService transactionService;
    private RegimeProgramService regimeProgramService;


    private UserService() {
        this.userCollection = MongoPool.getInstance().getCollection("user");
        transactionService = TransactionService.getInstance();
        regimeProgramService = RegimeProgramService.getInstance();
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    public static UserService getInstance(){
        if (userInstance == null) {
            synchronized (UserService .class) {
                if (userInstance == null) {
                    userInstance = new UserService();
                }
            }
        }
        return userInstance;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<User>();
        FindIterable<Document> results = this.userCollection.find();
        if (results == null) {
            return userList;
        }
        for (Document item : results) {
            User user = UserDocumentParser.convertDocumentToUser(item);
            userList.add(user);
        }
        return userList;
    }

    public User getUser(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        Document userItem = this.userCollection.find(query).first();
        if (userItem == null) {
            return null;
        }
        return UserDocumentParser.convertDocumentToUser(userItem);
    }

    public User create(Object request, Boolean isAdmin) {
        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));
            if(!UserDocumentParser.isUserTypeSupported(json)){
                return null;
            }
            Document doc = UserDocumentParser.convertJsonToUserDocument(json, isAdmin);
            userCollection.insertOne(doc);
            User user = UserDocumentParser.convertJsonToUser(json, isAdmin);
            ObjectId id = (ObjectId)doc.get( "_id" );
            user.setId(id.toString());
            return user;
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

    public ArrayList<Transaction> getUserTransactions(HttpHeaders headers, String id){
        ArrayList<Transaction> userTransactions = null;
        try {
            checkAuthentication(headers, id);
            userTransactions = transactionService.getAllUserTranscations(id);
        }catch(JsonProcessingException e) {
            System.out.println("Failed to create a document");
            return null;
        } catch(APPBadRequestException e) {
            throw new APPBadRequestException(33, e.getMessage());
        }catch(APPUnauthorizedException e) {
            throw new APPUnauthorizedException(34, e.getMessage());
        }catch(Exception e) {
            System.out.println("EXCEPTION!!!!");
            e.printStackTrace();
            throw new APPInternalServerException(99, e.getMessage());
        }
        return userTransactions;
    }

    public ArrayList<RegimeProgram> getUserRegimeProgram(HttpHeaders headers, String id){
        ArrayList<RegimeProgram> regimePrograms = null;
        try {
            checkAuthentication(headers, id);
            regimePrograms = regimeProgramService.getUserRegimeProgram(id);
        }catch(JsonProcessingException e) {
            System.out.println("Failed to create a document");
            return null;
        } catch(APPBadRequestException e) {
            throw new APPBadRequestException(33, e.getMessage());
        }catch(APPUnauthorizedException e) {
            throw new APPUnauthorizedException(34, e.getMessage());
        }catch(Exception e) {
            System.out.println("EXCEPTION!!!!");
            e.printStackTrace();
            throw new APPInternalServerException(99, e.getMessage());
        }
        return regimePrograms;
    }


    public Transaction createTranscation(HttpHeaders headers, String id, Object request) {
        Transaction trans = null;
        try {
            checkAuthentication(headers,id);
            trans = transactionService.create(request);
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
        return trans;
    }

    public RegimeProgram createUserRegimeProgram(HttpHeaders headers, String id, Object request){
        RegimeProgram regimeProgram = null;
        try {
            checkAuthentication(headers, id);
            regimeProgram = regimeProgramService.create(request);
        }catch(JsonProcessingException e) {
            System.out.println("Failed to create a document");
            return null;
        } catch(APPBadRequestException e) {
            throw new APPBadRequestException(33, e.getMessage());
        }catch(APPUnauthorizedException e) {
            throw new APPUnauthorizedException(34, e.getMessage());
        }catch(Exception e) {
            System.out.println("EXCEPTION!!!!");
            e.printStackTrace();
            throw new APPInternalServerException(99, e.getMessage());
        }
        return regimeProgram;
    }

    private void checkAuthentication(HttpHeaders headers,String id) throws Exception{
        List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        if (authHeaders == null)
            throw new APPUnauthorizedException(70,"No Authorization Headers");
        String token = authHeaders.get(0);
        String clearToken = APPCrypt.decrypt(token);
        if (id.compareTo(clearToken) != 0) {
            throw new APPUnauthorizedException(71,"Invalid token. Please try getting a new token");
        }
    }

    public Object update(String id, Object request) {
        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));
            BasicDBObject query = new BasicDBObject();
            query.put("_id", new ObjectId(id));
            Boolean isAdmin = false;
            if (json.has("isAdmin")) {
                isAdmin = json.getBoolean("isAdmin");
            }
            Document set = new Document("$set", UserDocumentParser.convertJsonToUserDocument(json, isAdmin));
            userCollection.updateOne(query,set);
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
        userCollection.deleteOne(query);
        return new JSONObject();
    }

    public Object deleteUserRegimeProgram(HttpHeaders headers, String id){
        Object obj;
        try {
            checkAuthentication(headers, id);
            obj = regimeProgramService.delete(id);
        }catch(JsonProcessingException e) {
            System.out.println("Failed to create a document");
            return null;
        } catch(APPBadRequestException e) {
            throw new APPBadRequestException(33, e.getMessage());
        }catch(APPUnauthorizedException e) {
            throw new APPUnauthorizedException(34, e.getMessage());
        }catch(Exception e) {
            System.out.println("EXCEPTION!!!!");
            e.printStackTrace();
            throw new APPInternalServerException(99, e.getMessage());
        }
       return obj;
    }


    public Object deleteAll() {
        userCollection.deleteMany(new BasicDBObject());
        return new JSONObject();
    }
}
