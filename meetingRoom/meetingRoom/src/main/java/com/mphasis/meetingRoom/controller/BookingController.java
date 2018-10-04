package com.mphasis.meetingRoom.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.mphasis.meetingRoom.model.BookingModel;
import com.mphasis.meetingRoom.model.RoomObject;

@RestController
//@RequestMapping(value="/ConferenceRoom")
public class BookingController {

	private Boolean rescheduleFlag= false;
	private String jsonFilePath = "C://Workspace//meetingRoom//meetingRoom//src//main//resources//json//";

	//RoomBooking
	@RequestMapping(value = "/ConferenceRoom", method = RequestMethod.POST)
	@ResponseBody()
	public BookingModel updateBookingDetails(@RequestBody BookingModel bm) throws IOException {

		// check filename with respect to booking date is already exist or not
		// Exist or not
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		Date date = bm.getDate();
		String dateformat = "Date" + dateFormat.format(date) + ".json";
		String relativePath = jsonFilePath + "" + dateformat;
		// File
		File file = new File(jsonFilePath + "" + dateformat);
		File dir = new File(jsonFilePath + "");
		System.out.println(jsonFilePath + "" + dateformat + " is file?" + file.isFile());
		// System.out.println("/meetingRoom/src/main/resources/json?"+dir.isDirectory());

		// If false Create a File else update the Existing File
		if (!file.isFile()) // File doesnt Exist Flow
		{

			creatingJson(bm, file, dateformat, relativePath);
		} else // file already exist flow
		{
			updatingJson(bm, file, dateformat, relativePath);

			JSONObject jobj = new JSONObject();
			jobj.put("Name", "Test");
		}
		return bm;

	}

