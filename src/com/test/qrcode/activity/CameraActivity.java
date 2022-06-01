package com.test.qrcode.activity;


import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.EmbeddedProject.R;
import com.test.qrcode.camera.activity.CaptureActivity;

public class CameraActivity extends Activity {
	private static int fd;
	private TextView resultTextView;
	private String scanResult;
	private static boolean flag;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);
        
        resultTextView = (TextView) this.findViewById(R.id.tv_scan_result);
        
        Button scanBarCodeButton = (Button) this.findViewById(R.id.btn_scan_barcode);
        scanBarCodeButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent openCameraIntent = new Intent(CameraActivity.this,CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
			}
		});
        
        //初始化LED
        String path = new String("/dev/ledtest");
		fd = LedDeviceOpen(path);	//调用jni函数
		LedDeviceIoctl(0, 0);
		LedDeviceIoctl(0, 1);
		LedDeviceIoctl(0, 2);
		LedDeviceIoctl(0, 3);
		
		//根据扫码内容点亮对应LED
		Button buttonlightButton=(Button)findViewById(R.id.btn_light_barcode);
		buttonlightButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!flag){
					Toast.makeText(CameraActivity.this, "请先扫码", Toast.LENGTH_SHORT).show();
					return;
				}
				if(scanResult.equals("Others")){
					LedDeviceIoctl(1, 0);
					beep();
					Toast.makeText(CameraActivity.this, "LED 1 已点亮", Toast.LENGTH_SHORT).show();
				}else if(scanResult.equals("仝一恒")){
					LedDeviceIoctl(1, 1);
					beep();
					Toast.makeText(CameraActivity.this, "LED 2 已点亮", Toast.LENGTH_SHORT).show();
				}else if(scanResult.equals("马兆佳")){
					LedDeviceIoctl(1, 2);
					beep();
					Toast.makeText(CameraActivity.this, "LED 3 已点亮", Toast.LENGTH_SHORT).show();
				}else if(scanResult.equals("周志达")){
					LedDeviceIoctl(1, 3);
					beep();
					Toast.makeText(CameraActivity.this, "LED 4 已点亮", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(CameraActivity.this, "不匹配，请重新扫描", Toast.LENGTH_SHORT).show();
				}
				flag=false;
			}
		});
		
	    Button buttonclslightButton=(Button)findViewById(R.id.btn_clslight_barcode);
	    buttonclslightButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LedDeviceIoctl(0, 0);
				LedDeviceIoctl(0, 1);
				LedDeviceIoctl(0, 2);
				LedDeviceIoctl(0, 3); 
				Toast.makeText(CameraActivity.this, "LED 已全部熄灭", Toast.LENGTH_SHORT).show();
			}
		});
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	
		if (resultCode == RESULT_OK) {
			flag=true;
			Bundle bundle = data.getExtras();
			scanResult = bundle.getString("result");
			resultTextView.setText(scanResult);
		}
	}
	private void beep() {
		LedDeviceIoctl(0, 4);
		for(int i=1;i<=100;i++)
			for(int j=1;j<=1000;j++)
				for(int k=1;k<=1000;k++);
		LedDeviceIoctl(1, 4);
	}
	/* 添加函数声明,告诉编译和链接器该方法在本地代码中实现 */
	private native int LedDeviceOpen(String path);
	private native void LedDeviceIoctl(int cmd, int arg);
	private native void LedDeviceClose();
	
	/* 加载JNI代码编译生成的共享库 */
	static {
		System.loadLibrary("led-jni");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		LedDeviceClose(); 
		super.onDestroy();
	}
}