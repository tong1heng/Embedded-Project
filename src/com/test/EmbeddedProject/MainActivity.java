package com.test.EmbeddedProject;

import java.text.SimpleDateFormat;

import com.test.database.DBOpenHelper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	
	private Button button1=null;
	private Button button2=null;
	private Button button3=null;
	
	private TextView textView1_1=null;
	private TextView textView2_1=null;
	private TextView textView1_2=null;
	private TextView textView2_2=null;
	private TextView textView1_3=null;
	private TextView textView2_3=null;
	private TextView textView1_4=null;
	private TextView textView2_4=null;
	private TextView textView1_5=null;
	private TextView textView2_5=null;
	
	private DBOpenHelper mDbOpenHelper=new DBOpenHelper(this);
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

}
