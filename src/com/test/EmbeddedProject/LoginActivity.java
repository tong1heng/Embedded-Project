package com.test.EmbeddedProject;

import com.test.database.DBOpenHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button button1=null;
	private EditText editText1=null;
	private EditText editText2=null;
	private DBOpenHelper mDbOpenHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		
		button1=(Button)findViewById(R.id.btn_log_login);
		editText1=(EditText)findViewById(R.id.edt_log_username);
		editText2=(EditText)findViewById(R.id.edt_log_password);
		mDbOpenHelper=new DBOpenHelper(this);
		
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name1=editText1.getText().toString().trim();
				String password1=editText2.getText().toString().trim();
				if(!TextUtils.isEmpty(name1)&&!TextUtils.isEmpty(password1))
				{
					boolean match=false;
					SQLiteDatabase db=mDbOpenHelper.getWritableDatabase();
					Cursor cursor=db.query("user", null, null, null, null, null, null);
					if(cursor.moveToFirst()){
						do{
				            String name2 = cursor.getString(cursor.getColumnIndex("name"));
				            String password2 = cursor.getString(cursor.getColumnIndex("password"));
				            if(name1.equals(name2)&&password1.equals(password2)){
				            	match=true;
				            	break;
				            }
				        }while(cursor.moveToNext());
					}
					cursor.close();
					if(match){
						Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
						Intent intent=new Intent(LoginActivity.this,MainActivity.class);
						startActivity(intent);
						finish();
					}
					else {
						Toast.makeText(LoginActivity.this, "密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
					}
				}
				else{
						Toast.makeText(LoginActivity.this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
