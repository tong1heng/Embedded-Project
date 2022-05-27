package com.test.EmbeddedProject;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.test.EmbeddedProject.MainActivity;
import com.test.database.DBOpenHelper;
import com.test.database.HistoryRecord;
import com.test.nfc.NfcActivity;
import com.test.qrcode.activity.CameraActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
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
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.main);
        show_data();
		button1=(Button)findViewById(R.id.btn_main_qrcode);
		button2=(Button)findViewById(R.id.btn_main_nfc);
//		button3=(Button)findViewById(R.id.btn_main_other);
		
		button1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				insert_data("扫码");
				show_data();
				Intent intent=new Intent(MainActivity.this,CameraActivity.class);
				startActivity(intent);
			}
		});
		
		button2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				insert_data("NFC");
				show_data();
				Intent intent=new Intent(MainActivity.this,NfcActivity.class);
				startActivity(intent);
			}
		});
    }
    
	private void insert_data(String type){
		SQLiteDatabase db=mDbOpenHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("type", type);
		values.put("time", df.format(new Date()));
		db.insert("opp", null, values);
	}
	
	private void show_data(){
		
		textView1_1=(TextView)findViewById(R.id.txt_main_db1_1);
		textView2_1=(TextView)findViewById(R.id.txt_main_db2_1);
		textView1_2=(TextView)findViewById(R.id.txt_main_db1_2);
		textView2_2=(TextView)findViewById(R.id.txt_main_db2_2);
		textView1_3=(TextView)findViewById(R.id.txt_main_db1_3);
		textView2_3=(TextView)findViewById(R.id.txt_main_db2_3);
		textView1_4=(TextView)findViewById(R.id.txt_main_db1_4);
		textView2_4=(TextView)findViewById(R.id.txt_main_db2_4);
		textView1_5=(TextView)findViewById(R.id.txt_main_db1_5);
		textView2_5=(TextView)findViewById(R.id.txt_main_db2_5);
		
		SQLiteDatabase db=mDbOpenHelper.getWritableDatabase();
		
		Cursor cursor=db.query("opp", null, null, null, null, null, "id DESC");
		String time = null,type=null;
		int count=0;
		HistoryRecord[] list=new HistoryRecord[5];
		for(int i=0;i<5;i++){
			list[i]=new HistoryRecord("","");
		}
		if(cursor.moveToFirst()){
			do{
				type = cursor.getString(cursor.getColumnIndex("type"));
				time = cursor.getString(cursor.getColumnIndex("time"));
				list[count]=new HistoryRecord(type, time);
				count++;
				if(count==5) break;
			}while(cursor.moveToNext());
		}
		cursor.close();
		textView1_1.setText(list[0].getType());
		textView1_2.setText(list[1].getType());
		textView1_3.setText(list[2].getType());
		textView1_4.setText(list[3].getType());
		textView1_5.setText(list[4].getType());
		
		textView2_1.setText(list[0].getTime());
		textView2_2.setText(list[1].getTime());
		textView2_3.setText(list[2].getTime());
		textView2_4.setText(list[3].getTime());
		textView2_5.setText(list[4].getTime());
	}
}
