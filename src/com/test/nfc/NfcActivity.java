package com.test.nfc;

import java.io.IOException;

import android.R.bool;
import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.EmbeddedProject.R;
import com.test.socket.ClientSocketThread;
import com.test.socket.ClientSocketThread.MessageListener;
import com.test.socket.Const;
import com.test.socket.clientSocketTools;

import java.util.concurrent.TimeUnit;

public class NfcActivity extends Activity{
	
	private static int fd;
	
	private Button button1=null;
	private Button button2=null;
	private Button button3=null;
	private TextView textView1=null;
	private String s;//检测到的nfc卡号
	private ClientSocketThread clientSocketThread=null;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg){
			s=(String) msg.obj;
			s=s.trim();
			textView1.setText(s);
			
		}
	};
	private byte[] data;

	//注册NFC卡号
	private final String nfc1="0056B3F178";
	private final String nfc2="00C68DD778";
	private final String nfc3="00F6110679";
	
	private boolean isOpen = false;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.nfc);
		
		button1=(Button)findViewById(R.id.btn_nfc_find);
		button2=(Button)findViewById(R.id.btn_nfc_light_open);
		button3=(Button)findViewById(R.id.btn_nfc_light_close);
		textView1=(TextView)findViewById(R.id.txt_nfc_result);
		
		light(2);
		isOpen = false;
		
		new Thread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				clientSocketThread=ClientSocketThread.getClientSocket(clientSocketTools.getLocalIpAddress(), Const.SERVER_PORT);
				clientSocketThread.setListener(new MessageListener() {
					public void Message(byte[] message, int message_len) {
						// TODO Auto-generated method stub
						handler.sendMessage(handler.obtainMessage(100,"\n"+clientSocketTools.byte2hex(message, message_len)));
					}
				});
			}
		}).start();
		
		// 扫卡
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				beep(0);
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				beep(1);
				// TODO Auto-generated method stub
				try {
					clientSocketThread.getOutputStream().write(Const.nfc_open);
					for(int iii=1;iii<=10;iii++)
						for(int jjj=1;jjj<=1000;jjj++)
							for(int kkk=1;kkk<=1000;kkk++);
					clientSocketThread.getOutputStream().write(Const.nfc_read);
					clientSocketThread.getOutputStream().write(Const.matrix_open);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.print(s);
				Toast.makeText(NfcActivity.this, s, Toast.LENGTH_SHORT).show();
				
			}
		});
		
		// 开门
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (s.equals(nfc1) || s.equals(nfc2) || s.equals(nfc3)) {
					if (!isOpen){
						// TODO : 让所有的东西 on
						led_open(0);
						led_open(1);
						led_open(2);
						led_open(3);
						moto_opp(1);//向左旋转
//						light(1);
						beep(0);
						Toast.makeText(NfcActivity.this, "正在开门", Toast.LENGTH_SHORT).show();
						
						// TODO: sleep 3
						try {
							TimeUnit.SECONDS.sleep(3);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						// TODO: 让所有的东西off
						led_closed();
						moto_opp(0);
						light(0);
						beep(1);
						Toast.makeText(NfcActivity.this, "已开门", Toast.LENGTH_SHORT).show();
						
						
						s="";
						isOpen = true;
						light(1);
					
					} else {
						Toast.makeText(NfcActivity.this, "已开门", Toast.LENGTH_SHORT).show();
					}
					
//				}else if (s.equals(nfc2)) {
//					led_open(1);
//					moto_opp(2);//向右旋转
//					light(2);
//					beep();
//					Toast.makeText(NfcActivity.this, "LED 1 已点亮", Toast.LENGTH_SHORT).show();
//					s="";
				}else if (s.equals("")){
					Toast.makeText(NfcActivity.this, "请先扫描NFC！", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(NfcActivity.this, "NFC未注册！", Toast.LENGTH_SHORT).show();
					s="";
				}
			}
		});
		
		// 关门
		button3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				
				if (s.equals(nfc1) || s.equals(nfc2) || s.equals(nfc3)) {
					if (isOpen){
						// TODO : 让所有的东西 on
						led_open(0);
						led_open(1);
						led_open(2);
						led_open(3);
						moto_opp(2);//向左旋转
						light(0);
						beep(0);
						Toast.makeText(NfcActivity.this, "正在关门", Toast.LENGTH_SHORT).show();
						
						// TODO: sleep 3
						try {
							TimeUnit.SECONDS.sleep(3);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						// TODO: 让所有的东西off
						led_closed();
						moto_opp(0);
						light(0);
						beep(1);
						Toast.makeText(NfcActivity.this, "已关门", Toast.LENGTH_SHORT).show();
						
						
						s="";
						isOpen = false;
						light(2);
					} else {
						Toast.makeText(NfcActivity.this, "已关门", Toast.LENGTH_SHORT).show();
					}
					
				}
				
				
				// TODO Auto-generated method stub
//				led_closed();
//				moto_opp(0);
//				light(0);
//				Toast.makeText(NfcActivity.this, "LED 已全部熄灭", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void led_open(int x) {
		try {
			if(0==x){
				Runtime.getRuntime().exec("ioctl -d /dev/ledtest 1 0");
			}else if (x==1) {
				Runtime.getRuntime().exec("ioctl -d /dev/ledtest 1 1");
			}else if (x==2) {
				Runtime.getRuntime().exec("ioctl -d /dev/ledtest 1 2");
			}else if (x==3) {
				Runtime.getRuntime().exec("ioctl -d /dev/ledtest 1 3");
			}else {}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void led_closed() {
		try {
			Runtime.getRuntime().exec("ioctl -d /dev/ledtest 0 0");
			Runtime.getRuntime().exec("ioctl -d /dev/ledtest 0 1");
			Runtime.getRuntime().exec("ioctl -d /dev/ledtest 0 2");
			Runtime.getRuntime().exec("ioctl -d /dev/ledtest 0 3");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			clientSocketThread.getOutputStream().write(Const.matrix_close);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void moto_opp(int opp) {
		if(opp==0){
			try {
				clientSocketThread.getOutputStream().write(Const.moto_stop);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (opp==1) {
			try {
				clientSocketThread.getOutputStream().write(Const.moto_left);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (opp==2) {
			try {
				clientSocketThread.getOutputStream().write(Const.moto_right);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			return;
		}
	}
	private void beep(int sw) {
		
		if (sw == 0){
			try {
				Runtime.getRuntime().exec("ioctl -d /dev/ledtest 0 4");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (sw == 1) {
			try {
				Runtime.getRuntime().exec("ioctl -d /dev/ledtest 1 4");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		
		
//		for(int i=1;i<=100;i++)
//			for(int j=1;j<=1000;j++)
//				for(int k=1;k<=1000;k++);
//		try {
//			Runtime.getRuntime().exec("ioctl -d /dev/ledtest 1 4");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
	}
	private void light(int opp) {
		if(opp==0){
			try {
				clientSocketThread.getOutputStream().write(Const.blight_close);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (opp==1) {
			try {
				clientSocketThread.getOutputStream().write(Const.blight_green);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (opp==2) {
			try {
				clientSocketThread.getOutputStream().write(Const.blight_red);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			return;
		}
	}
}
