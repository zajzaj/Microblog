package com.example.httptest;
 
public class UserDAOProxy {
    private DatabaseConnection dbc;  
    private UserDAOImpl idao;  
    public UserDAOProxy(){
        dbc = new DatabaseConnection();  
        idao = new UserDAOImpl(dbc.getConnection());  
    }  
    public String findLogin(User user,String chose) throws Exception{  
        String flag = idao.findLogin(user,chose);  
        dbc.close();  
        System.out.println("2ret="+flag);
        return flag;  
    }
    public String publishNewMicroblog(Microblog microblog,String chose) throws Exception{
    	String flag = idao.publishNewMicroblog(microblog,chose);
    	dbc.close();
    	System.out.println("2ret="+flag);
    	return flag;
    }
    
    public String findHomepageMicroblog(String username) throws Exception{
    	String flag = idao.findHomepageMicroblog(username);
    	dbc.close();
    	System.out.println("2ret="+flag);
    	return flag;
    }
    public String comment(String username,String tweet_id,String comment_content) throws Exception{
    	String flag = idao.comment(username,tweet_id,comment_content);
    	dbc.close();
    	System.out.println("2ret"+flag);
    	return flag;
    }
    public String getPersonalCenter(String username) throws Exception{
    	String flag = idao.getPersonalCenter(username);
    	dbc.close();
    	System.out.println("2ret"+flag);
    	return flag;
    }
    public String modifyPwd(String username,String password,String newPassword) throws Exception{
    	String flag = idao.modifyPwd(username,password,newPassword);
    	dbc.close();
    	System.out.println("2ret"+flag);
    	return flag;
    }
    public String modifyMicroblog(String username,String tweet_id,String comment_content) throws Exception{
    	String flag = idao.modifyMicroblog(username,tweet_id,comment_content);
    	dbc.close();
    	System.out.println("2ret"+flag);
    	return flag;
    }
}