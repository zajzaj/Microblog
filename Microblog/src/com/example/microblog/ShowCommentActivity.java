package com.example.microblog;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.os.Build;

public class ShowCommentActivity extends ListActivity {
	
	private TextView title;
	private Button backBtn;
	private Button funBtn;
	private ListView listView;
	
	 private String[] mListTitle = { "祖米诺", "延毕毕","祖米诺", "延毕毕"};  
	 private String[] mListStr = { "2014/11/19 17:17", "2014/11/19 20:00", "2014/11/19 17:17", "2014/11/19 20:00" }; 
	 private String[] mListcom={"我在和室友撕逼","我太不靠谱了","我在和室友撕逼","我太不靠谱了"};
	    ListView mListView = null;  
	    ArrayList<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();;  
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_comment);
		
		title = (TextView) findViewById(R.id.title);
		backBtn = (Button) findViewById(R.id.back_btn);
		funBtn = (Button) findViewById(R.id.fun_btn);

		/* 修改标题 */
		title.setText("评论");
		
		mListView = getListView();  
        
	    int lengh = mListTitle.length;  
	    for(int i =0; i < lengh; i++) {  
	        Map<String,Object> item = new HashMap<String,Object>();  
	        item.put("image", R.drawable.headpic_defalut_image);  
	        item.put("title", mListTitle[i]);  
	        item.put("text", mListStr[i]); 
	        item.put("comment", mListcom[i]);
	        mData.add(item);   
	    }  
	    SimpleAdapter adapter = new SimpleAdapter(this,mData,R.layout.show_comment_list_item,  
	        new String[]{"image","title","text","comment"},new int[]{R.id.sc_item_img,R.id.sc_name_text,R.id.sc_message_time,R.id.sc_comment});  
	        setListAdapter(adapter); 
		
	}

}
