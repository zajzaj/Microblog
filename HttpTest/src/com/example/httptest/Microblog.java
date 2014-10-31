package com.example.httptest;

public class Microblog {
	private String username;
	private String microblog;
	
	public Microblog(String username,String microblog)
	{
		this.username = username;
		this.microblog = microblog;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMicroblog() {
		return microblog;
	}
	public void setMicroblog(String microblog) {
		this.microblog = microblog;
	}
	
	

}
