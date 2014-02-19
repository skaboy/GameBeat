package com.BeatGame.Database;

public class User {
	
	private String username;
	private long score;
	private int id;
	private String uuid;

	public String getName(){
		return username;
	}
	
	public long getScore(){
		return score;
	}
	
	public void setName(String name){
		this.username = name;
	}
	
	public void setScore(long score){
		this.score = score;
	}
	
	public int getID(){
		return id;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public String getUUID(){
		return uuid;
	}
	
	public void setUUID(String uuid){
		this.uuid = uuid;
	}
}
