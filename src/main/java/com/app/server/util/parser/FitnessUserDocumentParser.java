package com.app.server.util.parser;

import com.app.server.models.Preferences.Ailment;
import com.app.server.models.Preferences.Habit;
import com.app.server.models.Preferences.Interest;
import com.app.server.models.User.FitnessUser;
import com.app.server.models.UserInfo.UserType;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FitnessUserDocumentParser {

    public static FitnessUser convertDocumentToFitnessUser(Document item){

        List<Integer> ailmentInts = (List<Integer>) item.get("ailmentTags");
        List<Ailment> ailmentTags = new ArrayList<Ailment>();
        for(Integer ailment: ailmentInts){
            ailmentTags.add(Ailment.getAilment(ailment));
        }

        List<Integer> interestInts = (List<Integer>) item.get("interestTags");
        List<Interest> interestTags = new ArrayList<Interest>();
        for(Integer interest: interestInts){
            interestTags.add(Interest.getInterest(interest));
        }


        List<Integer> habitInts = (List<Integer>) item.get("habitTags");
        List<Habit> habitTags = new ArrayList<Habit>();
        for(Integer habit: habitInts){
            habitTags.add(Habit.getHabit(habit));
        }

        FitnessUser fitnessUser = new FitnessUser(
                item.getString("userId"),
                item.getString("gender"),
                item.getString("height"),
                item.getString("weight"),
                item.getString("city"),
                item.getString("state"),
                item.getString("zipCode"),
                ailmentTags,
                interestTags,
                habitTags,
                item.getBoolean("termsConsent"),
                item.getBoolean("receiveEmailNotifications"),
                item.getString("timeCreated"),
                item.getString("timeUpdated"),
                item.getBoolean("isActive")
        );
        fitnessUser.setId(item.getObjectId("_id").toString());
        return fitnessUser;
    }

    public static FitnessUser convertJsonToFitnessUser(JSONObject json, String userId){

        ArrayList<Ailment> ailmentTags = new ArrayList<Ailment>();
        JSONArray ailmentArray = json.getJSONArray("ailmentTags");
        if(ailmentArray != null){
            int len = ailmentArray.length();
            for(int i=0;i<len;i++){
                ailmentTags.add(Ailment.getAilment(ailmentArray.getInt(i)));
            }
        }

        ArrayList<Interest> interestTags = new ArrayList<Interest>();
        JSONArray interestArray = json.getJSONArray("interestTags");
        if(interestArray != null){
            int len = interestArray.length();
            for(int i=0;i<len;i++){
                interestTags.add(Interest.getInterest(interestArray.getInt(i)));
            }
        }

        ArrayList<Habit> habitTags = new ArrayList<Habit>();
        JSONArray habitArray = json.getJSONArray("habitTags");
        if(habitArray != null){
            int len = habitArray.length();
            for(int i=0;i<len;i++){
                habitTags.add(Habit.getHabit(habitArray.getInt(i)));
            }
        }

        FitnessUser fitnessUser = new FitnessUser(
                userId,
                json.getString("gender"),
                json.getString("height"),
                json.getString("weight"),
                json.getString("city"),
                json.getString("state"),
                json.getString("zipCode"),
                ailmentTags,
                interestTags,
                habitTags,
                json.getBoolean("termsConsent"),
                json.getBoolean("receiveEmailNotifications"),
                String.valueOf(Instant.now().getEpochSecond()),
                String.valueOf(Instant.now().getEpochSecond()),
                json.getBoolean("isActive"));

        return fitnessUser;
    }

    public static Document convertJsonToFitnessUserDocument(JSONObject json, String userId){
        Document doc = new Document();
        doc.append("userId", userId);
        if (json.has("gender"))
            doc.append("gender",json.getString("gender"));
        if (json.has("height"))
            doc.append("height",json.getString("height"));
        if (json.has("weight"))
            doc.append("weight",json.getString("weight"));
        if (json.has("city"))
            doc.append("city",json.getString("city"));
        if (json.has("state"))
            doc.append("state",json.getString("state"));
        if (json.has("zipCode"))
            doc.append("zipCode",json.getString("zipCode"));

        if (json.has("ailmentTags")){
            ArrayList<Ailment> ailmentTags = new ArrayList<Ailment>();
            JSONArray ailmentArray = json.getJSONArray("ailmentTags");
            if(ailmentArray != null){
                int len = ailmentArray.length();
                for(int i=0;i<len;i++){
                    ailmentTags.add(Ailment.getAilment(ailmentArray.getInt(i)));
                }
            }
            doc.append("ailmentTags",ailmentTags);
        }


        if (json.has("interestTags")){
            ArrayList<Interest> interestTags = new ArrayList<Interest>();
            JSONArray interestArray = json.getJSONArray("interestTags");
            if(interestArray != null){
                int len = interestArray.length();
                for(int i=0;i<len;i++){
                    interestTags.add(Interest.getInterest(interestArray.getInt(i)));
                }
            }
            doc.append("interestTags",interestTags);
        }

        if (json.has("habitTags")){
            ArrayList<Habit> habitTags = new ArrayList<Habit>();
            JSONArray habitArray = json.getJSONArray("habitTags");
            if(habitArray != null){
                int len = habitArray.length();
                for(int i=0;i<len;i++){
                    habitTags.add(Habit.getHabit(habitArray.getInt(i)));
                }
            }
            doc.append("habitTags",habitTags);
        }

        if (json.has("termsConsent"))
            doc.append("termsConsent",json.getBoolean("termsConsent"));
        if (json.has("receiveEmailNotifications"))
            doc.append("receiveEmailNotifications",json.getBoolean("receiveEmailNotifications"));
        if (json.has("timeCreated")) {
            doc.append("timeCreated",json.getString("timeCreated"));
        } else {
            doc.append("timeCreated",String.valueOf(Instant.now().getEpochSecond()));
        }
        doc.append("timeUpdated",String.valueOf(Instant.now().getEpochSecond()));
        if (json.has("isActive"))
            doc.append("isActive",json.getBoolean("isActive"));
        return doc;
    }
}
