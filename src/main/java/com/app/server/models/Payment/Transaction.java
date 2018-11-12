package com.app.server.models.Payment;

import com.app.server.models.Preferences.Ailment;
import com.app.server.models.Preferences.Habit;
import com.app.server.models.Preferences.Interest;

import java.util.Date;
import java.util.List;

public class Transaction {

    private String id = null;
    private String eventId;
    private String organizerId;
    private String fitnessUserId;
    private Double amount;
    private String currency;
    private String paymentMode;
    private String paymentGateway;
    private String paymentAttemptId;
    private String transactionState;
    private String transactionTime;
    private String timeCreated;
    private String timeUpdated;
    private Boolean isActive;

    public Transaction(String eventId, String organizerId, String fitnessUserId, Double amount, String currency, String paymentMode, String paymentGateway, String paymentAttemptId, String transactionState, String transactionTime, String timeCreated, String timeUpdated, Boolean isActive) {
        this.eventId = eventId;
        this.organizerId = organizerId;
        this.fitnessUserId = fitnessUserId;
        this.amount = amount;
        this.currency = currency;
        this.paymentMode = paymentMode;
        this.paymentGateway = paymentGateway;
        this.paymentAttemptId = paymentAttemptId;
        this.transactionState = transactionState;
        this.transactionTime = transactionTime;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.isActive = isActive;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public String getFitnessUserId() {
        return fitnessUserId;
    }

    public void setFitnessUserId(String fitnessUserId) {
        this.fitnessUserId = fitnessUserId;
    }

    public String getId() {return id;}

    public Double getAmount() {return amount;}

    public void setAmount(Double amount) {this.amount = amount;}

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(String paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public String getPaymentAttemptId() {
        return paymentAttemptId;
    }

    public void setPaymentAttemptId(String paymentAttemptId) {
        this.paymentAttemptId = paymentAttemptId;
    }

    public String getTransactionState() {
        return transactionState;
    }

    public void setTransactionState(String transactionState) {
        this.transactionState = transactionState;
    }

    public String getTransactionTime() {return transactionTime;}

    public void setTransactionTime(String transactionTime) {this.transactionTime = transactionTime;}

    public String getTimeCreated() {return timeCreated;}

    public void setTimeCreated(String timeCreated) {this.timeCreated = timeCreated;}

    public String getTimeUpdated() {return timeUpdated;}

    public void setTimeUpdated(String timeUpdated) {this.timeUpdated = timeUpdated;}

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setId(String id) {
        this.id = id;
    }
}
