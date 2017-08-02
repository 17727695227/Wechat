package com.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.wechat.model.Image;
import com.wechat.model.ImageMessage;
import com.wechat.model.Music;
import com.wechat.model.MusicMessage;
import com.wechat.model.News;
import com.wechat.model.NewsMessage;
import com.wechat.model.TextMessage;

public class MessageUtil {
	//定义消息类型
	public static final String MSGTYPE_TEXT = "text";
	public static final String MSGTYPE_NEWS = "news";
	public static final String MSGTYPE_IMAGE = "image";
	public static final String MSGTYPE_VOICE = "voice";
	public static final String MSGTYPE_MUSIC = "music";
	public static final String MSGTYPE_LOCATION = "location";
	public static final String MSGTYPE_LINK = "link";
	public static final String MSGTYPE_EVENT = "event";
	public static final String EVENT_SUBSCRIBE = "subscribe";
	public static final String EVENT_SCAN = "SCAN";
	public static final String EVENT_LOCATION = "location_select";
	public static final String EVENT_CLICK = "CLICK";
	public static final String EVENT_VIEW = "VIEW";
	public static final String EVENT_SCANCODE_PUSH = "scancode_push";
	
	/**
	 * 1.xml转成 Map集合
	 * @param req
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request)throws Exception{
		Map<String, String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();
		InputStream ins =request.getInputStream();
		Document document = reader.read(ins);
		Element root =document.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		for (Element element : list) {
			map.put(element.getName(),element.getText());
		}
		ins.close();
		return map;
	}
	/**
	 * 2.将文本消息对象转换成　xml
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream stream=new XStream();
		stream.alias("xml", textMessage.getClass());
		return stream.toXML(textMessage);
		
	}
	/**
	 * 3.主菜单
	 */
	public static String menuText(){
		StringBuffer sb=new StringBuffer();
		sb.append("欢迎您关注黄志伟同学,请按照菜单提示进行操作：\n\n");
		sb.append("1.公众号介绍\n");
		sb.append("2.开发者介绍\n\n");
		sb.append("回复\"？\"调出此菜单。");
		return sb.toString();
	}
	/**
	 * 4.组合文本消息
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName,String fromUserName,String content){
		TextMessage textMessage=new TextMessage();
		textMessage.setFromUserName(toUserName);
		textMessage.setToUserName(fromUserName);
		textMessage.setContent(content);
		textMessage.setMsgType(MessageUtil.MSGTYPE_TEXT);
		textMessage.setCreateTime(String.valueOf(new Date().getTime()));
		return textMessageToXml(textMessage);
	}
	/**
	 * 5.第一个子菜单：1--公众号介绍
	 */
	public static String firstSubMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("此公众号为测试公众号");
		return sb.toString();
	}
	
	/**
	 * 6.第二个子菜单：2--开发者介绍
	 */
	public static String secondSubMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("此开发人员叫黄志伟!");
		return sb.toString();
	}
	
	public static String threeMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("词组翻译使用指南\n\n");
		sb.append("使用示例：\n");
		sb.append("翻译足球\n");
		sb.append("翻译中国足球\n");
		sb.append("翻译football\n\n");
		sb.append("回复？显示主菜单。");
		return sb.toString();
	}
	
	/**
	 * 图文消息转化为XML
	 * @param newsMessage
	 * @return
	 */
	public static String newMessageToXml(NewsMessage newsMessage){
		XStream xStream = new XStream();
		xStream.alias("xml", newsMessage.getClass());
		xStream.alias("item", new News().getClass());
		return xStream.toXML(newsMessage);
	}
	/**
	 * 图文消息的组装
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,String fromUserName){
		String message = null; 
		List<News> newslist = new ArrayList<>();
		NewsMessage newsMessage = new NewsMessage();
		
		News news = new News();
		news.setTitle("图文消息");
		news.setDescription("慕课网是垂直的互联网IT技能免费学习网站。以独家视频教程、在线编程工具、学习计划、问答社区为核心特色。在这里，你可以找到最好的互联网技术牛人，也可以通过免费的在线公开视频课程学习国内领先的互联网IT技术。慕课网课程涵盖前端开发、PHP、Html5、Android、iOS、Swift等IT前沿技术语言，包括基础课程、实用案例、高级分享三大类型，适合不同阶段的学习人群。");
		news.setPicUrl("http://ahui.ngrok.cc/Wechat/image/imooc.jpg");
		news.setUrl("www.baidu.com");
		newslist.add(news);
		
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(String.valueOf(new Date().getTime()));
		newsMessage.setMsgType(MSGTYPE_NEWS);
		newsMessage.setArticles(newslist);
		newsMessage.setArticleCount(newslist.size());
		
		message = newMessageToXml(newsMessage);
		return message;
	}
	/**
	 * 组装图片消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		String message = null;
		Image image = new Image();
		image.setMediaId("JTH8vBl0zDRlrrn2bBnMleySuHjVbMhyAo0U2x7kQyd1ciydhhsVPONbnRrKGp8m");
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MSGTYPE_IMAGE);
		imageMessage.setCreateTime(String.valueOf(new Date().getTime()));
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	}
	/**
	 * 图片消息转为xml
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	
	/**
	 * 音乐消息转为xml
	 * @param musicMessage
	 * @return
	 */
	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
	
	/**
	 * 组装音乐消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusicMessage(String toUserName,String fromUserName){
		String message = null;
		Music music = new Music();
		music.setThumbMediaId("KrbZIH1sm77B_yt2DG_sLXoFsay4KUpSGbLdkgQWbmq5_MaugL03tsdRYysEcV9G-DsZJzFZqZFeFk");
		music.setTitle("see you again");
		music.setDescription("速7片尾曲");
		music.setMusicUrl("http://zapper.tunnel.mobi/Weixin/resource/See You Again.mp3");
		music.setHQMusicUrl("http://zapper.tunnel.mobi/Weixin/resource/See You Again.mp3");
		
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(MSGTYPE_MUSIC);
		musicMessage.setCreateTime(String.valueOf(new Date().getTime()));
		musicMessage.setMusic(music);
		message = musicMessageToXml(musicMessage);
		return message;
	}
}
