package com.mphasis.meetingRoom.model;

import java.util.Arrays;

public class RoomObject {
	private String userId;
	private String[] time;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String[] getTime() {
		return time;
	}
	public void setTime(String[] time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "RoomObject [userId=" + userId + ", time=" + Arrays.toString(time) + "]";
	}
	
	
}
