package com.app.server.models.Preferences;

import com.app.server.models.UserInfo.UserType;

public enum Habit {
    //LIFESTYLE CHOICES
    HOUSE_HOLD_CHORES("Household chores", 1),
    BUSY_WORK_LIFE("Busy Work Life",2),
    SITTING_ALL_DAY("Sitting All Day",3),
    JUNK_FOOD_BINGING("Hogging Junk Food",4),
    SMOKING("Smoking", 5),
    WORK_LATE("Working Late",6),
    EXCESSIVE_ONLINE_PRESENCE("Spending lot of time online",7),
    EXCESSIVE_GAMING("Lot of Gaming",8),
    SHOPPING_MANIAC("Lot of Shopping",9),

    //DRINKING AND EATING
    DRINKING_SODA("Drinking Coffee or Tea", 10),
    DRINKING_COFFEE("Drinking Coffee or Tea", 11),
    DRINKING_WINE("Drinking Liquor", 12),
    DRINKING_HEALTHY("Drinking Healthy Drinks", 13),
    EATING_HEALTHY("Eating Healthy Foods", 14),
    EATING_STREET_FOOD("Eating Street Food", 15),
    EATING_OUT("Eating Out",16),
    COOKING("Cooking",17),

    //FITNESS HABITS
    RUNNING("Drinking Coffee", 18),
    EXERCISE("Drinking Coffee", 19),
    SPORTS("Playing Sports", 20),
    SWIMMING("Swimming", 21),
    DRIVING("Driving",22);

    private final String habit;
    private final Integer id;

    Habit(String habit, Integer id) {
        this.habit = habit;
        this.id = id;
    }

    public String getHabit() {
        return habit;
    }

    public Integer getId() {
        return id;
    }

    public static Habit getHabit(int value) {
        for(Habit e: Habit.values()) {
            if(e.id == value) {
                return e;
            }
        }
        return null;// not found
    }
}

