package com.weixin.server.message.req;

/**
 * 
 * @ClassName: TestMessage
 * @Description: TODO(文本消息) 
 * @author: zhanggd
 * @date 2016年3月3日下午6:40:26
 */
public class ReqTextMessage extends ReqBaseMessage{
	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
