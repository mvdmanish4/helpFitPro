package com.app.server.util.parser;

import com.app.server.models.HealthRegime.RegimeProgram;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegimeProgramParser {

    public static Document convertRegimeProgramToDocument(RegimeProgram regimeProgram){
        Document doc = new Document("userID",regimeProgram.getUserID())
                .append("regimesID", regimeProgram.getRegimesID())
                .append("recommendedDate", regimeProgram.getRecommendedDate())
                .append("ailmentTags", regimeProgram.getAilmentsAddressed())
                .append("insterestTags", regimeProgram.getInterestsAddressed())
                .append("habitsTags",regimeProgram.getHabitsAddressed())
                .append("isActive", regimeProgram.getIsActive())
                .append("isFulfilled", regimeProgram.getIsFulfilled())
                .append("durationWeeks", regimeProgram.getDurationWeeks())
                .append("startDate", regimeProgram.getStartDate())
                .append("endDate", regimeProgram.getEndDate())
                .append("timeCreated", regimeProgram.getTimeCreated())
                .append("timeUpdated", regimeProgram.getTimeUpdated());
        return doc;
    }

    public static RegimeProgram convertJsonToRegimeProgram(JSONObject json){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        RegimeProgram regimeProgram = null;
        try {
            regimeProgram = new RegimeProgram(json.getString("userID"),
                    jsonArraytoList(json.getJSONArray("regimesID")),
                    json.getString("recommendedDate"),
                    jsonArraytoList(json.getJSONArray("ailmentTags")),
                    jsonArraytoList(json.getJSONArray("insterestTags")),
                    jsonArraytoList(json.getJSONArray("habitsTags")),
                    json.getString("isActive"),
                    json.getString("isFulfilled"),
                    json.getDouble("durationWeeks"),
                    String.valueOf(Instant.now().getEpochSecond()),
                    String.valueOf(Instant.now().getEpochSecond()),
                    json.has("timeCreated")?json.getString("isFulfilled"): String.valueOf(Instant.now().getEpochSecond()),
                    String.valueOf(Instant.now().getEpochSecond()));
            regimeProgram.setId(json.getString("_id"));
        }catch(Exception e){}
        return regimeProgram;
    }

    public static RegimeProgram convertDocumentToRegimeProgram(Document item) {
        List<String> regimes = (List<String>) item.get("regimesID");
        List<String> ailments = (List<String>) item.get("ailmentsAddressed");
        List<String> interests = (List<String>) item.get("interestsAddressed");
        List<String> habits = (List<String>) item.get("habitsAddressed");
        RegimeProgram regimeProgram = new RegimeProgram(
                item.getString("userID"),
                regimes,
                item.getString("recommendedDate"),
                ailments,
                interests,
                habits,
                item.getString("isActive"),
                item.getString("isFulfilled"),
                item.getDouble("durationWeeks"),
                item.getString("startDate"),
                item.getString("endDate"),
                item.getString("timeCreated"),
                item.getString("timeUpdated"));
        regimeProgram.setId(item.getObjectId("_id").toString());
        return regimeProgram;
    }

    public static List<String> jsonArraytoList(JSONArray jsonArray){
        ArrayList<String> list = new ArrayList<String>();
        if (jsonArray != null) {
            int len = jsonArray.length();
            for (int i=0;i<len;i++){
                list.add(jsonArray.get(i).toString());
            }
        }
        return list;
    }
}
