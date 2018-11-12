package com.app.server.util.parser;

import com.app.server.models.User.User;
import com.app.server.models.UserInfo.UserType;
import org.bson.Document;
import org.json.JSONObject;

import java.util.Date;

public class UserDocumentParser {

    public static Boolean isUserTypeSupported(JSONObject json){
        if(UserType.getUserType(json.getInt("UserType")) != null){
            return true;
        }
        return false;
    }

    public static User convertDocumentToUser(Document item){
        User user = new User(
                item.getString("firstName"),
                item.getString("lastName"),
                item.getString("dateOfBirth"),
                UserType.getUserType(item.getDouble("UserType").intValue()),
                item.getString("emailAddress"),
                item.getString("phoneNumber"),
                item.getBoolean("isActive"),
                item.getString("timeCreated"),
                item.getString("timeUpdated"),
                item.getBoolean("isAdmin")
        );
        user.setId(item.getObjectId("_id").toString());
        return user;
    }

    public static Document convertUserToDocument(User user){
        Document doc = new Document("firstName", user.getFirstName())
                .append("lastName", user.getLastName())
                .append("dateOfBirth", user.getDateOfBirth())
                .append("UserType", user.getUserType().getId())
                .append("emailAddress", user.getEmailAddress())
                .append("phoneNumber", user.getPhoneNumber())
                .append("isActive", user.getIsActive())
                .append("timeCreated", user.getTimeCreated())
                .append("timeUpdated", user.getTimeUpdated())
                .append("isAdmin", user.getIsAdmin());
        return doc;
    }

    public static User convertJsonToUser(JSONObject json){
        User user = new User( json.getString("firstName"),
                json.getString("lastName"),
                json.getString("dateOfBirth"),
                UserType.getUserType(json.getInt("UserType")),
                json.getString("emailAddress"),
                json.getString("phoneNumber"),
                json.getBoolean("isActive"),
                (new Date()).toString(),
                (new Date()).toString(),
                json.getBoolean("isAdmin"));
        return user;
    }

    public static Document convertJsonToUserDocument(JSONObject json){
        Document doc = new Document();
        if (json.has("firstName"))
            doc.append("firstName",json.getString("firstName"));
        if (json.has("lastName"))
            doc.append("lastName",json.getString("lastName"));
        if (json.has("dateOfBirth"))
            doc.append("dateOfBirth",json.getString("dateOfBirth"));
        if (json.has("UserType"))
            doc.append("UserType",json.getInt("UserType"));
        if (json.has("emailAddress"))
            doc.append("emailAddress",json.getString("emailAddress"));
        if (json.has("phoneNumber"))
            doc.append("phoneNumber",json.getString("phoneNumber"));
        if (json.has("isActive"))
            doc.append("isActive",json.getBoolean("isActive"));
        if (json.has("timeCreated"))
            doc.append("timeCreated",json.getString("timeCreated"));
        if (json.has("timeUpdated"))
            doc.append("timeUpdated",(new Date()).toString());
        if (json.has("isAdmin"))
            doc.append("isAdmin",json.getBoolean("isAdmin"));
        return doc;
    }
}
