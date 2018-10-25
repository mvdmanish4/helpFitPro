package com.app.server.models.UserInfo;

public class Certification {

    private String id = null;
    private String expertId;
    private String name;
    private String issuingAuthority;
    private String issuedDate;
    private Boolean isActive;
    private String timeCreated;
    private String timeUpdated;

    public Certification(String expertId, String name, String issuingAuthority, String issuedDate,
                         Boolean isActive, String timeCreated, String timeUpdated){
        this.name = name;
        this.expertId = expertId;
        this.issuingAuthority = issuingAuthority;
        this.issuedDate = issuedDate;
        this.isActive = isActive;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
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
