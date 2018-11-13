package com.app.server.models.User;

import com.app.server.models.Event.Event;
import com.app.server.models.Preferences.RegionsOfService;

import java.util.ArrayList;
import java.util.List;

/**
 * EventOrganizer
 *
 */
public class EventOrganizer {

    private String id = null;
    private String userId;
    private String organizationName;
    private String website;
    private List<RegionsOfService> regionsOfService;
    private String titleInOrganization;
    private String timeCreated;
    private String timeUpdated;
    private Boolean isActive;
    private User userDetails = null;
    private List<Event> events = new ArrayList<>();

    public EventOrganizer(String userId, List<RegionsOfService> regionsOfService,
                          String timeCreated, String timeUpdated, Boolean isActive){
        this.userId = userId;
        this.regionsOfService = regionsOfService;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.isActive = isActive;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setUserDetails(User userDetails) { this.userDetails = userDetails; }

    public void setEvents(List<Event> events) { this.events = events; }

    public String getOrganizationName(){
        return organizationName;
    }

    public String getWebsite(){
        return website;
    }

    public String getUserId(){
        return userId;
    }

    public List<RegionsOfService> getRegionsOfService() { return regionsOfService; }

    public String getTitleInOrganization(){
        return titleInOrganization;
    }

    public String getTimeCreated(){
        return timeCreated;
    }

    public String getTimeUpdated(){
        return timeUpdated;
    }

    public Boolean getIsActive(){
        return isActive;
    }

    public User getUserDetails() { return userDetails; }

    public List<Event> getEvents() { return events; }


    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setTitleInOrganization(String titleInOrganization) {
        this.titleInOrganization = titleInOrganization;
    }
}
