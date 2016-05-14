package com.weixin.server.servlets.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.server.message.process.TulingApiProcess;
import com.weixin.server.message.resp.RespTextMessage;
import com.weixin.server.util.EnumManager;
import com.weixin.server.util.MessageUtil;

/**
 * 
 * @ClassName: CoreService
 * @Description: TODO(核心服务类) 
 * @author: zhanggd
 * @date 2016年3月3日下午7:16:46
 */
public class CoreService {
	
	private static Logger log = LoggerFactory.getLogger(CoreService.class);

	/**
	 * 
	 * @Title: processRequest 
	 * @Description: TODO(处理请求)
	 * @param request
	 * @return
	 * @author: zhanggd
	 * @date: 2016年3月3日下午7:20:11
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			log.info("发送方帐号:{}",fromUserName);
			log.info("公众帐号:{}",toUserName);
			log.info("消息类型:{}",msgType);
			RespTextMessage textMessage = new RespTextMessage(toUserName, fromUserName);
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
}
