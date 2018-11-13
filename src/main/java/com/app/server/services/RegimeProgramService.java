package com.app.server.services;

import com.app.server.models.HealthRegime.Regime;
import com.app.server.models.HealthRegime.RegimeProgram;
import com.app.server.util.MongoPool;
import com.app.server.util.parser.RegimeProgramParser;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.app.server.util.parser.RegimeProgramParser.convertDocumentToRegimeProgram;
import static com.app.server.util.parser.RegimeProgramParser.convertJsonToRegimeProgram;
import static com.app.server.util.parser.RegimeProgramParser.convertRegimeProgramToDocument;

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
            RegimeProgram regimeProgram = new RegimeProgramParser().convertDocumentToRegimeProgram(item);
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
        }
    }


    public Object delete(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        regimeProgramCollection.deleteOne(query);
      return new JSONObject();
    }

}
