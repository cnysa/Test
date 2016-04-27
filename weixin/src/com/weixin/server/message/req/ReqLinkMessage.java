package com.weixin.server.message.req;

/**
 * 
 * @ClassName: LinkMessage
 * @Description: TODO(链接消息 ) 
 * @author: zhanggd
 * @date 2016年3月3日下午6:43:19
 */
public class ReqLinkMessage extends ReqBaseMessage{
	// 消息标题
	private String Title;
	// 消息描述
	private String Description;
	// 消息链接
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
}
