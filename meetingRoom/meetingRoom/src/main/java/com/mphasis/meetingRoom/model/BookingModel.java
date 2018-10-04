package com.mphasis.meetingRoom.model;

import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Component;


public class BookingModel {
	
	private String roomId;
	private String userId;
	private Date date;
	private String[] time;
	private String Rescheduling;
	
	
	public String getRescheduling(){
		return Rescheduling;
	} 
	
	public void setRescheduling(String Rescheduling){
		this.Rescheduling=Rescheduling;
	} 
	
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String[] getTime() {
		return time;
	}
	public void setTime(String[] time) {
		this.time = time;
	}
	
	
	@Override
	public String toString() {
		return "BookingModel [roomId=" + roomId + ", userId=" + userId + ", date=" + date + ", time="
				+ Arrays.toString(time) + ", Rescheduling=" + Rescheduling + "]";
	}

	public BookingModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BookingModel(String roomId, String userId, Date date, String[] time, String rescheduling) {
		super();
		this.roomId = roomId;
		this.userId = userId;
		this.date = date;
		this.time = time;
		Rescheduling = rescheduling;
	}
	

	
	
	
	

}
