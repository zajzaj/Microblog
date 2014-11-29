package com.example.microblog;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.util.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyMicroblog extends Activity {

	private String username;
	private int tweet_id;
	private TextView title;
	private Button backBtn;
	private Button funBtn;
	
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(ModifyMicroblog.this, "修改成功",
						Toast.LENGTH_SHORT).show();
				finish();
				break;
			case -1:
				Toast.makeText(ModifyMicroblog.this, "修改失败",
						Toast.LENGTH_SHORT).show();
				finish();
				break;
			default:
				break;

			}
			super.handleMessage(msg);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_microblog);
		Intent intent = this.getIntent();
		Bundle bl = intent.getExtras();
		username = bl.getString("USERNAME");
		tweet_id = bl.getInt("TWEET_ID");
		
		title = (TextView) findViewById(R.id.title);
		backBtn = (Button) findViewById(R.id.back_btn);
		funBtn = (Button) findViewById(R.id.fun_btn);
		funBtn.setVisibility(View.INVISIBLE);
		
		title.setText("修改微博");

		/* 返回键，关闭当前界面 */
		backBtn = (Button) findViewById(R.id.back_btn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});
		Button confirm_publish = (Button) findViewById(R.id.modify_confirm_publish);
		
		confirm_publish.setOnClickListener(new OnClickListener() {
			final String newUsername = username;

			@Override
			public void onClick(View v) {
				try {
					new Thread(new Runnable() {
						@Override
						public void run() {

							List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
							paramsList
									.add(new BasicNameValuePair("CHOSE", "8"));
							paramsList.add(new BasicNameValuePair("USERNAME",
									newUsername));
							paramsList.add(new BasicNameValuePair("TWEET_ID",String.valueOf(tweet_id)));
							String comment = ((TextView) findViewById(R.id.modify_comment))
									.getText().toString();
							paramsList.add(new BasicNameValuePair("COMMENT",
									comment));

							HttpUtil hu = new HttpUtil();
							String res = hu.doPost(LoginActivity.URL, "8", paramsList);
							Log.i("ressss", res);

							try {
								JSONObject obj = new JSONObject(res);
								String ret;
								ret = obj.getString("Response");
								if (ret.equals("1")) {
									Message msg = new Message();
									msg.what = 1;
									mHandler.sendMessage(msg);

								} else if (ret.equals("-1")) {
									Message msg = new Message();
									msg.what = -1;
									mHandler.sendMessage(msg);
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
		});
		Button cancel = (Button)findViewById(R.id.modify_cancel_button);
		cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				
			}
			
		}
		);
	}
}
