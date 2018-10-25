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

    public EventOrganizer(String userId, String organizationName, String website, List<RegionsOfService> regionsOfService,
                          String titleInOrganization, String timeCreated, String timeUpdated, Boolean isActive){
        this.userId = userId;
        this.organizationName = organizationName;
        this.website = website;
        this.regionsOfService = regionsOfService;
        this.titleInOrganization = titleInOrganization;
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
}
