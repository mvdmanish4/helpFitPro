package com.app.server.util.parser;

import com.app.server.models.Event.Event;
import com.app.server.models.Preferences.Ailment;
import com.app.server.models.Preferences.Habit;
import com.app.server.models.Preferences.Interest;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventDocumentParser {

	public static Document convertJsonToEventDocument(JSONObject json){
		Document doc = new Document();
		if (json.has("name"))
			doc.append("name",json.getString("name"));
		if (json.has("description"))
			doc.append("description",json.getString("description"));
		if (json.has("venue"))
			doc.append("venue",json.getString("venue"));
		if (json.has("regionOfVenue"))
			doc.append("regionOfVenue",json.getString("regionOfVenue"));
		if (json.has("address"))
			doc.append("address",json.getString("address"));
		if (json.has("startDate"))
			doc.append("startDate",json.getString("startDate"));
		if (json.has("endDate"))
			doc.append("endDate",json.getString("endDate"));
		if (json.has("startTime"))
			doc.append("startTime",json.getString("startTime"));
		if (json.has("endTime"))
			doc.append("endTime",json.getString("endTime"));
		if (json.has("organizerId"))
			doc.append("organizerId",json.getString("organizerId"));
		if (json.has("ticketPrice"))
			doc.append("ticketPrice",json.getString("ticketPrice"));

		if (json.has("ailmentTags")){
			ArrayList<Ailment> ailmentTags = new ArrayList<Ailment>();
			JSONArray ailmentArray = json.getJSONArray("ailmentTags");
			if(ailmentArray != null){
				int len = ailmentArray.length();
				for(int i=0;i<len;i++){
					ailmentTags.add(Ailment.getAilment(ailmentArray.getInt(i)));
				}
			}
			doc.append("ailmentTags",ailmentTags);
		}


		if (json.has("interestTags")){
			ArrayList<Interest> interestTags = new ArrayList<Interest>();
			JSONArray interestArray = json.getJSONArray("interestTags");
			if(interestArray != null){
				int len = interestArray.length();
				for(int i=0;i<len;i++){
					interestTags.add(Interest.getInterest(interestArray.getInt(i)));
				}
			}
			doc.append("interestTags",interestTags);
		}

		if (json.has("habitTags")){
			ArrayList<Habit> habitTags = new ArrayList<Habit>();
			JSONArray habitArray = json.getJSONArray("habitTags");
			if(habitArray != null){
				int len = habitArray.length();
				for(int i=0;i<len;i++){
					habitTags.add(Habit.getHabit(habitArray.getInt(i)));
				}
			}
			doc.append("habitTags",habitTags);
		}

		if (json.has("isActive"))
			doc.append("isActive",json.getBoolean("isActive"));
		if (json.has("isOpen"))
			doc.append("isOpen",json.getBoolean("isOpen"));
		if (json.has("timeCreated"))
			doc.append("timeCreated",json.getString("timeCreated"));
		if (json.has("timeUpdated"))
			doc.append("timeUpdated",(new Date()).toString());

		return doc;
	}

	public static Event convertDocumentToEvent(Document item){

		List<Integer> ailmentInts = (List<Integer>) item.get("ailmentTags");
		List<Ailment> ailmentTags = new ArrayList<Ailment>();
		for(Integer ailment: ailmentInts){
			ailmentTags.add(Ailment.getAilment(ailment));
		}

		List<Integer> interestInts = (List<Integer>) item.get("ailmentTags");
		List<Interest> interestTags = new ArrayList<Interest>();
		for(Integer interest: interestInts){
			interestTags.add(Interest.getInterest(interest));
		}


		List<Integer> habitInts = (List<Integer>) item.get("ailmentTags");
		List<Habit> habitTags = new ArrayList<Habit>();
		for(Integer habit: habitInts){
			habitTags.add(Habit.getHabit(habit));
		}

		Event event = new Event(
				item.getString("name"),
				item.getString("description"),
				item.getString("venue"),
				item.getString("regionOfVenue"),
				item.getString("address"),
				item.getString("startDate"),
				item.getString("endDate"),
				item.getString("startTime"),
				item.getString("endTime"),
				item.getString("organizerId"),
				item.getString("ticketPrice"),
				ailmentTags,
				interestTags,
				habitTags,
				item.getBoolean("isActive"),
				item.getBoolean("isOpen"),
				item.getString("timeCreated"),
				item.getString("timeUpdated"));
		event.setId(item.getObjectId("_id").toString());
		return event;
	}

	public static Document convertEventToDocument(Event event){
		Document doc = new Document("name", event.getName())
				.append("description", event.getDescription())
				.append("venue", event.getVenue())
				.append("regionOfVenue", event.getRegionOfVenue())
				.append("address", event.getAddress())
				.append("startDate", event.getStartDate())
				.append("endDate", event.getEndDate())
				.append("startTime", event.getStartTime())
				.append("endTime", event.getEndTime())
				.append("organizerId", event.getOrganizerId())
				.append("ticketPrice", event.getTicketPrice())
				.append("ailmentTags", event.getAilmentTags())
				.append("interestTags", event.getInterestTags())
				.append("habitTags", event.getHabitTags())
				.append("isActive", event.getIsActive())
				.append("isOpen", event.getIsOpen())
				.append("timeCreated", event.getTimeCreated())
				.append("timeUpdated", event.getTimeUpdated());
		return doc;
	}

	public static Event convertJsonToEvent(JSONObject json){

		ArrayList<Ailment> ailmentTags = new ArrayList<Ailment>();
		JSONArray ailmentArray = json.getJSONArray("ailmentTags");
		if(ailmentArray != null){
			int len = ailmentArray.length();
			for(int i=0;i<len;i++){
				ailmentTags.add(Ailment.getAilment(ailmentArray.getInt(i)));
			}
		}

		ArrayList<Interest> interestTags = new ArrayList<Interest>();
		JSONArray interestArray = json.getJSONArray("interestTags");
		if(interestArray != null){
			int len = interestArray.length();
			for(int i=0;i<len;i++){
				interestTags.add(Interest.getInterest(interestArray.getInt(i)));
			}
		}

		ArrayList<Habit> habitTags = new ArrayList<Habit>();
		JSONArray habitArray = json.getJSONArray("habitTags");
		if(habitArray != null){
			int len = habitArray.length();
			for(int i=0;i<len;i++){
				habitTags.add(Habit.getHabit(habitArray.getInt(i)));
			}
		}

		Event event = new Event( json.getString("name"),
				json.getString("description"),
				json.getString("venue"),
				json.getString("regionOfVenue"),
				json.getString("address"),
				json.getString("startDate"),
				json.getString("endDate"),
				json.getString("startTime"),
				json.getString("endTime"),
				json.getString("organizerId"),
				json.getString("ticketPrice"),
				ailmentTags,
				interestTags,
				habitTags,
				json.getBoolean("isActive"),
				json.getBoolean("isOpen"),
				(new Date()).toString(),
				(new Date()).toString());
		return event;
	}
}
