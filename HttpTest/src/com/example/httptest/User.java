package com.example.httptest;
 
public class User {
	
    private String username;  
    private String password;  
    private int follow, fans;
    
    public User(){  
          this.setUsername("");
          this.password="";
          setFollow(0);
          setFans(0);
    }
    
    public User(String username,String password)
    {
    	this.setUsername(username);
    	this.password = password;   
    	setFollow(0);
    	setFans(0);
    }

    public void setPassword(String password){  
        this.password = password;  
    }  
    public String getPassword(){  
        return password;  
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getFollow() {
		return follow;
	}

	public void setFollow(int follow) {
		this.follow = follow;
	}

	public int getFans() {
		return fans;
	}

	public void setFans(int fans) {
		this.fans = fans;
	}

}