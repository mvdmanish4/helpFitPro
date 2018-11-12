package com.app.server.models.HealthRegime;

import java.util.Date;
import java.util.List;

public class RegimeProgram {

    String id;
    String userID;
    List<String> regimesID;
    String recommendedDate;
    List<String> ailmentsAddressed;
    List<String> interestsAddressed;
    List<String> habitsAddressed;
    String isActive;
    String isFulfilled;
    Double durationWeeks;
    String startDate;
    String endDate;
    String timeCreated;
    String timeUpdated;

    public RegimeProgram(String userID, List<String> regimesID, String recommendedDate, List<String> ailmentsAddressed, List<String> interestsAddressed, List<String> habitsAddressed, String isActive, String isFulfilled, Double durationWeeks, String startDate, String endDate, String timeCreated, String timeUpdated) {
        this.userID = userID;
        this.regimesID = regimesID;
        this.recommendedDate = recommendedDate;
        this.ailmentsAddressed = ailmentsAddressed;
        this.interestsAddressed = interestsAddressed;
        this.habitsAddressed = habitsAddressed;
        this.isActive = isActive;
        this.isFulfilled = isFulfilled;
        this.durationWeeks = durationWeeks;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<String> getRegimesID() {
        return regimesID;
    }

    public void setRegimesID(List<String> regimesID) {
        this.regimesID = regimesID;
    }

    public String getRecommendedDate() {return recommendedDate;}

    public void setRecommendedDate(String recommendedDate) {this.recommendedDate = recommendedDate;}

    public List<String> getAilmentsAddressed() {
        return ailmentsAddressed;
    }

    public void setAilmentsAddressed(List<String> ailmentsAddressed) {
        this.ailmentsAddressed = ailmentsAddressed;
    }

    public List<String> getInterestsAddressed() {
        return interestsAddressed;
    }

    public void setInterestsAddressed(List<String> interestsAddressed) {
        this.interestsAddressed = interestsAddressed;
    }

    public List<String> getHabitsAddressed() {
        return habitsAddressed;
    }

    public void setHabitsAddressed(List<String> habitsAddressed) {
        this.habitsAddressed = habitsAddressed;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsFulfilled() {
        return isFulfilled;
    }

    public void setIsFulfilled(String isFulfilled) {
        this.isFulfilled = isFulfilled;
    }

    public Double getDurationWeeks() {
        return durationWeeks;
    }

    public void setDurationWeeks(Double durationWeeks) {
        this.durationWeeks = durationWeeks;
    }

    public String getStartDate() {return startDate;}

    public void setStartDate(String startDate) {this.startDate = startDate;}

    public String getEndDate() {return endDate;}

    public void setEndDate(String endDate) {this.endDate = endDate;}

    public String getTimeCreated() {return timeCreated;}

    public void setTimeCreated(String timeCreated) {this.timeCreated = timeCreated;}

    public String getTimeUpdated() {return timeUpdated;}

    public void setTimeUpdated(String timeUpdated) {this.timeUpdated = timeUpdated;}

    @Override
    public String toString() {
        return "RegimeProgram{" +
                "id='" + id + '\'' +
                ", userID='" + userID + '\'' +
                ", regimesID=" + regimesID +
                ", recommendedDate=" + recommendedDate +
                ", ailmentsAddressed=" + ailmentsAddressed +
                ", interestsAddressed=" + interestsAddressed +
                ", habitsAddressed=" + habitsAddressed +
                ", isActive='" + isActive + '\'' +
                ", isFulfilled='" + isFulfilled + '\'' +
                ", durationWeeks=" + durationWeeks +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", timeCreated=" + timeCreated +
                ", timeUpdated=" + timeUpdated +
                '}';
    }
}
