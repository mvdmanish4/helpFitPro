package com.app.server.models.User;

import com.app.server.models.Preferences.Ailment;
import com.app.server.models.Preferences.Interest;
import com.app.server.models.UserInfo.Certification;

import java.util.List;

/**
 * Expert
 *
 */
public class Expert {

    private String id = null;
    private String userId;
    private String organizationName; //Optional
    private String cityOfService;
    private String regionOfService;
    private List<Ailment> ailments;
    private String website; //Optional
    private List<Certification> certifications; //Optional
    private Boolean termsConsent;
    private Boolean isActive;
    private String timeCreated;
    private String timeUpdated;
    private User userDetails;

    public Expert(String userId, String cityOfService, String regionOfService,
                  List<Ailment> ailments, Boolean termsConsent,
                  String timeCreated, String timeUpdated, Boolean isActive){
        this.userId = userId;
        this.cityOfService = cityOfService;
        this.regionOfService = regionOfService;
        this.ailments = ailments;
        this.termsConsent = termsConsent;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.isActive = isActive;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getCityOfService() {
        return cityOfService;
    }

    public void setCityOfService(String cityOfService) {
        this.cityOfService = cityOfService;
    }

    public String getRegionOfService() {
        return regionOfService;
    }

    public void setRegionOfService(String regionOfService) {
        this.regionOfService = regionOfService;
    }

    public List<Ailment> getAilments() {
        return ailments;
    }

    public void setAilments(List<Ailment> ailments) {
        this.ailments = ailments;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Certification> getCertifications() {
        return certifications;
    }

    public void setCertification(List<Certification> certification) {
        this.certifications = certifications;
    }

    public Boolean getTermsConsent() {
        return termsConsent;
    }

    public void setTermsConsent(Boolean termsConsent) {
        this.termsConsent = termsConsent;
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

    public User getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(User userDetails) {
        this.userDetails = userDetails;
    }
}
