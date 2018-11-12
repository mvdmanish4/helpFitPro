package com.app.server.util.parser;

import com.app.server.models.User.User;
import com.app.server.models.UserInfo.UserType;
import com.app.server.util.APPCrypt;
import org.bson.Document;
import org.json.JSONObject;

import java.time.Instant;
import java.util.Date;

public class UserDocumentParser {

    public static Boolean isUserTypeSupported(JSONObject json){
        if(UserType.getUserType(json.getInt("userType")) != null){
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

    //Called on Create only
    public static User convertJsonToUser(JSONObject json, Boolean isAdmin){
        User user = new User( json.getString("firstName"),
                json.getString("lastName"),
                json.getString("dateOfBirth"),
                UserType.getUserType(json.getInt("UserType")),
                json.getString("emailAddress"),
                json.getString("phoneNumber"),
                json.getBoolean("isActive"),
                String.valueOf(Instant.now().getEpochSecond()),
                String.valueOf(Instant.now().getEpochSecond()),
                isAdmin);
        return user;
    }

    //Called on Create and Update
    public static Document convertJsonToUserDocument(JSONObject json, Boolean isAdmin) throws Exception {
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
        if (json.has("password"))
            doc.append("password", APPCrypt.encrypt(json.getString("password")));
        if (json.has("phoneNumber"))
            doc.append("phoneNumber",json.getString("phoneNumber"));
        if (json.has("isActive"))
            doc.append("isActive",json.getBoolean("isActive"));
        if (json.has("timeCreated")) {
            doc.append("timeCreated",json.getString("timeCreated"));
        } else {
            doc.append("timeCreated",String.valueOf(Instant.now().getEpochSecond()));
        }
        doc.append("timeUpdated",String.valueOf(Instant.now().getEpochSecond()));
        doc.append("isAdmin", isAdmin);
        return doc;
    }
}
