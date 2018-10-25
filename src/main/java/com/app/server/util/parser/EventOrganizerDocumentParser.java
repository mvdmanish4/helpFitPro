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

    public static Document convertEventOrganizerToDocument(EventOrganizer eventOrganizer){
        Document doc = new Document("userId", eventOrganizer.getUserId())
                .append("organizationName", eventOrganizer.getOrganizationName())
                .append("website", eventOrganizer.getWebsite())
                .append("regionsOfService", eventOrganizer.getRegionsOfService())
                .append("titleInOrganization", eventOrganizer.getTitleInOrganization())
                .append("timeCreated", eventOrganizer.getTimeCreated())
                .append("timeUpdated", eventOrganizer.getTimeUpdated())
                .append("isActive", eventOrganizer.getIsActive());
        return doc;
    }

    public static EventOrganizer convertJsonToEventOrganizer(JSONObject json){
        ArrayList<RegionsOfService> regionList = new ArrayList<RegionsOfService>();
        JSONArray jsonArray = json.getJSONArray("regionsOfService");
        if(jsonArray != null){
            int len = jsonArray.length();
            for(int i=0;i<len;i++){
                regionList.add(RegionsOfService.getRegionOfService(Integer.parseInt(jsonArray.get(i).toString())));
            }
        }

        EventOrganizer eventOrganizer = new EventOrganizer( json.getString("userId"),
                json.getString("organizationName"),
                json.getString("website"),
                regionList,
                json.getString("titleInOrganization"),
                (new Date()).toString(),
                (new Date()).toString(),
                json.getBoolean("isActive"));
        return eventOrganizer;
    }

    public static Document convertJsonToEventOrganizerDocument(JSONObject json){
        Document doc = new Document();
        if (json.has("userId"))
            doc.append("userId",json.getString("userId"));
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
        if (json.has("timeCreated"))
            doc.append("timeCreated",json.getString("timeCreated"));
        if (json.has("timeUpdated"))
            doc.append("timeUpdated",json.getString("timeUpdated"));
        return doc;
    }
}
