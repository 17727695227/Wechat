package com.wechat.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wechat.model.TokenMsg;
import com.wechat.util.CheckUtil;
import com.wechat.util.MessageUtil;

@Controller
public class WechatController {

	//接入是否成功
	@RequestMapping(value="/wx",method=RequestMethod.GET)
	@ResponseBody
	public String vilader(TokenMsg tokenMsg,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		if(CheckUtil.checkSignatrue(tokenMsg)){
			out.print(tokenMsg.getEchostr());
		}
		return null;
	}
	
	//接入是否成功
	@RequestMapping(value="/wx",method=RequestMethod.POST)
	@ResponseBody
	public void reciveMsg(HttpServletRequest request,
			HttpServletResponse response)throws Exception{
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		
//		PrintWriter out = response.getWriter();
//		try {
//			Map<String, String> map = MessageUtil.xmlToMap(request);
//			String fromUserName = map.get("FromUserName");
//			String toUserName = map.get("ToUserName");
//			String msgType = map.get("MsgType");
//			String content = map.get("Content");
//			
//			String message =null;
//			if("text".equals(msgType)){
//				TextMessage textMessage = new TextMessage();
//				textMessage.setFromUserName(toUserName);
//				textMessage.setToUserName(fromUserName);
//				textMessage.setMsgType("text");
//				textMessage.setCreateTime(String.valueOf((new Date().getTime())));
//				textMessage.setContent("您发送的消息是:"+content);
//				message = MessageUtil.textMessageToXml(textMessage);
//				System.out.println(message);
//			}
//			out.print(message);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			out.close();
//		}
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		   //将传来的xml解析成消息对象，再将消息对象转为xml传给微信后台
		   PrintWriter out=response.getWriter();
		   try {
				Map<String,String> map=MessageUtil.xmlToMap(request);
				String fromUserName=map.get("FromUserName");
				String toUserName=map.get("ToUserName");
				String msgType=map.get("MsgType");
				String content=map.get("Content");
				String message=null;
				if(MessageUtil.MSGTYPE_TEXT.equals(msgType)){
					if("1".equals(content)){//关键字判断
						message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstSubMenu());
					}else if("2".equals(content)){
//						message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondSubMenu());
					    message=MessageUtil.initNewsMessage(toUserName, fromUserName);
					}else if("3".equals(content)){//图片回复
						message=MessageUtil.initImageMessage(toUserName, fromUserName);
					}else if("4".equals(content)){//图片回复
						message=MessageUtil.initMusicMessage(toUserName, fromUserName);
					}else if("?".equals(content)||"？".equals(content)){//关键字判断
						message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
					}else{//这一段我自己添加的，如果未输入关键字的情况 显示未处理
						
						message=MessageUtil.initText(toUserName, fromUserName, "您输入的消息是:"+content);
					
					}
				}else if(MessageUtil.MSGTYPE_EVENT.equals(msgType)){
					      //事件类型再去细化判断
					      String eventType=map.get("Event");
					      if(MessageUtil.EVENT_SUBSCRIBE.equals(eventType)){
						  message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
					       }
				}else{
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
				
				out.print(message);
				System.out.println(message);
			} catch (DocumentException e) {
				e.printStackTrace();
			}finally{
				out.close();
			}
	}
	
}
