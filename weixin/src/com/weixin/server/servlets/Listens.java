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
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weixin.gacl.manager.interfaces.UserManager;
import com.weixin.server.message.process.TulingApiProcess;
import com.weixin.server.message.resp.RespTextMessage;
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

	@RequestMapping(value="/listen")
	public void listens(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		if ("GET".equalsIgnoreCase(request.getMethod())){
			log.info("==GET请求==");
			// 微信加密签名
			String signature = request.getParameter("signature");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");
			// 随机字符串
			String echostr = request.getParameter("echostr");

			PrintWriter out = response.getWriter();
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				out.print(echostr);
			}
			out.close();
			out = null;
			
			}else{
				log.info("==POST请求==");
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
	}
	
	public String processRequest(HttpServletRequest request) {
		String respMessage = null;
		log.info("=====接受消息=====");
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			String msgType = setWxid(requestMap);
			RespTextMessage textMessage = new RespTextMessage(requestMap.get("ToUserName"), requestMap.get("FromUserName"));
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				log.info("消息内容:文本[{}]",requestMap.get("Content"));
				respContent = TulingApiProcess.getTulingResult(requestMap.get("Content"));
				//				respContent = "您发送的是文本消息！";
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				log.info("消息内容:链接[{}]",requestMap.get("PicUrl"));
				respContent = "您发送的是图片消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				log.info("消息内容:经纬[{},{}],",
						requestMap.get("Location_X"),
						requestMap.get("Location_Y"));
				log.info("消息内容:缩放大小[{}],地理位置[{}]",
						requestMap.get("Scale"),
						requestMap.get("Label"));
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				log.info("消息内容:标题[{}]",requestMap.get("Title"));
				log.info("消息内容:描述[{}]",requestMap.get("Description"));
				log.info("消息内容:链接[{}]",requestMap.get("Url"));
				respContent = "您发送的是链接消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				log.info("消息内容:媒体ID[{}]",requestMap.get("MediaId"));
				log.info("消息内容:语音格式[{}]",requestMap.get("Format"));
				respContent = "您发送的是音频消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				log.info("消息内容:媒体ID[{}]",requestMap.get("MediaId"));
				log.info("消息内容:视频格式[{}]",requestMap.get("ThumbMediaId"));
				respContent = "您发送的是视频消息！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORT_VIDEO)) {
				log.info("消息内容:媒体ID[{}]",requestMap.get("MediaId"));
				log.info("消息内容:视频格式[{}]",requestMap.get("ThumbMediaId"));
				respContent = "您发送的是小视频消息！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "非常感谢您的关注！";
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					String eventKey = requestMap.get("EventKey");
					respContent = EnumManager.getEnumByCode(eventKey).getRespMsg()+"菜单项被点击！";
				}
			}
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
//			log.info(respMessage.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}
	
	public String setWxid(Map<String, String> requestMap){
		
		String fromUserName = requestMap.get("FromUserName");
//		String toUserName = requestMap.get("ToUserName");
		String msgType = requestMap.get("MsgType");
		if(!userManagerImpl.isUser(fromUserName)){
			log.info("新用户:{}",fromUserName);
			userManagerImpl.insertUser(fromUserName);
		}else{
			log.info("老用户:{}",fromUserName);
		}
		return msgType;
	}
}