	public void updatingJson(BookingModel bm, File file, String dateformat, String relativePath) // for
																									// already
																									// existing																								// File
	{
		System.out.println(111);
		JSONParser parser = new JSONParser();
		/* Get the file content into the JSONObject */
		Object obj;
		try {
			obj = parser.parse(new FileReader(jsonFilePath + "" + dateformat));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray arrObj = (JSONArray) jsonObject.get("MeetingRoom");
			String RoomName;
			Boolean Flag = false;
			int index = -1;
			// JSONObject jsonObject1 = (JSONObject)
			// jsonObject.get("MeetingRoom");

			for (int i = 0; i < arrObj.size(); i++) {
				JSONObject object = (JSONObject) arrObj.get(i);
				Set<String> keys = object.keySet();
				Iterator<String> it = keys.iterator();
				do {
					RoomName = it.next().toString();
					System.out.println(RoomName);
					Flag = RoomName.equals("Room" + bm.getRoomId());
					System.out.println(Flag);
				} while (it.hasNext());
				if (Flag) {
					index = i;
					break;
				}
				System.out.println("i am outside while loop");

			}

			if (Flag) {
				JSONObject object = (JSONObject) arrObj.get(index);
				object = (JSONObject) object.get("Room" + bm.getRoomId());

				for (String time : bm.getTime()) {
					if (object.get("slot" + time) == null) {
						object.put("slot" + time, bm.getUserId());
					}
					System.out.println("slot" + time + ":" + object.get("slot" + time));
				}
				System.out.println(jsonObject);
				FileWriter fw = new FileWriter(jsonFilePath + "" + dateformat);
				fw.write(jsonObject.toJSONString());
				fw.close();
				System.out.println("Successfully Copied JSON Object to File...");
				System.out.println("\nJSON Object: " + jsonObject);
				// break;
			} else {

				// Left for the future
				System.out.println("111111111111111111111111111111");
				System.out.println("Room Doesnt Exist create a Room and add time slots");

				JSONObject roomObj = new JSONObject();
				JSONObject roomMeeting = new JSONObject();
				roomMeeting.put("Room" + bm.getRoomId(), roomObj);
				arrObj.add(roomMeeting);

				FileWriter fw = new FileWriter(jsonFilePath + "" + dateformat);
				fw.write(jsonObject.toJSONString());
				fw.close();

				System.out.println("Successfully Updated Room in JSON Object to File...");
				System.out.println("\nJSON Object: " + jsonObject);
				BookingModel bm2 = bm;
				// nested calling to update the slots in the file
				updatingJson(bm2, file, dateformat, relativePath);
				// break;

			}

			// }
			// System.out.println(arrObj+" JSon Object1");

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void readingJson() {
	}

	public void creatingJson(BookingModel bm, File file, String dateformat, String relativePath) throws IOException {
		if (file.createNewFile()) {
			System.out.println(relativePath + " File Created in Project source directory");
		}
		JSONObject obj = new JSONObject();
		JSONArray MeetingRoom = new JSONArray();
		JSONObject RoomId = new JSONObject();
		JSONArray RoomArray = new JSONArray();
		JSONObject roomObj = new JSONObject();

		for (String time : bm.getTime()) {
			roomObj.put("slot" + time, bm.getUserId());
		}
		// RoomArray.add(roomObj);
		RoomId.put("Room" + bm.getRoomId(), roomObj);
		// RoomId.put("Room"+bm.getRoomId(),roomObj);
		MeetingRoom.add(RoomId);
		obj.put("MeetingRoom", MeetingRoom);

		// writing to file
		FileWriter fw = new FileWriter(jsonFilePath + "" + dateformat);
		fw.write(obj.toJSONString());
		fw.close();
		System.out.println("Successfully Copied JSON Object to File...");
		System.out.println("\nJSON Object: " + obj);

	}

	//canclelling Room solts
	@RequestMapping(value="/ConferenceRoom", method=RequestMethod.DELETE)
	public JSONObject cancelBooking(@RequestBody BookingModel bm)
	{
		JSONObject jobj = new JSONObject();
		System.out.println("111C");
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		Date date = bm.getDate();
		String dateformat = "Date" + dateFormat.format(date) + ".json";
		String relativePath = jsonFilePath + "" + dateformat;
		// File
		File file = new File(jsonFilePath + "" + dateformat);
		File dir = new File(jsonFilePath + "");
		System.out.println(jsonFilePath + "" + dateformat + " is file?" + file.isFile());
		if (file.isFile()) // File doesnt Exist Flow
		{
			System.out.println("112C");
			if(rescheduleFlag)
			{
				return cancelJson(bm, file, dateformat, relativePath,true);
			}
			
			
			return cancelJson(bm, file, dateformat, relativePath,false);
			
		} 
		else{
			
			System.out.println("Invalid Request");
			jobj.put("status", "invalidRequest no booking exists on that day");
			return jobj;
		}
		
		
	}

	public JSONObject cancelJson(BookingModel bm, File file, String dateformat, String relativePath,Boolean rescheduling){
		JSONObject jobj = new JSONObject();
		ArrayList<String> jsuccess = new ArrayList<String>();
		ArrayList<String> jfailed = new ArrayList<String>();
		JSONParser parser = new JSONParser();
		/* Get the file content into the JSONObject */
		Object obj;
		try {
			obj = parser.parse(new FileReader(jsonFilePath + "" + dateformat));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray arrObj = (JSONArray) jsonObject.get("MeetingRoom");
			String RoomName;
			Boolean roomFlag = false;
			Boolean slotFlag =false;
			int index = -1;
			// JSONObject jsonObject1 = (JSONObject)
			// jsonObject.get("MeetingRoom");

			//This Loop is to get the Roomid
			for (int i = 0; i < arrObj.size(); i++) {
				JSONObject object = (JSONObject) arrObj.get(i);
				Set<String> keys = object.keySet();
				Iterator<String> it = keys.iterator();
				do {
					RoomName = it.next().toString();
					System.out.println(RoomName);
					roomFlag = RoomName.equals("Room" + bm.getRoomId());
					System.out.println(roomFlag);
				} while (it.hasNext());
				if (roomFlag) {
					index = i;
					break;
				}
				

			}

			if (roomFlag) {
				JSONObject object = (JSONObject) arrObj.get(index);
				
				object = (JSONObject) object.get("Room" + bm.getRoomId());

				for (String time : bm.getTime()) {
					if (object.get("slot" + time)!= null && object.get("slot"+time).equals(bm.getUserId())) {
						//object.put("slot" + time, bm.getUserId());
						jsuccess.add(time);
						object.put("slot" + time, null);
						System.out.println("Slot"+ time+" has be cancled ");
						
						//jobj.put(time+"", "Slot"+ time+" has be cancled ");
						
					}else
					{	jfailed.add(time);
						//jobj.put(time+"", "Slot"+ time+" cannot be cancled ");
						System.out.println("Slot"+ time+" cannot be cancled ");
					}
					
					//System.out.println("slot" + time + ":" + object.get("slot" + time));
				}
				
				
				
				
				//Use this to return json object
	//flow added for Rescheduling insital all the slots in time array will be cancled
				if(rescheduling)
				{
					System.out.println("Rescheduling flow");
					if (object.get("slot" + bm.getRescheduling())== null )
					{
						object.put("slot"+bm.getRescheduling(),bm.getUserId());
						jsuccess.add(bm.getRescheduling());
						
					}
					else
						jfailed.add(bm.getRescheduling());
				}
				
				jobj.put("success", jsuccess);
				jobj.put("failed", jfailed);
				
	
				
				
				//Writing the Updated part in json
				System.out.println(jsonObject);
				FileWriter fw = new FileWriter(jsonFilePath + "" + dateformat);
				fw.write(jsonObject.toJSONString());
				fw.close();
				System.out.println("Successfully Copied JSON Object to File...");
				System.out.println("\nJSON Object: " + jsonObject);
				// break;
			}
			else{
				System.out.println("Room Doesnt Exist so cannot be updated--Return invalid request");
			}
	
		}catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jobj;
	}

	//Reshecduling Room Slots
	@RequestMapping(value = "/ConferenceRoom", method = RequestMethod.PUT)
	public JSONObject reschedulingBooking(@RequestBody BookingModel bm)
	{
		
		JSONObject jobj = new JSONObject();
		//checking whether rescheduling slot is not Null
		if(bm.getRescheduling()== null)
		{	
			
			jobj.put("Status", "invalid Request for rescheduling");
			return jobj;
		}
		
		rescheduleFlag=true;
		
		jobj=cancelBooking(bm);
		
		rescheduleFlag=false;
		
		
		
		
		
		System.out.println("111R");
		
	return jobj;	
		
	}


}
