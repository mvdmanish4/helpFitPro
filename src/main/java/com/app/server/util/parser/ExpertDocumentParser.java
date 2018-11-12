package com.app.server.util.parser;

import com.app.server.models.Preferences.Ailment;
import com.app.server.models.Preferences.Habit;
import com.app.server.models.Preferences.Interest;
import com.app.server.models.User.Expert;
import com.app.server.models.UserInfo.Certification;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ExpertDocumentParser {

    public static Expert convertDocumentToExpert(Document item){

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

        Expert expert = new Expert (
                item.getString("userId"),
                item.getString("cityOfService"),
                item.getString("regionOfService"),
                ailmentTags,
                interestTags,
                item.getBoolean("termsConsent"),
                item.getString("timeCreated"),
                item.getString("timeUpdated"),
                item.getBoolean("isActive")
        );
        expert.setId(item.getObjectId("_id").toString());
        return expert;
    }

    public static Expert convertJsonToExpert(JSONObject json, String userId){

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

        Expert expert = new Expert(
                userId,
                json.getString("cityOfService"),
                json.getString("regionOfService"),
                ailmentTags,
                interestTags,
                json.getBoolean("termsConsent"),
                json.getString("timeCreated"),
                json.getString("timeUpdated"),
                json.getBoolean("isActive"));

        return expert;
    }

    //TODO: Enable Certifications for final project submission
    public static Document convertJsonToExpertDocument(JSONObject json, String userId){
        Document doc = new Document();
        doc.append("userId", userId);
        if (json.has("cityOfService"))
            doc.append("cityOfService",json.getString("cityOfService"));
        if (json.has("regionOfService"))
            doc.append("regionOfService",json.getString("regionOfService"));

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

        if (json.has("termsConsent"))
            doc.append("termsConsent",json.getBoolean("termsConsent"));
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
