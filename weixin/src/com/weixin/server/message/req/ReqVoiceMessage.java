package com.weixin.server.message.req;

/**
 * 
 * @ClassName: VoiceMessage
 * @Description: TODO(音频消息) 
 * @author: zhanggd
 * @date 2016年3月3日下午6:44:43
 */
public class ReqVoiceMessage {
	// 媒体ID
	private String MediaId;
	// 语音格式
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
