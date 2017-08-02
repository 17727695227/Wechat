package com.wechat.test;

import net.sf.json.JSONObject;

import com.wechat.model.AccessToken;
import com.wechat.util.WeixinUtil;



public class WeixinTest {
	public static void main(String[] args) {
	
			try {
				AccessToken token = WeixinUtil.getAccessToken();
			    System.out.println("票据"+token.getToken());
				System.out.println("有效时间"+token.getExpiresIn());
				

				String menu=JSONObject.fromObject(WeixinUtil.initMenu()).toString();
				int result=WeixinUtil.createMenu(token.getToken(), menu);
				if (result==0) {
					System.out.println("成功");
				}else {
					System.out.println("失败");
				}
				
//				String path = "E:/tree.jpg";
//				String mediaId = WeixinUtil.upload(path, token.getToken(), "thumb");
//				System.out.println("mediaId:"+mediaId);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
		
}
