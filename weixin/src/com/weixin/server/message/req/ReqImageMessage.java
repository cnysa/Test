package com.weixin.server.message.req;

/**
 * 
 * @ClassName: ImageMessage
 * @Description: TODO(ͼƬ��Ϣ) 
 * @author: zhanggd16818
 * @date 2016��3��3������6:42:42
 */
public class ReqImageMessage extends ReqBaseMessage{
	// ͼƬ����
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
}
