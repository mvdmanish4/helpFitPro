package com.app.server.models.Event;

import com.app.server.models.Preferences.Ailment;
import com.app.server.models.Preferences.Habit;
import com.app.server.models.Preferences.Interest;

import java.util.List;

public class EventTicket {

    private String id = null;
    private String eventId;
    private String fitnessUserId;
    private String registrationStatus;
    private String paymentStatus;
    private String paymentMode;
    private String transactionId;
    private String registrationTime;
    private String registrationDate;
    private Boolean isActive;
    private String timeCreated;
    private String timeUpdated;

    public EventTicket(String eventId, String fitnessUserId, String registrationStatus, String paymentStatus,
                       String paymentMode, Boolean isActive, String timeCreated, String timeUpdated){
        this.eventId = eventId;
        this.fitnessUserId = fitnessUserId;
        this.registrationStatus = registrationStatus;
        this.paymentStatus = paymentStatus;
        this.paymentMode = paymentMode;
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

    public String getFitnessUserId() {
        return fitnessUserId;
    }

    public void setFitnessUserId(String fitnessUserId) {
        this.fitnessUserId = fitnessUserId;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(String timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public void setId(String id) {
        this.id = id;
    }
}
