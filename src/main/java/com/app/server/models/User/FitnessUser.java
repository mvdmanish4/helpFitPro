package com.app.server.models.User;

import com.app.server.models.Preferences.Ailment;
import com.app.server.models.Preferences.Habit;
import com.app.server.models.Preferences.Interest;

import java.util.List;

/**
 * FitnessUser
 *
 */
public class FitnessUser {

    private String id = null;
    private String userId;
    private String gender;
    private String height;
    private String weight;
    private String bmi;
    private String city;
    private String state;
    private String zipCode;
    private List<Ailment> ailments;
    private List<Interest> interests;
    private List<Habit> habits;
    private Boolean termsConsent;
    private Boolean receiveEmailNotifications;
    private String timeCreated;
    private String timeUpdated;
    private Boolean isActive;
    private User userDetails;

    public FitnessUser(String userId, String gender, String height, String weight, String city, String state,
                       String zipCode, List<Ailment> ailments, List<Interest> interests, List<Habit> habits,
                       Boolean termsConsent, Boolean receiveEmailNotifications,
                       String timeCreated, String timeUpdated, Boolean isActive){
        this.userId = userId;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.ailments = ailments;
        this.interests = interests;
        this.habits = habits;
        this.termsConsent = termsConsent;
        this.receiveEmailNotifications = receiveEmailNotifications;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.isActive = isActive;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public List<Ailment> getAilments() {
        return ailments;
    }

    public void setAilments(List<Ailment> ailments) {
        this.ailments = ailments;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public List<Habit> getHabits() {
        return habits;
    }

    public void setHabits(List<Habit> habits) {
        this.habits = habits;
    }

    public Boolean getTermsConsent() {
        return termsConsent;
    }

    public void setTermsConsent(Boolean termsConsent) {
        this.termsConsent = termsConsent;
    }

    public Boolean getReceiveEmailNotifications() {
        return receiveEmailNotifications;
    }

    public void setReceiveEmailNotifications(Boolean receiveEmailNotifications) {
        this.receiveEmailNotifications = receiveEmailNotifications;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getTimeUpdated() {  return timeUpdated; }

    public void setTimeUpdated(String timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public Boolean getIsActive() { return isActive; }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public User getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(User userDetails) {
        this.userDetails = userDetails;
    }
}
