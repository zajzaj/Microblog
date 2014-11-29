package com.example.microblog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.util.HttpUtil;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalCenterActivity extends ListActivity {
	/* head */
	private TextView title;
	private Button backBtn;
	private Button funBtn;
    private String back_username;
    private String front_username;

	private ListView listView;

	ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
	
	public void getData()
	{
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
					paramsList.add(new BasicNameValuePair("CHOSE", "6"));
					paramsList.add(new BasicNameValuePair("USERNAME", front_username));
					HttpUtil hu = new HttpUtil();
					String res = hu.doPost(LoginActivity.URL, "6", paramsList);
					Log.i("ressss", res);

					try {
						JSONArray array = new JSONArray(res);
						TextView follows = (TextView) findViewById(R.id.personal_focus_count);
						TextView fans = (TextView) findViewById(R.id.personal_fans_count);
						follows.setText(String.valueOf((array.getJSONObject(0)).getInt("FOLLOW")));
						fans = (TextView) findViewById(R.id.personal_fans_count);
						fans.setText(String.valueOf((array.getJSONObject(0)).getInt("FANS")));
						for (int i = array.length() - 1; i >= 1; i--) {
							JSONObject obj = array.getJSONObject(i);
							Map<String, Object> item = new HashMap<String, Object>();
							item.put("item_img",
									R.drawable.headpic_defalut_image);
							item.put("name_text", obj.getString("USERNAME"));
							item.put("message_time",obj.getString("TWEET_TIME"));
							item.put("tweet_message", obj.getString("TWEET"));
							item.put("tweet_id",obj.getInt("TWEET_ID"));
							item.put("comment_count",obj.getInt("COMMENTS"));
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
		setContentView(R.layout.personal_center);

		title = (TextView) findViewById(R.id.title);
		backBtn = (Button) findViewById(R.id.back_btn);
		funBtn = (Button) findViewById(R.id.fun_btn);

		Intent intent = this.getIntent();
		Bundle bl = intent.getExtras();
		back_username = bl.getString("BACK_USERNAME");
		front_username = bl.getString("FRONT_USERNAME");
		/* 修改标题 */
		title.setText(front_username);

		/* 返回键，关闭当前界面 */
		backBtn = (Button) findViewById(R.id.back_btn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});

		/*
		 * 不需要时，隐藏返回键 backBtn.setVisibility(View.INVISIBLE);
		 */

		
		funBtn.setVisibility(View.INVISIBLE);
		
		funBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(PersonalCenterActivity.this,
						NewMicroblogActicity.class);
				startActivity(intent);
				// finish();
			}
		});
        getData();
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listView = (ListView) findViewById(android.R.id.list);
		MyAdapter myAdapter = new MyAdapter(this);
		listView.setAdapter(myAdapter);
	}
	 private final class PersonalViewHolder{
	      ImageView item_img;
	      TextView name_text;
	      TextView message_time;
	      TextView tweet_message;
	      Button tweet_comment;
	      TextView tweet_comment_count;
	      Button tweet_repost;
	      TextView tweet_repost_count;
	    }
		private class MyAdapter extends BaseAdapter{

			private LayoutInflater m_Inflater;		
			public MyAdapter(Context context){
				this.m_Inflater = LayoutInflater.from(context);
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mData.size();
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				PersonalViewHolder holder = null;
				if (convertView == null) {				
					holder=new PersonalViewHolder();  				
					convertView = m_Inflater.inflate(R.layout.personal_center_list_item, null);	  //根据布局产生一个view
					holder.item_img = (ImageView)convertView.findViewById(R.id.personal_item_img); 
					holder.name_text = (TextView)convertView.findViewById(R.id.personal_name_text);
					holder.message_time = (TextView)convertView.findViewById(R.id.personal_message_time);
					holder.tweet_message = (TextView)convertView.findViewById(R.id.personal_tweet_message);
					holder.tweet_comment = (Button)convertView.findViewById(R.id.personal_tweet_comment);
					holder.tweet_comment_count=(TextView)convertView.findViewById(R.id.personal_tweet_comment_count);
					holder.tweet_repost = (Button)convertView.findViewById(R.id.personal_tweet_repost);
					convertView.setTag(holder);					
				} else {				
					holder = (PersonalViewHolder)convertView.getTag();
				}		
				
				holder.item_img.setBackgroundResource(R.drawable.headpic_defalut_image);			
				holder.name_text.setText((String)mData.get(position).get("name_text"));
				holder.message_time.setText((String)mData.get(position).get("message_time"));
				holder.tweet_message.setText((String)mData.get(position).get("tweet_message"));
                holder.tweet_comment_count.setText(String.valueOf(mData.get(position).get("comment_count")));
                final int tweet_id = (Integer) mData.get(position).get("tweet_id");
                
                holder.tweet_message.setOnLongClickListener(new OnLongClickListener(){

					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						if (!back_username.equals(front_username))
						return false;
						else
						{
							Intent intent = new Intent(PersonalCenterActivity.this,ModifyMicroblog.class);
							Bundle bl = new Bundle();
							bl.putString("USERNAME",back_username);
							bl.putInt("TWEET_ID",tweet_id);
			                intent.putExtras(bl);
							startActivity(intent);	
							return true;
						}
					}
                	
                });
				holder.tweet_comment.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v){
						Intent intent = new Intent(PersonalCenterActivity.this,CommentActivity.class);
						Bundle bl = new Bundle();
						bl.putString("USERNAME",back_username);
						bl.putInt("TWEET_ID",tweet_id);
		                intent.putExtras(bl);
						startActivity(intent);					
						//finish();
					}
				});			
				holder.tweet_comment.setOnLongClickListener(new OnLongClickListener(){

					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(PersonalCenterActivity.this,ShowCommentActivity.class);
						Bundle bl = new Bundle();
						bl.putString("USERNAME",back_username);
	                    bl.putInt("TWEET_ID",tweet_id);
						intent.putExtras(bl);
						startActivity(intent);
						return true;
					}
					
					
				});
				final String tweet = mData.get(position).get("tweet_message").toString();
				holder.tweet_repost.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v){
						Intent intent = new Intent(PersonalCenterActivity.this,
								RepostActivity.class);
						Bundle bl = new Bundle();
						bl.putString("USERNAME", back_username);
						String newTweet = "Repost from " + front_username + ":" + tweet;
						bl.putString("TWEET",newTweet);
						intent.putExtras(bl);
						startActivity(intent);
						//finish();
					}
				});			
				return convertView;
			}
			

		}
}
