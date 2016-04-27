package com.weixin.server.message.req;

/**
 * 
 * @ClassName: ImageMessage
 * @Description: TODO(图片消息) 
 * @author: zhanggd16818
 * @date 2016年3月3日下午6:42:42
 */
public class ReqImageMessage extends ReqBaseMessage{
	// 图片链接
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
}
