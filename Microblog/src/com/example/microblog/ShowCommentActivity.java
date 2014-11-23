package com.example.microblog;
//

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.util.HttpUtil;

import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
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
	
	 private String[] mListTitle = { "×æÃ×Åµ", "ÑÓ±Ï±Ï","×æÃ×Åµ", "ÑÓ±Ï±Ï"};  
	 private String[] mListStr = { "2014/11/19 17:17", "2014/11/19 20:00", "2014/11/19 17:17", "2014/11/19 20:00" }; 
	 private String[] mListcom={"ÎÒÔÚºÍÊÒÓÑËº±Æ","ÎÒÌ«²»¿¿Æ×ÁË","ÎÒÔÚºÍÊÒÓÑËº±Æ","ÎÒÌ«²»¿¿Æ×ÁË"};
	    ListView mListView = null;  
	    ArrayList<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();;  
	    
	  private void getData(int tweet_id) {

			final String id = String.valueOf(tweet_id);
			try {
				new Thread(new Runnable() {
					@Override
			
					public synchronized void run() {
						List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
						paramsList.add(new BasicNameValuePair("CHOSE", "9"));
						paramsList.add(new BasicNameValuePair("TWEET_ID", id));
						HttpUtil hu = new HttpUtil();
						String res = hu.doPost(LoginActivity.URL, "9", paramsList);
						Log.i("ressss", res);

						try {
							JSONArray array = new JSONArray(res);
							for (int i = array.length() - 1; i >=0; i--) {
								JSONObject obj = array.getJSONObject(i);
								Map<String, Object> item = new HashMap<String, Object>();
								item.put("image",
										R.drawable.headpic_defalut_image);
								item.put("name", obj.getString("name"));
								item.put("time",obj.getString("time"));
								item.put("message", obj.getString("text"));
								mData.add(item);
							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}).start();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_comment);
		
		title = (TextView) findViewById(R.id.title);
		backBtn = (Button) findViewById(R.id.back_btn);
		funBtn = (Button) findViewById(R.id.fun_btn);

		backBtn = (Button) findViewById(R.id.back_btn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});
		funBtn.setVisibility(View.INVISIBLE);
		
		mListView = getListView();  
        
		Intent intent = this.getIntent();
		Bundle bl;
		bl = intent.getExtras();
		int tweet_id = bl.getInt("TWEET_ID");
        String username = bl.getString("USERNAME");
        title.setText(username);
		getData(tweet_id);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    SimpleAdapter adapter = new SimpleAdapter(this,mData,R.layout.show_comment_list_item,  
	        new String[]{"image","name","time","message"},new int[]{R.id.sc_item_img,R.id.sc_name_text,R.id.sc_message_time,R.id.sc_comment});  
	        setListAdapter(adapter); 
		
	}

}
