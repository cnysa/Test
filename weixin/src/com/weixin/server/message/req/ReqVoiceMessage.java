package com.weixin.server.message.req;

/**
 * 
 * @ClassName: VoiceMessage
 * @Description: TODO(��Ƶ��Ϣ) 
 * @author: zhanggd
 * @date 2016��3��3������6:44:43
 */
public class ReqVoiceMessage {
	// ý��ID
	private String MediaId;
	// ������ʽ
	private String Format;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}
}
