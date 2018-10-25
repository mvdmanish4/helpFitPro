package com.app.server.models.UserInfo;

public enum UserType {

    //WEST_COAST
    FITNESS_USER("Fitness User", 1),
    EXPERT("Expert User",2),
    EVENT_ORGANIZER("Event Organizer",3),
    ADMIN("Admin",4);

    private final String userType;
    private final Integer id;

    UserType(String userType, Integer id) {
        this.userType = userType;
        this.id = id;
    }

    public String getUserType() {
        return this.userType;
    }

    public Integer getId() {
        return this.id;
    }

    public static UserType getUserType(int value) {
        for(UserType e: UserType.values()) {
            if(e.id == value) {
                return e;
            }
        }
        return null;// not found
    }
}