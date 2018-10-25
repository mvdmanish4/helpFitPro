package com.app.server.models.Preferences;

import com.app.server.models.UserInfo.UserType;

public enum Ailment {

    //DISEASES
    DIABETES("Diabetes", 1),
    CANCER("Cancer",2),
    HEART("Heart Diseases",3),
    ALZHEIMER("Alzheimer's condition",4),
    RESPIRATORY("Respiratory diseases", 5),
    KIDNEY("Kidney Diseases",6),
    LIVER_DISEASES("Liver Diseases",7),

    //DISORDERS
    OBESITY("Obesity", 8),
    UNFIT("Not in Good shape", 9),
    ALCOHOLIC("Alcohol Addiction", 10),
    SMOKE_ADDICTION("Smoke Addiction", 11),
    DEPRESSION("Depression", 12),

    //DISABILITY
    LIMB_REATED("Limb Related Disability", 13),
    MUSCULAR("Muscular Dystrophy", 14),
    MENTAL_DISABILITY("Mental Health", 15);

    private final String ailment;
    private final Integer id;

    Ailment(String ailment, Integer id) {
        this.ailment = ailment;
        this.id = id;
    }

    public String getAilment() {
        return this.ailment;
    }
    public Integer getId() {
        return this.id;
    }

    public static Ailment getAilment(int value) {
        for(Ailment e: Ailment.values()) {
            if(e.id == value) {
                return e;
            }
        }
        return null;// not found
    }
}
