package com.test.EmbeddedProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WelcomeActivity extends Activity {
/** Called when the activity is first created. */
	
	private Button button1=null;
	private Button button2=null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome);
        
        button1=(Button)findViewById(R.id.btn_wel_login);
        button2=(Button)findViewById(R.id.btn_wel_register);
        
        button1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
				startActivity(intent);
			}
		});
        button2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(WelcomeActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
    }
}
