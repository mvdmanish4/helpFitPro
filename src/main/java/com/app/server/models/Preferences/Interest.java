package com.app.server.models.Preferences;

public enum Interest {

    //Arts
    VISUAL_ARTS("Visual Arts, Painting, Gallery", 1),
    INTERIOR_DESIGN("Interior Design",2),
    CLASSICAL_MUSIC("Classical Music",3),
    MUSICAL_CONCERTS("Musical Concerts",4),
    DRAMA_SKITS("Plays, Dramas and Skits", 5),
    DANCING("Dancing",6),
    INSTRUMENTALS("Instrumental Music",7),
    DESIGN("Traditional and Modern Design",8),
    ANIMATION("Animation Arts",9),

    //FITNESS
    YOGA("Yoga", 10),
    RUNNING("Running", 11),
    SWIMMING("Swimming", 12),
    ZUMBA_DANCE("Zumba Dance", 13),
    GYM("Going to Gym", 14),
    MARTIAL_ARTS("Martial Arts", 15),
    HIKING("Hiking",16),
    SPORTS("Sports and Games",17),

    //LIFESTYLE
    TRAVELLING("Drinking Coffee", 18),
    BEACHES("Playing Sports", 19),
    MOUNTAINS("Swimming", 20),
    DRIVING("Driving",21),
    RACING("Racing",22),
    BOWLING("Bowling", 23),
    PARTY("Party",24),
    GAMBLING("Gambling",25);

    private final String interest;
    private final Integer id;

    Interest(String interest, Integer id) {
        this.interest = interest;
        this.id = id;
    }

    public String getInterest() {
        return this.interest;
    }
    public Integer getId() {
        return this.id;
    }

    public static Interest getInterest(int value) {
        for(Interest e: Interest.values()) {
            if(e.id == value) {
                return e;
            }
        }
        return null;// not found
    }
}
