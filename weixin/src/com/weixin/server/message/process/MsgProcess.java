package com.weixin.server.message.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.server.message.entity.XmlEntity;

public class MsgProcess {

	private static Logger log = LoggerFactory.getLogger(MsgProcess.class);

	/**
	 * 
	 * @Title: wechatMsgProcess 
	 * @Description: TODO(解析处理xml、获取智能回复结果（通过图灵机器人api接口）)
	 * @param xml接收到的微信数据
	 * @return 最终的解析结果（xml格式数据）
	 * @author: zhanggd
	 * @date: 2016年3月2日下午5:58:28
	 */
	public String wechatMsgProcess(String xml){
		// 解析xml数据 
		XmlEntity xmlEntity = XmlProcess.getMsgEntity(xml);
		log.info("xml Content: "+xmlEntity.getContent());
		// 以文本消息为例，调用图灵机器人api接口，获取回复内容 
		String result = "";
		if("text".endsWith(xmlEntity.getMsgType())){
			result = TulingApiProcess.getTulingResult(xmlEntity.getContent());
		}else{
			result = "亲爱的，不明白你说的什么意思。";
		}
		
		// 此时，如果用户输入的是“你好”，在经过上面的过程之后，result为“你也好”类似的内容 
		// 因为最终回复给微信的也是xml格式的数据，所有需要将其封装为文本类型返回消息
		result = FormatXmlProcess.formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), result);
		
		return result;
	}
}
