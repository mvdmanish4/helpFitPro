package com.app.server.models.Event;

import com.app.server.models.Preferences.Ailment;
import com.app.server.models.Preferences.Habit;
import com.app.server.models.Preferences.Interest;

import java.util.List;

public class Event {

    private String id = null;
    private String name;
    private String description;
    private String venue;
    private String regionOfVenue;
    private String address;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String organizerId;
    private String ticketPrice;
    private List<Ailment> ailmentTags;
    private List<Interest> interestTags;
    private List<Habit> habitTags;
    private Boolean isActive;
    private Boolean isOpen;
    private String timeCreated;
    private String timeUpdated;

    public Event(String name, String description, String venue, String regionOfVenue, String address, String startDate,
                 String endDate, String startTime, String endTime, String organizerId, String ticketPrice,
                 List<Ailment> ailmentTags, List<Interest> interestTags, List<Habit> habitTags, Boolean isActive, Boolean isOpen,
                 String timeCreated, String timeUpdated){
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.regionOfVenue = regionOfVenue;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.organizerId = organizerId;
        this.ticketPrice = ticketPrice;
        this.ailmentTags = ailmentTags;
        this.interestTags = interestTags;
        this.habitTags = habitTags;
        this.isActive = isActive;
        this.isOpen = isOpen;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String getVenue(){
        return venue;
    }

    public String getRegionOfVenue(){
        return regionOfVenue;
    }

    public String getAddress(){
        return address;
    }

    public String getStartDate(){
        return startDate;
    }

    public String getEndDate(){
        return endDate;
    }

    public String getStartTime(){
        return startTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public String getOrganizerId() { return organizerId; }

    public String getTicketPrice(){
        return ticketPrice;
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

    public Boolean getIsOpen(){
        return isOpen;
    }

    public List<Ailment> getAilmentTags(){
        return ailmentTags;
    }

    public List<Interest> getInterestTags(){
        return interestTags;
    }

    public List<Habit> getHabitTags(){
        return habitTags;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setRegionOfVenue(String regionOfVenue) {
        this.regionOfVenue = regionOfVenue;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setAilmentTags(List<Ailment> ailmentTags) {
        this.ailmentTags = ailmentTags;
    }

    public void setInterestTags(List<Interest> interestTags) {
        this.interestTags = interestTags;
    }

    public void setHabitTags(List<Habit> habitTags) {
        this.habitTags = habitTags;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public void setTimeUpdated(String timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

}
