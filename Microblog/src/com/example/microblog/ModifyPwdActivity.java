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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyPwdActivity extends Activity{
	public static final String URL = "http://10.0.2.2:8080/HttpTest/login";
	public static String username="";
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(ModifyPwdActivity.this, "修改成功",Toast.LENGTH_SHORT).show();
				
				 Bundle bundle = new Bundle();
				 bundle.putString("USERNAME",username); 
				 Intent intent = new Intent(ModifyPwdActivity.this,HomePageActivity.class);
				 intent.putExtras(bundle); startActivity(intent);
				 
				break;
			case -1:
				Toast.makeText(ModifyPwdActivity.this, "修改失败",Toast.LENGTH_SHORT).show();
				break;
			default:
				break;

			}
			super.handleMessage(msg);
		}
	};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_password);		
		
		Bundle bl;
		Intent intent = this.getIntent();
		bl = intent.getExtras();
		username = bl.getString("USERNAME", "石小六");
    
		TextView usernameTxt = (TextView)findViewById(R.id.modify_pwd_username);
		final EditText passwordEdit = (EditText)findViewById(R.id.modify_pwd_old);
		final EditText newPasswordEdit = (EditText)findViewById(R.id.register_password);
		final EditText reNewPasswordEdit = (EditText)findViewById(R.id.modify_pwd_confirm_new);
		Button confirmBtn = (Button) findViewById(R.id.modify_pwd_confirm_btn);
		
		usernameTxt.setText(username);

		Button backBtn;
		backBtn = (Button) findViewById(R.id.back_btn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});
		Button funbt = (Button)findViewById(R.id.fun_btn);
		funbt.setVisibility(View.INVISIBLE);
		
		confirmBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				final String password=passwordEdit.getText().toString().trim();
				final String newPassword = newPasswordEdit.getText().toString().trim();
				String reNewPassword = reNewPasswordEdit.getText().toString().trim();
				if(newPassword.endsWith(reNewPassword)){
					try {
						new Thread(new Runnable() {
							@Override
							public void run() {

								
								List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
								paramsList.add(new BasicNameValuePair("CHOSE", "7"));
								paramsList.add(new BasicNameValuePair("USERNAME",username));
								paramsList.add(new BasicNameValuePair("PASSWORD",password));
								paramsList.add(new BasicNameValuePair("NEWPASSWORD",newPassword));

								HttpUtil hu = new HttpUtil();
								String res = hu.doPost(URL, "7", paramsList);
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
				else
				{
					Toast.makeText(ModifyPwdActivity.this, "两次密码不一致",Toast.LENGTH_SHORT).show();
					
				}					
			}			
		});		
	}
}
