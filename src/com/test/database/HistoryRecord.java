package com.test.database;

public class HistoryRecord {
	private String type;
	private String time;
	
	public HistoryRecord(String type, String time) {
		this.type = type;
		this.time = time;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
