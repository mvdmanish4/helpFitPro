package com.app.server.services;

import com.app.server.models.HealthRegime.Regime;
import com.app.server.models.HealthRegime.RegimeProgram;
import com.app.server.util.MongoPool;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class RegimeProgramService {

    private static RegimeProgramService self;
    private RegimeService regimeService;
    private ObjectWriter ow;
    private MongoCollection<Document> regimeProgramCollection = null;

    private RegimeProgramService() {
        this.regimeProgramCollection = MongoPool.getInstance().getCollection("regimeProgram");
        regimeService = RegimeService.getInstance();
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    public static RegimeProgramService getInstance(){
        if (self == null)
            self = new RegimeProgramService();
        return self;
    }

    public ArrayList<RegimeProgram> getAll() {
        ArrayList<RegimeProgram> regimeList = new ArrayList<RegimeProgram>();

        FindIterable<Document> results = this.regimeProgramCollection.find();
        if (results == null) {
            return regimeList;
        }
        for (Document item : results) {
            RegimeProgram regimeProgram = convertDocumentToRegimeProgram(item);
            regimeList.add(regimeProgram);
        }
        return regimeList;
    }

    public RegimeProgram getOne(String id) {

        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        Document item = regimeProgramCollection.find(query).first();
        if (item == null) {
            return  null;
        }
        return  convertDocumentToRegimeProgram(item);
    }

    public Object updateRegimeAsSubResource(String id, String idtwo, Object request){
        List<String> regimes = getOne(id).getRegimesID();
        return regimeService.getRegimeForOneRegimeProgramWhenSubresource(regimes, idtwo, request);
    }


    public List<Regime> getRegimeinRegimeProgram(String id){
       return regimeService.getRegimeForOneRegimeProgram(getOne(id).getRegimesID());
    }


    private RegimeProgram convertDocumentToRegimeProgram(Document item) {
        List<String> regimes = (List<String>) item.get("regimesID");
        List<String> ailments = (List<String>) item.get("ailmentsAddressed");
        List<String> interests = (List<String>) item.get("interestsAddressed");
        List<String> habits = (List<String>) item.get("habitsAddressed");
        RegimeProgram regimeProgram = new RegimeProgram(item.getObjectId("_id").toString(),
                item.getString("userID"),
                regimes,
                item.getDate("recommendedDate"),
                ailments,
                interests,
                habits,
                item.getString("isActive"),
                item.getString("isFulfilled"),
                item.getDouble("durationWeeks"),
                item.getDate("startDate"),
                item.getDate("endDate"),
                item.getDate("timeCreated"),
                item.getDate("timeUpdated"));
        return regimeProgram;
    }

}
