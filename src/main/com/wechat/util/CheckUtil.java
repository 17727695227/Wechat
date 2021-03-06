package com.wechat.util;


import java.security.MessageDigest;
import java.util.Arrays;

import com.wechat.model.TokenMsg;


public class CheckUtil {

	private static final String token = "wechat";
	public static boolean checkSignatrue(TokenMsg tokenMsg){
		
		String[] arr = new String[]{token,tokenMsg.getTimestamp(),tokenMsg.getNonce()}; 
		Arrays.sort(arr);//排序
		//生成字符串
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		//sha1加密
		String temp=getSha1(content.toString());
		return temp.equals(tokenMsg.getSignature());
	}
	public static String getSha1(String str){
		   if (null == str || 0 == str.length()){
		       return null;
		   }
		   char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
		           'a', 'b', 'c', 'd', 'e', 'f'};
		   try {
		       MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
		       mdTemp.update(str.getBytes("UTF-8"));
		        
		       byte[] md = mdTemp.digest();
		       int j = md.length;
		       char[] buf = new char[j * 2];
		       int k = 0;
		       for (int i = 0; i < j; i++) {
		           byte byte0 = md[i];
		           buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
		           buf[k++] = hexDigits[byte0 & 0xf];
		       }
		       return new String(buf);
		   } catch (Exception e) {
		       return null;
		   }
		}
}
