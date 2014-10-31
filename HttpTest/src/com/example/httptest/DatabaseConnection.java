package com.example.httptest;
 
import java.sql.*;
 
public class DatabaseConnection {
    private Connection con;  
    public DatabaseConnection(){  
        try{  
        	System.out.println("TestDB!");
        	Class.forName("com.mysql.jdbc.Driver");// 加载Oracle驱动程序
	        String url="jdbc:mysql://127.0.0.1:3307/Microblog"; //orcl为数据库的SID
	        
	        String user = "root";// 用户名,系统默认的账户名
	        String password = "System123";// 你安装时选设置的密码
	        System.out.println("开始尝试连接数据库！");
	        con = DriverManager.getConnection(url, user, password);// 获取连接
	        System.out.println("连接成功！");

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