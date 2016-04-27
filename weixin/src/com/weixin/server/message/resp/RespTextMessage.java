package com.weixin.server.message.resp;

import com.weixin.server.util.MessageUtil;

/**
 * 
 * @ClassName: TestMessage
 * @Description: TODO(文本消息) 
 * @author: zhanggd
 * @date 2016年3月3日下午6:40:26
 */
public class RespTextMessage extends BaseMessage{
	// 消息内容
	private String Content;

	public RespTextMessage(String fromUserName,String toUserName){
		super(fromUserName,toUserName,MessageUtil.RESP_MESSAGE_TYPE_TEXT);
	}
	
	public RespTextMessage(){
		
	}
	
	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
