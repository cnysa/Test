package com.weixin.server.message.req;

/**
 * 
 * @ClassName: VideoMessage
 * @Description: TODO(��Ƶ��Ϣ) 
 * @author: zhanggd
 * @date 2016��3��3������8:09:50
 */
public class ReqVideoMessage extends ReqBaseMessage{
	//��Ƶ��Ϣý��id�����Ե��ö�ý���ļ����ؽӿ���ȡ���ݡ�
	private String MediaId;
	//��Ƶ��Ϣ����ͼ��ý��id�����Ե��ö�ý���ļ����ؽӿ���ȡ���ݡ�
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
