package com.app.server.models.Session;

import com.app.server.models.User.User;
import com.app.server.util.APPCrypt;

import java.sql.Timestamp;

public class Session {

    String id;
    String firstName;
    String lastName;

    public Session(User user) throws Exception{
        Timestamp now = new Timestamp(System.currentTimeMillis());
        this.id = APPCrypt.encrypt(user.getId()+Long.toString(now.getTime())+Math.random());
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
