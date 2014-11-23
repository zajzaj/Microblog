package com.example.httptest;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl {
	private Connection con;
	private Statement stat;

	public UserDAOImpl(Connection con) {
		this.con = con;
		
	}


	public String findLogin(User user, String chose) throws Exception {

		try {
			this.stat = con.createStatement();
			System.out.println("stat ������");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// �����
		JSONObject obj = new JSONObject();
		System.out.println("stat �����ɹ���");
		if (chose.equals("1")) {
			String sql = "SELECT * FROM userinfo WHERE username=" + "'"
					+ user.getUsername() + "'" + " and password=" + "'"
					+ user.getPassword() + "'";
			System.out.println(sql);
			System.out.println("��ʼִ�У�");
			ResultSet rs = stat.executeQuery(sql);
			System.out.println("ִ�гɹ���");

			if (rs.next()) {
				System.out.println("����next��");
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				obj.put("Response", "1");
				obj.put("USERNAME", user.getUsername());
				obj.put("FOLLOW", rs.getInt("follow"));
				obj.put("FANS", rs.getInt("fans"));
				System.out.println("��½�ɹ�");
			} else {
				obj.put("Response", "-1");
				System.out.println("��½ʧ��");
			}

		}

		if (chose.equals("2")) {
			try {
				String sql = "insert into userinfo values(" + "'"
						+ user.getUsername() + "'" + "," + "'"
						+ user.getPassword() + "'" + "," + "0" + "," + "0"
						+ ")";
				System.out.println(sql);
				System.out.println("��ʼע��");
				stat.execute(sql);
				System.out.println("ע��ɹ�");
				obj.put("Response", "1");
			} catch (Exception e) {
				e.printStackTrace();
				obj.put("Response", "-1");
			}
		}

		if (stat != null) {
			stat.close();
			stat = null;
		}
		return obj.toString();
	}

	public String publishNewMicroblog(Microblog microblog, String chose)
			throws Exception

	{
		try {
			this.stat = con.createStatement();
			System.out.println("stat ������");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// �����
		JSONObject obj = new JSONObject();
		System.out.println("stat �����ɹ���");

		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
			String currentTime = df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
			String sql = "INSERT INTO Microblog values(" + "null" + "," + "'"
					+ microblog.getUsername() + "'" + "," + "'"
					+ microblog.getMicroblog() + "','" + currentTime + "'"
					+ ")";
			System.out.println(sql);
			System.out.println("��ʼִ�У�");
			stat.execute(sql);
			System.out.println("ִ�гɹ���");

			obj.put("Response", "1");
			System.out.println("����ɹ�");

			/*
			 * { obj.put("Response", "-1"); System.out.println("����ʧ��"); }
			 */

		}
		if (stat != null) {
			stat.close();
			stat = null;
		}
		return obj.toString();
	}

	public String findHomepageMicroblog(String username) throws Exception {

		JSONArray array = new JSONArray();
		try {
			this.stat = con.createStatement();
			System.out.println("stat ������");

			String sql = "SELECT * FROM friend_relationship,Microblog WHERE friend_relationship.username1='"
					+ username
					+ "'"
					+ "and friend_relationship.username2 = Microblog.username";
			System.out.println(sql);
			System.out.println("��ʼִ�У�");
			ResultSet rs = stat.executeQuery(sql);
			System.out.println("ִ�гɹ���");

			while (rs.next()) {
				System.out.println("����next��");
				JSONObject obj = new JSONObject();
				obj.put("Response", "1");
				obj.put("USERNAME", rs.getString("Microblog.username"));
				obj.put("TWEET_ID", rs.getInt("Microblog.tweet_id"));
				obj.put("TWEET", rs.getString("Microblog.tweet"));
				obj.put("TWEET_TIME", rs.getString("Microblog.tweet_time"));
				sql = "SELECT COUNT(*) as comments FROM comments WHERE tweet_id = " + rs.getInt("Microblog.tweet_id");
				Statement newStat = con.createStatement();
				ResultSet result = newStat.executeQuery(sql);
				System.out.println(sql);
				if (result.next())
				{
					obj.put("COMMENTS",result.getInt("comments"));
				}
				array.add(obj);
				if (newStat != null)
				{
					newStat.close();
					newStat = null;
				}
			} 
			if (stat != null) {
				stat.close();
				stat = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array.toString();
	}
	
	public String comment(String username,String tweet_id,String comment_content) throws Exception
	{
		try {
			this.stat = con.createStatement();
			System.out.println("stat ������");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// �����
		JSONObject obj = new JSONObject();
		System.out.println("stat �����ɹ���");

		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
			String currentTime = df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
			String sql = "INSERT INTO comments values(" + "null" + "," + "'"
					+ Integer.valueOf(tweet_id) + "'" + "," + "'"
					+ username + "','" + comment_content + "','" + currentTime + "'"
					+ ")";
			System.out.println(sql);
			System.out.println("��ʼִ�У�");
			stat.execute(sql);
			System.out.println("ִ�гɹ���");

			obj.put("Response", "1");
			System.out.println("����ɹ�");

			/*
			 * { obj.put("Response", "-1"); System.out.println("����ʧ��"); }
			 */

		}
		if (stat != null) {
			stat.close();
			stat = null;
		}
		return obj.toString();
	}
	
	public String getPersonalCenter(String username) throws Exception{
		try {
			this.stat = con.createStatement();
			System.out.println("stat ������");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray array = new JSONArray();
		String sql = "SELECT * FROM userinfo WHERE username=" + "'"
				+ username + "'" ;
		System.out.println(sql);
		System.out.println("��ʼִ�У�");
		ResultSet rs = stat.executeQuery(sql);
		System.out.println("ִ�гɹ���");
		if (rs.next()) {
			System.out.println("����next��");
			JSONObject obj = new JSONObject();
			obj.put("FOLLOW", rs.getInt("follow"));
			obj.put("FANS", rs.getInt("fans"));
			array.add(obj);
			System.out.println("��½�ɹ�");
		} 
	    sql = "SELECT * FROM Microblog WHERE username='"+username+"'";
		System.out.println(sql);
		System.out.println("��ʼִ�У�");
		rs = stat.executeQuery(sql);
		System.out.println("ִ�гɹ���");

		while (rs.next()) {
			System.out.println("����next��");
			JSONObject obj = new JSONObject();
			obj.put("Response", "1");
			obj.put("USERNAME", rs.getString("Microblog.username"));
			obj.put("TWEET_ID", rs.getInt("Microblog.tweet_id"));
			obj.put("TWEET", rs.getString("Microblog.tweet"));
			obj.put("TWEET_TIME", rs.getString("Microblog.tweet_time"));
			sql = "SELECT COUNT(*) as comments FROM comments WHERE tweet_id = " + rs.getInt("Microblog.tweet_id");
			Statement newStat = con.createStatement();
			ResultSet result = newStat.executeQuery(sql);
			System.out.println(sql);
			if (result.next())
			{
				obj.put("COMMENTS",result.getInt("comments"));
			}
			array.add(obj);
			if (newStat != null)
			{
				newStat.close();
				newStat = null;
			}
		} 
		
		
		if (stat != null) {
			stat.close();
			stat = null;
		}
		
		return array.toString();

	}
	public String modifyPwd(String username,String password,String newPassword) throws Exception{
		try {
			this.stat = con.createStatement();
			System.out.println("stat ������");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray array = new JSONArray();
		String sql1 = "SELECT * FROM userinfo WHERE username=" + "'"
				+ username + "'"+"and password="+"'"+password+"'" ;
		System.out.println(sql1);
		System.out.println("��ʼִ�У�");
		ResultSet rs = stat.executeQuery(sql1);
		System.out.println("ִ�гɹ���");
		
		if (rs.next()) {
			System.out.println("����next��");
			JSONObject obj = new JSONObject();
			String sql2 = "update userinfo set password = "+"'"+newPassword+"'"+" WHERE username=" + "'"+ username + "'";
			System.out.println(sql2);
			System.out.println("��ʼִ�У�");
			stat.execute(sql2);
			System.out.println("ִ�гɹ���");
			obj.put("Response","1");
			//array.add(obj);

			System.out.println("�޸�����ɹ�");
			return obj.toString();
		} 
		else
		{
			JSONObject obj = new JSONObject();
			obj.put("Response","-1");
			return obj.toString();
		}
	}
	public String modifyMicroblog(String username,String tweet_id,String comment_content) throws Exception
	{
		try {
			this.stat = con.createStatement();
			System.out.println("stat ������");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// �����
		JSONObject obj = new JSONObject();
		System.out.println("stat �����ɹ���");

		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
			String currentTime = df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
			String sql = "update Microblog set tweet = '" + comment_content + "'" + ",tweet_time = '" + currentTime + "' WHERE tweet_id = '" + tweet_id + "'";
			System.out.println(sql);
			System.out.println("��ʼִ�У�");
			stat.execute(sql);
			System.out.println("ִ�гɹ���");

			obj.put("Response", "1");
			System.out.println("����ɹ�");
		}
		if (stat != null) {
			stat.close();
			stat = null;
		}
		return obj.toString();
	}


	public String showComments(int tweet_id) throws Exception {
		try {
			this.stat = con.createStatement();
			System.out.println("stat ������");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// �����
		System.out.println("stat �����ɹ���");

		JSONArray array = new JSONArray();
		String sql = "SELECT * FROM comments WHERE tweet_id=" + String.valueOf(tweet_id);
		System.out.println(sql);
		System.out.println("��ʼִ�У�");
		ResultSet rs = stat.executeQuery(sql);
		System.out.println("ִ�гɹ���");
		if (rs.next()) {
			System.out.println("����next��");
			JSONObject obj = new JSONObject();
			obj.put("name", rs.getString("USERNAME"));
			obj.put("time", rs.getString("COMMENT_TIME"));
			obj.put("text", rs.getString("COMMENT_CONTENT"));
			array.add(obj);
			System.out.println("��½�ɹ�");
		} 
		if (stat != null) {
			stat.close();
			stat = null;
		}
		return array.toString();
	}
}