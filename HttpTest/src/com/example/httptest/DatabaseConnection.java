package com.example.httptest;
 
import java.sql.*;
 
public class DatabaseConnection {
    private Connection con;  
    public DatabaseConnection(){  
        try{  
        	System.out.println("TestDB!");
        	Class.forName("com.mysql.jdbc.Driver");// ����Oracle��������
	        String url="jdbc:mysql://127.0.0.1:3307/Microblog"; //orclΪ���ݿ��SID
	        
	        String user = "root";// �û���,ϵͳĬ�ϵ��˻���
	        String password = "System123";// �㰲װʱѡ���õ�����
	        System.out.println("��ʼ�����������ݿ⣡");
	        con = DriverManager.getConnection(url, user, password);// ��ȡ����
	        System.out.println("���ӳɹ���");

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