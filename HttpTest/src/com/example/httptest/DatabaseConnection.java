package com.example.httptest;
 
import java.sql.*;
 
public class DatabaseConnection {
    private Connection con;  
    public DatabaseConnection(){  
        try{  
        	System.out.println("TestDB!");
        	Class.forName("com.mysql.jdbc.Driver");//Mysql
	        String url="jdbc:mysql://127.0.0.1:3307/Microblog"; 
	        
	        String user = "root";//Username for Mysql
	        String password = "System123";//password£¡
	        System.out.println("Connecting to Mysql");
	        con = DriverManager.getConnection(url, user, password);
	        System.out.println("Connecting Success");

        }  
        catch(Exception e){
        	e.printStackTrace();
        }  
    }  
    

    
    public Connection getConnection(){  
        return con;  
    }
    
    
    public void close(){  
        try{  
            if(con!=null){  
                con.close();  
            }  
        }  
        catch(Exception e){}  
    }  
    
}