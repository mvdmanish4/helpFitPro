package com.app.server.util.parser;

import com.app.server.models.Event.Event;
import com.app.server.models.Preferences.RegionsOfService;
import com.app.server.models.User.EventOrganizer;
import com.app.server.models.User.User;
import com.app.server.models.UserInfo.UserType;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.plaf.synth.Region;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventOrganizerDocumentParser {

    public static Boolean isUserTypeSupported(JSONObject json){
        if(UserType.getUserType(json.getInt("UserType")) != null){
            return true;
        }
        return false;
    }

    public static EventOrganizer convertDocumentToEventOrganizer(Document item){

        List<RegionsOfService> listItems = (List<RegionsOfService>) item.get("regionsOfService");
        EventOrganizer eventOrganizer = new EventOrganizer(
                item.getString("userId"),
                item.getString("organizationName"),
                item.getString("website"),
                listItems,
                item.getString("titleInOrganization"),
                item.getString("timeCreated"),
                item.getString("timeUpdated"),
                item.getBoolean("isActive")
        );
        eventOrganizer.setId(item.getObjectId("_id").toString());
        return eventOrganizer;
    }

    public static EventOrganizer convertJsonToEventOrganizer(JSONObject json, String userId){
        ArrayList<RegionsOfService> regionList = new ArrayList<RegionsOfService>();
        JSONArray jsonArray = json.getJSONArray("regionsOfService");
        if(jsonArray != null){
            int len = jsonArray.length();
            for(int i=0;i<len;i++){
                regionList.add(RegionsOfService.getRegionOfService(Integer.parseInt(jsonArray.get(i).toString())));
            }
        }

        EventOrganizer eventOrganizer = new EventOrganizer(
                userId,
                json.getString("organizationName"),
                json.getString("website"),
                regionList,
                json.getString("titleInOrganization"),
                String.valueOf(Instant.now().getEpochSecond()),
                String.valueOf(Instant.now().getEpochSecond()),
                json.getBoolean("isActive"));
        return eventOrganizer;
    }

    public static Document convertJsonToEventOrganizerDocument(JSONObject json, String userId){
        Document doc = new Document();
        doc.append("userId", userId);
        if (json.has("organizationName"))
            doc.append("organizationName",json.getString("organizationName"));
        if (json.has("website"))
            doc.append("website",json.getString("website"));
        if (json.has("regionsOfService")){
            ArrayList<String> regionList = new ArrayList<String>();
            JSONArray jsonArray = json.getJSONArray("regionsOfService");
            if(jsonArray != null){
                int len = jsonArray.length();
                for(int i=0;i<len;i++){
                    regionList.add(jsonArray.get(i).toString());
                }
            }
            doc.append("regionsOfService",regionList);
        }
        if (json.has("titleInOrganization"))
            doc.append("titleInOrganization",json.getString("titleInOrganization"));
        if (json.has("timeCreated")) {
            doc.append("timeCreated",json.getString("timeCreated"));
        } else {
            doc.append("timeCreated",String.valueOf(Instant.now().getEpochSecond()));
        }
        doc.append("timeUpdated",String.valueOf(Instant.now().getEpochSecond()));
        return doc;
    }
}
