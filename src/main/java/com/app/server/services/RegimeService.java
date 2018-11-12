package com.app.server.services;

import com.app.server.http.exceptions.APPUnauthorizedException;
import com.app.server.models.HealthRegime.Regime;
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
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.core.HttpHeaders;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegimeService {

    private static RegimeService self;
    private ObjectWriter ow;
    private MongoCollection<Document> regimeCollection = null;

    private RegimeService() {
        this.regimeCollection = MongoPool.getInstance().getCollection("regime");
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    }

    public static RegimeService getInstance(){
        if (self == null)
            self = new RegimeService();
        return self;
    }

    public ArrayList<Regime> getAll() {
        ArrayList<Regime> regimeList = new ArrayList<Regime>();

            FindIterable<Document> results = this.regimeCollection.find();
            if (results == null) {
                return regimeList;
            }
            for (Document item : results) {
                Regime regime = convertDocumentToRegime(item);
                regimeList.add(regime);
            }
        return regimeList;
    }

    private void checkAuthentication(HttpHeaders headers,String id) throws Exception{
        List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        if (authHeaders == null)
            throw new APPUnauthorizedException(70,"No Authorization Headers");
        String token = authHeaders.get(0);
        //String clearToken = APPCrypt.decrypt(token);
        if (id.compareTo(token) != 0) {
            throw new APPUnauthorizedException(71,"Invalid token. Please try getting a new token");
        }
    }

    public List<Regime> getRegimeForOneRegimeProgram(List<String> regimesID){
        List<Regime> regimeList = new ArrayList<Regime>();
        FindIterable<Document> results = this.regimeCollection.find();
        if (results == null) {
            return regimeList;
        }
        for (Document item : results) {
            Regime regime = convertDocumentToRegime(item);
            if(regimesID.contains(regime.getHelpFitID())){
                regimeList.add(regime);
            }
        }
        return regimeList;
    }

    public Object getRegimeForOneRegimeProgramWhenSubresource(List<String> regimesID, String idTwo, Object request){
        List<Regime> regimeList = new ArrayList<Regime>();
        FindIterable<Document> results = this.regimeCollection.find();
        Regime regUpdate; Object obj = null;
        if (results == null) {
            return regimeList;
        }
        for (Document item : results) {
            Regime regime = convertDocumentToRegime(item);
            if(regimesID.contains(regime.getHelpFitID())){
                regimeList.add(regime);
            }
        }
        for (Regime reg: regimeList) {
            if(reg.getId().contains(idTwo)) {
                regUpdate = reg;
                obj = update(idTwo, request);
                break;
            }
        }
        return obj;
    }

    public Regime getOne(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        Document item = regimeCollection.find(query).first();
        if (item == null) {
            return  null;
        }
        return convertDocumentToRegime(item);
    }

    public Regime create(Object request) {
        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));
            Regime regime = convertJsonToRegime(json);
            Document doc = convertRegimeToDocument(regime);
            regimeCollection.insertOne(doc);
            ObjectId id = (ObjectId)doc.get( "_id" );
            regime.setId(id.toString());
            return regime;
        } catch(JsonProcessingException e) {
            System.out.println("Failed to create a document");
            return null;
        }
    }


    public Object update(String id, Object request) {
        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));

            BasicDBObject query = new BasicDBObject();
            query.put("_id", new ObjectId(id));

            Document doc = new Document();
            if (json.has("name"))
                doc.append("name",json.getString("name"));
            if (json.has("description"))
                doc.append("description",json.getString("description"));
            if (json.has("regimeType"))
                doc.append("regimeType",json.getString("regimeType"));
            if (json.has("timeRequiredWeeks"))
                doc.append("timeRequiredWeeks",json.getString("timeRequiredWeeks"));

            Document set = new Document("$set", doc);
            regimeCollection.updateOne(query,set);
            return request;
        } catch(JSONException e) {
            System.out.println("Failed to update a document");
            return null;
        } catch(JsonProcessingException e) {
            System.out.println("Failed to create a document");
            return null;
        }
    }

    public Object delete(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        regimeCollection.deleteOne(query);

        return new JSONObject();
    }

    private Document convertRegimeToDocument(Regime regime){
        Document doc = new Document("helpFitID", "reg")
                .append("name", regime.getName())
                .append("description", regime.getDescription())
                .append("regimeType", regime.getRegimeType())
                .append("timeRequiredWeeks", regime.getTimeRequiredWeeks())
                .append("ailmentTags", regime.getAilmentTags())
                .append("interestTags", regime.getInsterestTags())
                .append("habitsTags", regime.getHabitsTags())
                .append("isActive", regime.getIsActive()).append("timeCreated", new Date()).append("timeUpdated", regime.getTimeUpdated());
        return doc;
    }

    private Regime convertDocumentToRegime(Document item) {
        List<String> ailments = (List<String>) item.get("ailmentTags");
        List<String> interests = (List<String>) item.get("interestTags");
        List<String> habits = (List<String>) item.get("habitsTags");
        Regime regime = new Regime(item.getObjectId("_id").toString(),item.getString("helpFitID"),
                item.getString("name"),
                item.getString("description"),
                item.getString("regimeType"),
                item.getString("timeRequiredWeeks"),
                ailments,
                interests,
                habits,
                item.getString("isActive"),
                item.getString("timeCreated"),
                item.getString("timeUpdated"));
        return regime;
    }

    private Regime convertJsonToRegime(JSONObject json){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Regime regime = new Regime( json.getString("id"),
                json.getString("helpFitID"),
                json.getString("name"),
                json.getString("description"),
                json.getString("regimeType"),
                json.getString("timeRequiredWeeks"),
                jsonArraytoList(json.getJSONArray("ailmentTags")),
                jsonArraytoList(json.getJSONArray("insterestTags")),
                jsonArraytoList(json.getJSONArray("habitsTags")),
                json.getString("isActive"),
                json.has("timeCreated")?json.getString("isFulfilled"): String.valueOf(Instant.now().getEpochSecond()),
                String.valueOf(Instant.now().getEpochSecond()));
        return regime;
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
