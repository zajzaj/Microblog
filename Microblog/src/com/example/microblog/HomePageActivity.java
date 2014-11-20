package com.example.microblog;

import java.util.ArrayList;
import android.widget.AdapterView.OnItemClickListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import com.example.util.HttpUtil;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.os.Build;

public class HomePageActivity extends ListActivity {
	/* head */
	private TextView title;
	private Button backBtn;
	private Button funBtn;
	private ListView listView;

	ArrayList<Map<String, Object>> mData1 = new ArrayList<Map<String, Object>>();
	ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
	String username;

	private void getData() {

		final String newUsername = username;
		try {
			new Thread(new Runnable() {
				@Override
		
				public synchronized void run() {
					List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
					paramsList.add(new BasicNameValuePair("CHOSE", "4"));
					paramsList.add(new BasicNameValuePair("USERNAME", username));
					HttpUtil hu = new HttpUtil();
					String res = hu.doPost(LoginActivity.URL, "4", paramsList);
					Log.i("ressss", res);

					try {
						JSONArray array = new JSONArray(res);
						for (int i = array.length() - 1; i >=0; i--) {
							JSONObject obj = array.getJSONObject(i);
							Map<String, Object> item = new HashMap<String, Object>();
							item.put("item_img",
									R.drawable.headpic_defalut_image);
							item.put("name_text", obj.getString("USERNAME"));
							item.put("message_time",obj.getString("TWEET_TIME"));
							item.put("tweet_message", obj.getString("TWEET"));
							item.put("tweet_id",obj.getInt("TWEET_ID"));
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
		setContentView(R.layout.activity_home_page);

		title = (TextView) findViewById(R.id.title);
		backBtn = (Button) findViewById(R.id.back_btn);
		funBtn = (Button) findViewById(R.id.fun_btn);

		/* 修改标题 */
		Intent intent = this.getIntent();
		Bundle bl;
		bl = intent.getExtras();
		username = bl.getString("USERNAME", "石小六");
		title.setText(username);

		final String mUsername = username; 
		title.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomePageActivity.this,ModifyPwdActivity.class);
				Bundle bl = new Bundle();
				bl.putString("USERNAME", mUsername);
				intent.putExtras(bl);
				startActivity(intent);
			}
			
		});
		
		title.setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomePageActivity.this,PersonalCenterActivity.class);
				Bundle bl = new Bundle();
				bl.putString("BACK_USERNAME", username);
				bl.putString("FRONT_USERNAME",username);
				intent.putExtras(bl);
				startActivity(intent);
				return true;
			}
			
			
		});
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

		/*
		 * 不需要时，隐藏功能键 funBtn.setVisibility(View.INVISIBLE);
		 */
		final String newUsername = username;
		funBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(HomePageActivity.this,
						NewMicroblogActicity.class);
				Bundle bl = new Bundle();
				bl.putString("USERNAME", newUsername);
				intent.putExtras(bl);
				startActivity(intent);
				// finish();
			}
		});

		// listView = getListView();
	    getData();
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Map<String, Object> item = new HashMap<String, Object>();
		item.put("item_img",
				R.drawable.headpic_defalut_image);
		item.put("name_text","test");
		item.put("message_time","Test");
		item.put("tweet_message", "test");
		item.put("tweet_id",1);
		mData.add(item);*/
	    /*
		for (int i = 0; i < mData1.size(); i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("item_img",
					R.drawable.headpic_defalut_image);
			item.put("name_text", "test");
			item.put("message_time","1:1:1:1");
			item.put("tweet_message", "hahahaha");
			item.put("tweet_id",i);
			mData.add(mData1.get(i));	
		}*/
		listView = (ListView) findViewById(android.R.id.list);
		MyAdapter myAdapter = new MyAdapter(this);
		listView.setAdapter(myAdapter);
	}

	public final class ViewHolder {
		ImageView item_img;
		TextView name_text;
		TextView message_time;
		TextView tweet_message;
		Button favor_btn;
		Button tweet_comment;
		Button tweet_repost;
	}

	public class MyAdapter extends BaseAdapter {

		private LayoutInflater m_Inflater;

		public MyAdapter(Context context) {
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

			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = m_Inflater.inflate(R.layout.home_page_list_item,
						null); // 根据布局产生一个view
				holder.item_img = (ImageView) convertView
						.findViewById(R.id.item_img);
				holder.name_text = (TextView) convertView
						.findViewById(R.id.name_text);
				holder.message_time = (TextView) convertView
						.findViewById(R.id.message_time);
				holder.tweet_message = (TextView) convertView
						.findViewById(R.id.tweet_message);
				holder.favor_btn = (Button) convertView
						.findViewById(R.id.favor_btn);
				holder.tweet_comment = (Button) convertView
						.findViewById(R.id.tweet_comment);
				holder.tweet_repost = (Button) convertView
						.findViewById(R.id.tweet_repost);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.item_img
					.setBackgroundResource(R.drawable.headpic_defalut_image);
			holder.name_text.setText((String) mData.get(position).get(
					"name_text"));
			holder.message_time.setText((String) mData.get(position).get(
					"message_time"));
			holder.tweet_message.setText((String) mData.get(position).get(
					"tweet_message"));
            final String name_text = mData.get(position).get("name_text").toString();
            final int tweet_id = (Integer) mData.get(position).get("tweet_id");
            final String tweet = mData.get(position).get("tweet_message").toString();
            
            holder.item_img.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(HomePageActivity.this,PersonalCenterActivity.class);
					Bundle bl = new Bundle();
					bl.putString("BACK_USERNAME", username);
					bl.putString("FRONT_USERNAME",name_text);
					intent.putExtras(bl);
					startActivity(intent);
				}
            	
            });
            final boolean flag = false ;
			holder.favor_btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
						Toast.makeText(HomePageActivity.this, "已收藏 " ,Toast.LENGTH_LONG).show();
					
					// finish();
				}
			});
			holder.tweet_comment.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(HomePageActivity.this,
							CommentActivity.class);
					Bundle bl = new Bundle();
					bl.putString("USERNAME",username);
					bl.putInt("TWEET_ID",tweet_id);
	                intent.putExtras(bl);
					startActivity(intent);
					// finish();
				}
			});
			holder.tweet_repost.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(HomePageActivity.this,
							RepostActivity.class);
					Bundle bl = new Bundle();
					bl.putString("USERNAME", username);
					String newTweet = "Repost from " + name_text + ":" + tweet;
					bl.putString("TWEET",newTweet);
					intent.putExtras(bl);
					startActivity(intent);
					// finish();
				}
			});
			return convertView;
		}

	}

}
