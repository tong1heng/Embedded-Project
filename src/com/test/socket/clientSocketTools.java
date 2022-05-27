package com.test.socket;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class clientSocketTools {
	/**
	 * get localhost IP address
	 * 
	 * @return IP address
	 */
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()&& !inetAddress.isLinkLocalAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return null;
	}	
	/**
	 * ��ȡ��Ļ��Density
	 * 
	 * @param context
	 *            �����Ķ���
	 * @return density
	 */
	public static float getScreenDensity(Context context) {
		try {
			DisplayMetrics dm = new DisplayMetrics();
			WindowManager manager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			manager.getDefaultDisplay().getMetrics(dm);
			return dm.density;
		} catch (Exception ex) {

		}
		return 1.0f;
	}
	/** 
     * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
	/**
	 * ��ȡ��Ļ��DisplayMetrics
	 * 
	 * @param context
	 *            �����Ķ���
	 * @return DisplayMetrics
	 */
	public static DisplayMetrics getDisplayMetrics(Context context) {
		try {
			DisplayMetrics dm = new DisplayMetrics();
			WindowManager manager = (WindowManager) context .getSystemService(Context.WINDOW_SERVICE);
			manager.getDefaultDisplay().getMetrics(dm);
			return dm;
		} catch (Exception ex) {

		}
		return null;
	}
    /**
     * java�ֽ���ת�ַ���
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b,int len) { //һ���ֽڵ�����
        // ת��16�����ַ���
        String hs = "";
        String tmp = "";
        for (int n = 0; n < len; n++) {
            //����ת��ʮ�����Ʊ�ʾ
            tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (tmp.length() == 1) {
                hs = hs + "0" + tmp;
            } else {
                hs = hs + tmp;
            }
        }
        tmp = null;
        return hs.toUpperCase(); //ת�ɴ�д
    }
    /**
     * java�ֽ���ת�ַ���
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b,int start, int len) { //һ���ֽڵ�����
        // ת��16�����ַ���
        String hs = "";
        String tmp = "";
        for (int n = start; n < len; n++) {
            //����ת��ʮ�����Ʊ�ʾ
 
            tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (tmp.length() == 1) {
                hs = hs + "0" + tmp;
            } else {
                hs = hs + tmp;
            }
        }
        tmp = null;
        return hs.toUpperCase(); //ת�ɴ�д
    }
    /** 
     * �ֽ�ת��Ϊ����
     *  
     * @param b �ֽڣ�����4���ֽڣ� 
     * @param index ��ʼλ�� 
     * @return 
     */  
    public static int byte2int(byte[] b, int index) {    
        int temp;                                             
        temp = b[index + 0];                                  
        temp &= 0xff;                                         
        temp |= (b[index + 1] << 8);                   
        temp &= 0xffff;                                       
        temp |= (b[index + 2] << 16);                  
        temp &= 0xffffff;                                     
        temp |= (b[index + 3] << 24);                  
        return temp;                    
    }  
    /** 
     * �ֽ�ת��Ϊ���� 
     *  
     * @param b �ֽڣ�����4���ֽڣ� 
     * @param index ��ʼλ�� 
     * @return 
     */  
    public static float byte2float(byte[] b, int index) {    
        int temp;                                             
        temp = b[index + 0];                                  
        temp &= 0xff;                                         
        temp |= ((long) b[index + 1] << 8);                   
        temp &= 0xffff;                                       
        temp |= ((long) b[index + 2] << 16);                  
        temp &= 0xffffff;                                     
        temp |= ((long) b[index + 3] << 24);                  
        return Float.intBitsToFloat(temp);                    
    }  
}
