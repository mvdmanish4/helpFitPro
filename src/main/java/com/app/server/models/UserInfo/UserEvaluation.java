package com.app.server.models.UserInfo;

import com.app.server.models.Event.Event;
import com.app.server.models.HealthRegime.RegimeProgram;

import java.util.ArrayList;

public class UserEvaluation {

    ArrayList<RegimeProgram> regimePrograms;
    ArrayList<Event> eventsRecommended;

    public UserEvaluation(ArrayList<RegimeProgram> regimePrograms, ArrayList<Event> eventsRecommended) {
        this.regimePrograms = regimePrograms;
        this.eventsRecommended = eventsRecommended;
    }

    public ArrayList<RegimeProgram> getRegimePrograms() {
        return regimePrograms;
    }

    public void setRegimePrograms(ArrayList<RegimeProgram> regimePrograms) {
        this.regimePrograms = regimePrograms;
    }

    public ArrayList<Event> getEventsRecommended() {
        return eventsRecommended;
    }

    public void setEventsRecommended(ArrayList<Event> eventsRecommended) {
        this.eventsRecommended = eventsRecommended;
    }
}
