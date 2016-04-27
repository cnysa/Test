package com.weixin.server.message.resp;

import com.weixin.server.util.MessageUtil;

/**
 * 
 * @ClassName: MusicMessage
 * @Description: TODO(������Ϣ) 
 * @author: zhanggd
 * @date 2016��3��3������6:50:12
 */
public class RespMusicMessage extends BaseMessage{
	// ����
	private Music Music;
	
	public RespMusicMessage(){
		
	}
	
	public RespMusicMessage(String fromUserName,String toUserName){
		super(fromUserName,toUserName,MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
	}

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
}
