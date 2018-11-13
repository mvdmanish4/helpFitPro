package com.app.server.util.parser;

import com.app.server.models.Payment.Transaction;
import org.bson.Document;
import org.json.JSONObject;

import java.time.Instant;

public class TransactionParser {

    public static Transaction convertDocumentToTransaction(Document item) {
        Transaction transaction = new Transaction(item.getString("eventId"),
                item.getString("organizerId"),
                item.getString("fitnessUserId"),
                item.getDouble("amount"),
                item.getString("currency"),
                item.getString("paymentMode"),
                item.getString("paymentGateway"),
                item.getString("paymentAttemptId"),
                item.getString("transactionState"),
                item.getString("transactionTime"),
                item.getString("timeCreated"),
                item.getString("timeUpdated"),
                item.getBoolean("isActive"));
        transaction.setId(item.getObjectId("_id").toString());
        return transaction;
    }

    public static Transaction convertJsonToTransaction(JSONObject json){
        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        //Date date = new Date();
        Transaction transaction = new Transaction( json.getString("eventId"),
                json.getString("organizerId"),
                json.getString("fitnessUserId"),
                json.getDouble("amount"),
                json.getString("currency"),
                json.getString("paymentMode"),
                json.getString("paymentGateway"),
                json.getString("paymentAttemptId"),
                json.getString("transactionState"),
                json.getString("transactionTime"),
                json.has("timeCreated")?json.getString("timeCreated"): String.valueOf(Instant.now().getEpochSecond()),
                String.valueOf(Instant.now().getEpochSecond()),
                true);
        return transaction;
    }

    public static Document convertTransactionToDocument(Transaction transaction){
        Document doc = new Document("eventId", transaction.getEventId())
                .append("organizerId",transaction.getOrganizerId())
                .append("fitnessUserId",transaction.getFitnessUserId())
                .append("amount",transaction.getAmount())
                .append("currency",transaction.getCurrency())
                .append("paymentMode",transaction.getPaymentMode())
                .append("paymentGateway",transaction.getPaymentGateway())
                .append("paymentAttemptId",transaction.getPaymentAttemptId())
                .append("transactionState",transaction.getTransactionState())
                .append("transactionTime", transaction.getTransactionTime())
                .append("timeCreated", transaction.getTimeCreated())
                .append("timeUpdated", transaction.getTimeUpdated())
                .append("isActive", transaction.getActive());
        return doc;
    }
}
