package com.app.server.services;

import com.app.server.http.exceptions.APPBadRequestException;
import com.app.server.http.exceptions.APPInternalServerException;
import com.app.server.http.exceptions.APPUnauthorizedException;
import com.app.server.models.HealthRegime.Regime;
import com.app.server.models.HealthRegime.RegimeProgram;
import com.app.server.util.MongoPool;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public ArrayList<RegimeProgram> getUserRegimeProgram(String id){
        ArrayList<RegimeProgram> regimeList = new ArrayList<RegimeProgram>();

        FindIterable<Document> results = this.regimeProgramCollection.find();
        if (results == null) {
            return regimeList;
        }
        for (Document item : results) {
            RegimeProgram regimeProgram = convertDocumentToRegimeProgram(item);
            if(regimeProgram.getUserID().equals(id)){
                regimeList.add(regimeProgram);
            }
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

    public RegimeProgram create(Object request) {
        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));
            RegimeProgram regimeProgram = convertJsonToRegimeProgram(json);
            Document doc = convertRegimeProgramToDocument(regimeProgram);
            regimeProgramCollection.insertOne(doc);
            ObjectId id = (ObjectId)doc.get( "_id" );
            regimeProgram.setId(id.toString());
            return regimeProgram;
        } catch(JsonProcessingException e) {
            System.out.println("Failed to create a document");
            return null;
        } catch(APPBadRequestException e) {
            throw new APPBadRequestException(33, e.getMessage());
        } catch(APPUnauthorizedException e) {
            throw new APPUnauthorizedException(34, e.getMessage());
        } catch(Exception e) {
            System.out.println("EXCEPTION!!!!");
            e.printStackTrace();
            throw new APPInternalServerException(99, e.getMessage());
        }
    }

    private Document convertRegimeProgramToDocument(RegimeProgram regimeProgram){
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

    private RegimeProgram convertJsonToRegimeProgram(JSONObject json){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        RegimeProgram regimeProgram = null;
        try {
            regimeProgram = new RegimeProgram(json.getString("userID"),
                    jsonArraytoList(json.getJSONArray("regimesID")),
                    new SimpleDateFormat("dd/MM/yyyy").parse(json.getString("recommendedDate")),
                    jsonArraytoList(json.getJSONArray("ailmentTags")),
                    jsonArraytoList(json.getJSONArray("insterestTags")),
                    jsonArraytoList(json.getJSONArray("habitsTags")),
                    json.getString("isActive"),
                    json.getString("isFulfilled"),
                    json.getDouble("durationWeeks"),
                    new SimpleDateFormat("dd/MM/yyyy").parse(json.getString("startDate")),
                    new SimpleDateFormat("dd/MM/yyyy").parse(json.getString("endDate")),
                    new Date(),
                    new Date());
            regimeProgram.setId(json.getString("_id"));
        }catch(Exception e){}
        return regimeProgram;
    }

    private RegimeProgram convertDocumentToRegimeProgram(Document item) {
        List<String> regimes = (List<String>) item.get("regimesID");
        List<String> ailments = (List<String>) item.get("ailmentsAddressed");
        List<String> interests = (List<String>) item.get("interestsAddressed");
        List<String> habits = (List<String>) item.get("habitsAddressed");
        RegimeProgram regimeProgram = new RegimeProgram(
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
        regimeProgram.setId(item.getObjectId("_id").toString());
        return regimeProgram;
    }

    public Object delete(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        regimeProgramCollection.deleteOne(query);
      return new JSONObject();
    }

    private List<String> jsonArraytoList(JSONArray jsonArray){
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
