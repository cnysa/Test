package com.weixin.server.message.resp;

import com.weixin.server.util.MessageUtil;

/**
 * 
 * @ClassName: TestMessage
 * @Description: TODO(�ı���Ϣ) 
 * @author: zhanggd
 * @date 2016��3��3������6:40:26
 */
public class RespTextMessage extends BaseMessage{
	// ��Ϣ����
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
