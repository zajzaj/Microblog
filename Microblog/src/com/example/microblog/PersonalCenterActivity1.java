package com.example.microblog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class PersonalCenterActivity1  extends ListActivity{
	/*head*/
	private TextView title;
	private Button backBtn;
	private Button funBtn;
	
	private String home_page_username="石小六";
	private ListView listView;
	private String[] mListImg;
	private String[] mListName = { "祖米拉","延毕毕", "祖米拉","延毕毕", "祖米拉","延毕毕", "祖米拉","延毕毕"};  
    private String[] mListTime = { "2014-10-26 13:06","2014-10-26 15:06","2014-10-26 13:06","2014-10-26 15:06","2014-10-26 13:06","2014-10-26 15:06","2014-10-26 13:06","2014-10-26 15:06"};  
    private String[] mListTweet = { "我要延毕毕！","感觉我爱上我室友了！","我要延毕毕！","感觉我爱上我室友了！","我要延毕毕！","感觉我爱上我室友了！","我要延毕毕！","感觉我爱上我室友了！"};  
    private String[] mListRepostCount={"0","1","2","3","4","5","6","1000"};
    private String[] mListCommentCount={"0","1","2","3","4","5","6","1000"};
    ArrayList<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();
    

	@Override
	protected void onCreate( Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_center);
		
		title = (TextView) findViewById(R.id.title);
		backBtn = (Button) findViewById(R.id.back_btn);
		funBtn = (Button) findViewById(R.id.fun_btn);
		
		/*修改标题*/
		title.setText(home_page_username);
		
		/*返回键，关闭当前界面*/
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
		
		/*不需要时，隐藏功能键
		funBtn.setVisibility(View.INVISIBLE);
		*/
		
		listView = getListView();
		 int lengh = mListName.length;  
		    for(int i =0; i < lengh; i++) {  
		        Map<String,Object> item = new HashMap<String,Object>();  
		        item.put("item_img", R.drawable.headpic_defalut_image);  
		        item.put("name_text", mListName[i]);  
		        item.put("message_time", mListTime[i]);  
		        item.put("tweet_message", mListTweet[i]);
		        item.put("repost_count", mListRepostCount[i]);
		        item.put("comment_count", mListCommentCount[i]);
		        mData.add(item);   
		    }  
		    SimpleAdapter adapter = new SimpleAdapter(this,mData,R.layout.personal_center_list_item,  
		        new String[]{"item_img","name_text","message_time","tweet_message","repost_count",
		    		"comment_count"},new int[]{R.id.personal_item_img,R.id.personal_name_text,
		    		R.id.message_time,R.id.personal_tweet_message,R.id.personal_tweet_repost_count,
		    		R.id.personal_tweet_comment_count});  
		        setListAdapter(adapter);
		    }  
}
