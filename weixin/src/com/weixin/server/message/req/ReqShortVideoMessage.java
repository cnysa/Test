package com.weixin.server.message.req;

/**
 * 
 * @ClassName: ShortVideoMessage
 * @Description: TODO(小视频消息) 
 * @author: zhanggd
 * @date 2016年3月3日下午8:09:28
 */
public class ReqShortVideoMessage extends ReqBaseMessage{
	//视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
	private String MediaId;
	//视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
	private String ThumbMediaId;

	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
}
