/**
 * @Project Name:weixin
 * @File Name:Listens.java
 * @Package Name:com.weixin.server.servlets
 * @author zhanggd
 * @Date:2016年5月15日下午4:45:45
 */

package com.weixin.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weixin.gacl.manager.interfaces.TelManager;
import com.weixin.gacl.manager.interfaces.UserManager;
import com.weixin.gacl.mapping.beans.Tel;
import com.weixin.server.message.process.TulingApiProcess;
import com.weixin.server.message.resp.Article;
import com.weixin.server.message.resp.RespNewsMessage;
import com.weixin.server.message.resp.RespTextMessage;
import com.weixin.server.servlets.threads.TokenThread;
import com.weixin.server.util.EnumManager;
import com.weixin.server.util.MessageUtil;
import com.weixin.server.util.SignUtil;

/**
 * @ClassName: Listens
 * @Description: TODO(描述)
 * @author zhanggd
 * @date 2016年5月15日 下午4:45:45
 */
@Controller
public class Listens {
	
	private static Logger log = LoggerFactory.getLogger(Listens.class);
	@Autowired
    private UserManager userManagerImpl;//注入dao
	@Autowired
	private TelManager telManagerImpl;

	@RequestMapping(value="/listen")
	public void listens(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		log.debug("进入listens()");
		if ("GET".equalsIgnoreCase(request.getMethod())){
			log.info("执行GET请求");
			String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String echostr = request.getParameter("echostr");

			PrintWriter out = response.getWriter();
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				out.print(echostr);
			}
			out.close();
			out = null;
			
			}else{
				log.info("执行POST请求");
				// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");

				// 调用核心业务类接收消息、处理消息
				String respMessage = processRequest(request);
				
				// 响应消息
				PrintWriter out = response.getWriter();
				out.print(respMessage);
				out.close();
			}
		log.debug("离开listens()");
	}
	
	public String processRequest(HttpServletRequest request) {
		log.debug("进入processRequest()");
		String respMessage = null;
		boolean isNewTextClick = false;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			String msgType = setWxid(requestMap);
			RespTextMessage textMessage = new RespTextMessage(requestMap.get("ToUserName"), requestMap.get("FromUserName"));
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				log.info("消息内容:文本[{}]",requestMap.get("Content"));
				String tel = null;
				String reques = requestMap.get("Content");
				if(reques.charAt(0) == '#'){
					tel = reques.substring(1);
					tel = queryTel(tel);
				}
				if(tel == null){
					respContent = TulingApiProcess.getTulingResult(requestMap.get("Content"));
				}else{
					respContent = tel;
				}
			}
			// 图片消息 地理位置消息 链接消息 音频消息 小视频
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)
					|| msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)
					|| msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)
					|| msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)
					|| msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)
					|| msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORT_VIDEO)
					) {
				respContent = "暂不支持该类型消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "非常感谢您对本平台的支持与关注，希望您能在本平台找到您心仪的功能。本平台服务于齐鲁工业大学信息学院全体师生，拥有较强的人员号召力度并且以众多先进技术作为支撑。如果您对本平台的发展有一定的建议，如果您对平台的页面设置以及部分功能有更好的理解，如果您想将您的大作在平台即时发布，如果您想来本平台一展您才华，请您：稿件请编辑邮件发送至315316371@qq.com（来信请注明您的姓名和基本联系方式。）";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					String eventKey = requestMap.get("EventKey");
					if(eventKey.equals(EnumManager.MENU_XYJJ.getRespCode())){
						respContent = createTw(requestMap.get("ToUserName"), requestMap.get("FromUserName"));
						isNewTextClick = true;
					}else{
						respContent = EnumManager.getEnumByCode(eventKey).getRespMsg()+"菜单项被点击！";
					}
				}
			}
			if(isNewTextClick){
				respMessage = respContent;
			}else{
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
		} catch (Exception e) {
			log.error("{}", e);
		}
		log.debug("离开listens():{}",respMessage);
		return respMessage;
	}
	
	/**
	 * 
	 * createTw
	 * @Title: createTw
	 * @Description: TODO(单图文消息)
	 * @param fromUserName
	 * @param toUserName: void
	 */
	public String createTw(String fromUserName,String toUserName){
		log.debug("进入createTw(fromUserName={}, toUserName={})",fromUserName,toUserName);
        // 创建图文消息  
        RespNewsMessage newsMessage = new RespNewsMessage();  
        newsMessage.setToUserName(toUserName);  
        newsMessage.setFromUserName(fromUserName);  
        newsMessage.setCreateTime(new Date().getTime());  
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();  
        article.setTitle("齐鲁工业大学信息学院简介");  
        article.setDescription("信息学院前身计算机科学与技术系成立于1998年8月（1992年在原机电工程系建立了计算机专业），2003年7月，更名信息科学与技术学院，2011年4月又更名为信息学院。信息学院现有计算机应用技术、电子商务与物流信息工程两个二级学科硕士点，");  
        article.setPicUrl(TokenThread.weburl+"/weixin/images/xxxy.jpg");  
        article.setUrl("http://baike.baidu.com/link?url=jVO6aOjpuxJ_WXLgSXPmqFOIomfhzkGHGJa82dgqAovxYoEuNBkEQ6uGYs1OzBdsiRftl8LGQ5apVrVCTmBD0aNbmgFHId82PH6vnroDLFXGLvonRNfie3_jjXaIvzho7xxKToIqM257hgvi7xjG7rnq7CHShePJpxQ67kFVPpEVUNx_uQFu1n7Io58deNOaO5e5SiaQb8jB7KGhrUK8Ga");  
        articleList.add(article);  
        // 设置图文消息个数  
        newsMessage.setArticleCount(articleList.size());  
        // 设置图文消息包含的图文集合  
        newsMessage.setArticles(articleList);  
        // 将图文消息对象转换成xml字符串  
        log.debug("离开createTw()");
        return MessageUtil.newsMessageToXml(newsMessage); 
	}
	
	public String setWxid(Map<String, String> requestMap){
		log.debug("进入setWxid()");
		String fromUserName = requestMap.get("FromUserName");
		String msgType = requestMap.get("MsgType");
		if(!userManagerImpl.isUser(fromUserName)){
			log.info("新用户 {}",fromUserName);
			userManagerImpl.addUser(fromUserName);
		}else{
			log.info("老用户 {}",fromUserName);
		}
		log.debug("离开setWxid():{}",msgType);
		return msgType;
	}
	
	public String queryTel(String name){
		log.debug("进入queryTel(name={})",name);
		String result = null;
		StringBuilder re = new StringBuilder();
		Tel[] tels = telManagerImpl.queryMh(name);
		if(tels != null){
			for(int i=0; i<tels.length; i++){
				re.append(tels[i].getTelName());
				re.append(" : ");
				re.append("\n");
				re.append(tels[i].getTelNum1());
				if(tels[i].getTelNum2()!=null){
					re.append("\n");
					re.append(tels[i].getTelNum2());
				}
				re.append("\n");
			}
			result = re.toString();
		}else{
			result = "很抱歉，没有相关结果！如果您得到该联系方式，请直接回复联系人与联系方式，非常感谢！";
		}
		log.debug("离开queryTel():{}",result);
		return result;
	}
}
