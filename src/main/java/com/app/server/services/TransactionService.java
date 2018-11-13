package com.app.server.services;

import com.app.server.http.exceptions.APPBadRequestException;
import com.app.server.http.exceptions.APPInternalServerException;
import com.app.server.http.exceptions.APPUnauthorizedException;
import com.app.server.models.Payment.Transaction;
import com.app.server.util.MongoPool;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import static com.app.server.util.parser.TransactionParser.convertDocumentToTransaction;
import static com.app.server.util.parser.TransactionParser.convertJsonToTransaction;
import static com.app.server.util.parser.TransactionParser.convertTransactionToDocument;

public class TransactionService {

    private static TransactionService self;
    private ObjectWriter ow;
    private MongoCollection<Document> transactionCollection = null;

    private TransactionService() {
        this.transactionCollection = MongoPool.getInstance().getCollection("transaction");
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    public static TransactionService getInstance(){
        if (self == null)
            self = new TransactionService();
        return self;
    }

    public Transaction getOne(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        Document item = transactionCollection.find(query).first();
        if (item == null) {
            return  null;
        }
        return convertDocumentToTransaction(item);
    }

    public Transaction create(Object request) {
        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));
            Transaction transaction = convertJsonToTransaction(json);
            Document doc = convertTransactionToDocument(transaction);
            transactionCollection.insertOne(doc);
            ObjectId id = (ObjectId)doc.get( "_id" );
            transaction.setId(id.toString());
            return transaction;
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

    public ArrayList<Transaction> getAllUserTranscations(String id){
        ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();

        FindIterable<Document> results = this.transactionCollection.find();
        if (results == null) {
            return transactionsList;
        }
        for (Document item : results) {
            Transaction transaction = convertDocumentToTransaction(item);
            if(transaction.getFitnessUserId().equals(id)){
                transactionsList.add(transaction);}
        }
        return transactionsList;
    }


    public Object update(String id, Object request) {
        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));

            BasicDBObject query = new BasicDBObject();
            query.put("_id", new ObjectId(id));

            Document doc = new Document();
            if (json.has("eventId"))
                doc.append("eventId",json.getString("eventId"));
            if (json.has("organizerId"))
                doc.append("organizerId",json.getString("organizerId"));
            if (json.has("fitnessUserId"))
                doc.append("fitnessUserId",json.getString("fitnessUserId"));
            if (json.has("amount"))
                doc.append("amount",json.getDouble("amount"));
            if (json.has("currency"))
                doc.append("currency",json.getString("currency"));
            if (json.has("paymentMode"))
                doc.append("paymentMode",json.getString("paymentMode"));
            if (json.has("paymentGateway"))
                doc.append("paymentGateway",json.getString("paymentGateway"));
            if (json.has("paymentAttemptId"))
                doc.append("paymentAttemptId",json.getString("paymentAttemptId"));
            if (json.has("transactionState"))
                doc.append("transactionState",json.getString("transactionState"));

            Document set = new Document("$set", doc);
            transactionCollection.updateOne(query,set);
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
        transactionCollection.deleteOne(query);
      return new JSONObject();
    }
}
