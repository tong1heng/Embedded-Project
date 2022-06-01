package com.test.EmbeddedProject;

import com.test.database.DBOpenHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
private Button button1=null;
	
	private EditText editText1=null;
	private EditText editText2=null;
	private EditText editText3=null;
	
	private DBOpenHelper mDbOpenHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		
		button1=(Button)findViewById(R.id.btn_reg_register);
		editText1=(EditText)findViewById(R.id.edt_reg_username);
		editText2=(EditText)findViewById(R.id.edt_reg_password);
		editText3=(EditText)findViewById(R.id.edt_reg_phonenumber);
		mDbOpenHelper=new DBOpenHelper(this);
		
		button1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name=editText1.getText().toString().trim();
				String password=editText2.getText().toString().trim();
				String phone=editText3.getText().toString().trim();
				SQLiteDatabase db=mDbOpenHelper.getWritableDatabase();
				ContentValues values=new ContentValues();
				if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(phone)){
					values.put("name", name);
					values.put("password", password);
					values.put("phone", phone);
					db.insert("user", null, values);
					Intent intent=new Intent(RegisterActivity.this,WelcomeActivity.class);
					startActivity(intent);
					overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
					finish();
					Toast.makeText(RegisterActivity.this, "×¢²á³É¹¦£¡", Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(RegisterActivity.this, "Î´ÍêÉÆÐÅÏ¢£¬×¢²áÊ§°Ü£¡", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
