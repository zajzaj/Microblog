package com.example.httptest;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class ServerClass extends HttpServlet{
     
    private static final long serialVersionUID = 1L;

  
    public ServerClass(){
  
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
       // doPost(req,resp);
       
    }
  
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
    	System.out.println("Jump!");
        if(req == null){
            return;
        }
        
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
  
        
        PrintWriter out = resp.getWriter();
        
        String chose=req.getParameter("CHOSE");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //根据chose判断要执行的内容

        String ret="-2";
        if (chose==null)
        	return;
        
        if(chose.equals("1"))
        {//登录
        	String name = req.getParameter("USERNAME");
            String password = req.getParameter("PASSWORD");

        	   try {
        		   //把chose设为其第二位的值
                   ret = checkSubmit(name,password,"1");
                   System.out.println("chose"+chose);
                   
               } catch (Exception e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               }
        }
        else
        	if (chose.equals("2"))
        	{//注册
            	String name = req.getParameter("USERNAME");
                String password = req.getParameter("PASSWORD");

            	   try {
            		   //把chose设为其第二位的值
                       ret = checkSubmit(name,password,"2");
                       System.out.println("chose"+chose);
                       
                   } catch (Exception e) {
                       // TODO Auto-generated catch block
                       e.printStackTrace();
                   }
        	}
        else if (chose.equals("3"))
        {
        	//发表新微博
        	String name = req.getParameter("USERNAME");
        	String comment = req.getParameter("COMMENT");
        	
        	try
        	{
        		ret = publishNewMicroblog(name,comment,chose);
        		System.out.println("chose" + chose);
        	}
        	catch (Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        else if (chose.equals("4")){
        	//获取首页
        	String name = req.getParameter("USERNAME");
        	try
        	{
        		ret = findHomepageMicroblog(name);
        		System.out.println("chose" + chose);
        	}
        	catch (Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        else if (chose.equals("5"))
        {//评论微博
        	String username = req.getParameter("USERNAME");
        	String tweet_id = req.getParameter("TWEET_ID");
        	String comment = req.getParameter("COMMENT");
        	try
        	{
        		
        		ret = comment(username,tweet_id,comment);
         	}
        	catch (Exception e)
        	{
        		e.printStackTrace();
        	}
        	
        }
        else if (chose.equals("6"))
        {
        	//获取PersonCenter数据
        	String username = req.getParameter("USERNAME");
        	try
        	{
        		ret = getPersonalCenter(username);
        	}
        	catch (Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        else if (chose.equals("7")){
        	String username = req.getParameter("USERNAME");
        	String password = req.getParameter("PASSWORD");
        	String newPassword = req.getParameter("NEWPASSWORD");
        	try{
        		ret = modifyPwd(username,password,newPassword);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        else if (chose.equals("8")){
        	String username = req.getParameter("USERNAME");
        	String tweet_id = req.getParameter("TWEET_ID");
        	String comment = req.getParameter("COMMENT");
        	try{
        		ret = modifyMicroblog(username,tweet_id,comment);
        	}catch (Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        else if (chose.equals("9")){
        	String tweet_id = req.getParameter("TWEET_ID");
        	try{
        		ret = showComments(tweet_id);
        	}catch (Exception e){
        		e.printStackTrace();
        	}
        }
        else if (chose.equals("10")){
        	String username = req.getParameter("USERNAME");
        	String headpic = req.getParameter("HEADPIC");
        	try{
        		ret = uploadHead(username,headpic);
        	}catch (Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        
        System.out.println("4ret="+ret);
        out.print(ret);
        out.flush();
        out.close();
        System.out.println("5ret="+ret);
    }
  
    /**
     * 判断登录名和密码
     * @param name
     * @param code
     * @return
     * @throws Exception 
     */
    private String checkSubmit(String name, String code,String chose) throws Exception{
        String ret = "-1";        
        User user=new User(name,code);
        ret=new UserDAOProxy().findLogin(user,chose);
        return ret;
    }
    
    private String publishNewMicroblog(String name,String comment,String chose) throws Exception{
    	String ret = "-1";
    	Microblog mb = new Microblog(name,comment);
    	ret = new UserDAOProxy().publishNewMicroblog(mb,chose);
    	return ret;
    }
    
   private String findHomepageMicroblog(String name) throws Exception
   {
	   String ret = "-1";
	   ret = new UserDAOProxy().findHomepageMicroblog(name);
	   return ret;
   }
   
   private String comment(String username,String tweet_id,String comment_content) throws Exception
   {
	   String ret = "-1";
	   return ret = new UserDAOProxy().comment(username,tweet_id,comment_content);
	   
   }
   
   private String getPersonalCenter(String username) throws Exception
   {
	   String ret = "-1";
	   return ret = new UserDAOProxy().getPersonalCenter(username);
   }

   private String modifyPwd(String username,String password,String newPassword) throws Exception
   {
	   String ret = "-1";
	   return ret = new UserDAOProxy().modifyPwd(username,password,newPassword);
   }
   
   private String modifyMicroblog(String username,String tweet_id,String comment_content) throws Exception
   {
	   String ret = "-1";
	   return ret = new UserDAOProxy().modifyMicroblog(username,tweet_id,comment_content);
	   
   }
   
   private String showComments(String tweet_id) throws Exception{
	   int id = Integer.parseInt(tweet_id);
	   String ret = "-1";
	   return  ret = new UserDAOProxy().showComments(id);
   }
   private String uploadHead(String userName,String headPic) throws Exception{
	   String ret = "-1";
	   return  ret = new UserDAOProxy().uploadHead(userName,headPic);
   }
}