package com.weixin.server.message.req;

/**
 * 
 * @ClassName: LinkMessage
 * @Description: TODO(������Ϣ ) 
 * @author: zhanggd
 * @date 2016��3��3������6:43:19
 */
public class ReqLinkMessage extends ReqBaseMessage{
	// ��Ϣ����
	private String Title;
	// ��Ϣ����
	private String Description;
	// ��Ϣ����
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
