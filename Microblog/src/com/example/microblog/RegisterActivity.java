package com.example.microblog;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.util.HttpUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private String username_bak;
	private TextView title;
	private Button backBtn;
	private Button funBtn;
    Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
				 Bundle bundle = new Bundle();
			      bundle.putString("USERNAME",username_bak);
			      Intent intent = new Intent(RegisterActivity.this,HomePageActivity.class);
			      intent.putExtras(bundle);
			      startActivity(intent);
			      break;
			case -1:
			      Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
			      break;
			case -2:
			      Toast.makeText(RegisterActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.register);
		Button register = (Button) findViewById(R.id.confirm_register);

		
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
		
		/*不需要时，隐藏返回键
		backBtn.setVisibility(View.INVISIBLE);
		*/
		
		//不需要时，隐藏功能键
		funBtn.setVisibility(View.INVISIBLE);
		title.setText("注册");
		
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
            try{
				new Thread(new Runnable() {
					@Override
					public void run() {

						String username = ((TextView) findViewById(R.id.register_username))
								.getText().toString();
						String password = ((TextView) findViewById(R.id.register_password))
								.getText().toString();
						String repassword = ((TextView) findViewById(R.id.register_repassword))
								.getText().toString();
						if (password.equals(repassword)) {
							List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
							paramsList.add(new BasicNameValuePair("CHOSE",
									"2"));
							paramsList.add(new BasicNameValuePair("USERNAME",
									username));
							username_bak = username;
							BasicNameValuePair basic = new BasicNameValuePair("PASSWORD",password);
							paramsList.add(basic);
							HttpUtil hu = new HttpUtil();
							String res = hu.doPost(LoginActivity.URL, "2", paramsList);
							Log.i("ressss", res);

							try {
								JSONObject obj = new JSONObject(res);
								String ret;
								ret = obj.getString("Response");
								if (ret.equals("1")) {
									//Toast.makeText(RegisterActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
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

						} else {
							Message msg = new Message();
                     		msg.what = -2;
                     		mHandler.sendMessage(msg);

						}

					}
				}).start();
				}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			}

		});
	}

}
