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

        Expert expert = new Expert (
                item.getString("userId"),
                item.getString("cityOfService"),
                item.getString("regionOfService"),
                ailmentTags,
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

        Expert expert = new Expert(
                userId,
                json.getString("cityOfService"),
                json.getString("regionOfService"),
                ailmentTags,
                json.getBoolean("termsConsent"),
                String.valueOf(Instant.now().getEpochSecond()),
                String.valueOf(Instant.now().getEpochSecond()),
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
            ArrayList<Integer> ailmentTags = new ArrayList<Integer>();
            JSONArray ailmentArray = json.getJSONArray("ailmentTags");
            if(ailmentArray != null){
                int len = ailmentArray.length();
                for(int i=0;i<len;i++){
                    if(Ailment.getAilment(ailmentArray.getInt(i)) != null)
                        ailmentTags.add(ailmentArray.getInt(i));
                }
            }
            doc.append("ailmentTags",ailmentTags);
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
