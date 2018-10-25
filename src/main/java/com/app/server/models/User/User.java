package com.app.server.models.User;

import com.app.server.models.UserInfo.UserType;

public class User {

    private String id=null;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private UserType userType;
    private String emailAddress;
    private String phoneNumber;
    private Boolean isActive;
    private String timeCreated;
    private String timeUpdated;
    private Boolean isAdmin;

    public User(String firstName, String lastName, String dateOfBirth, UserType userType, String emailAddress,
                String phoneNumber, Boolean isActive, String timeCreated, String timeUpdated, Boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.userType = userType;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.isAdmin = isAdmin;
    }

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getDateOfBirth(){
       return dateOfBirth;
    }

    public UserType getUserType(){
        return userType;
    }

    public String getEmailAddress(){
        return emailAddress;
    }

    public String getPhoneNumber(){
        return phoneNumber;
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

    public Boolean getIsAdmin(){
        return isAdmin;
    }
}
