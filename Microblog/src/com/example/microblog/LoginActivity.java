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

public class LoginActivity extends Activity {

	private String username_bak;
	//public static final String URL = "http://10.0.2.2:8080/HttpTest/login";
	public static final String URL = "http://192.168.1.125:8080/HttpTest/login";
    private int follow, fans;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(LoginActivity.this, "µÇÂ½³É¹¦",
						Toast.LENGTH_SHORT).show();
				
				 Bundle bundle = new Bundle();
				 bundle.putString("USERNAME",username_bak); 
				 Intent intent = new Intent(LoginActivity.this,HomePageActivity.class);
				 intent.putExtras(bundle); startActivity(intent);
				// finish();
				break;
			case -1:
				Toast.makeText(LoginActivity.this, "µÇÂ½Ê§°Ü",
						Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.login);

		TextView register = (TextView) findViewById(R.id.register);
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toLoginActivity = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(toLoginActivity);
				// finish();
			}
		});

		Button login = (Button) findViewById(R.id.loginbutton);

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					new Thread(new Runnable() {
						@Override
						public void run() {

							String username = ((TextView) findViewById(R.id.login_username))
									.getText().toString();
							String password = ((TextView) findViewById(R.id.login_password))
									.getText().toString();
							List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
							paramsList
									.add(new BasicNameValuePair("CHOSE", "1"));
							paramsList.add(new BasicNameValuePair("USERNAME",
									username));
							username_bak = username;
							BasicNameValuePair basic = new BasicNameValuePair(
									"PASSWORD", password);
							paramsList.add(basic);
							HttpUtil hu = new HttpUtil();
							String res = hu.doPost(LoginActivity.URL, "1", paramsList);
							Log.i("ressss", res);

							try {
								JSONObject obj = new JSONObject(res);
								String ret;
								ret = obj.getString("Response");
							//	follow = obj.getInt("FOLLOW");
							//	fans = obj.getInt("FANS");
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
	}

}
