package com.weixin.server.message.resp;

import com.weixin.server.util.MessageUtil;

/**
 * 
 * @ClassName: MusicMessage
 * @Description: TODO(音乐消息) 
 * @author: zhanggd
 * @date 2016年3月3日下午6:50:12
 */
public class RespMusicMessage extends BaseMessage{
	// 音乐
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
