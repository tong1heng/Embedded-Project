package com.test.EmbeddedProject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AnimationActivity extends Activity {
	/** Called when the activity is first created. */
	private ImageView img;
	private Animation animation;//ÉùÃ÷AnimationÀà
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.animation);
        img = (ImageView) findViewById(R.id.tween_img);
        
        animation = AnimationUtils.loadAnimation(AnimationActivity.this, R.drawable.anim_progress);
//        img.startAnimation(animation);
//        try {
//            Thread.currentThread().sleep(2000);
//        } catch(InterruptedException ie){
//             ie.printStackTrace();
//        }
        Intent intent=new Intent(AnimationActivity.this,WelcomeActivity.class);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
