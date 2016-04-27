package com.weixin.server.message.resp;

import java.util.Date;

/**
 * 
 * @ClassName: BaseMessage
 * @Description: TODO(��Ϣ����(�����ʺ� -> ��ͨ�û�)) 
 * @author: zhanggd
 * @date 2016��3��3������6:47:02
 */
public class BaseMessage {
	// ���շ��ʺţ��յ���OpenID��
	private String ToUserName;
	// ������΢�ź�
	private String FromUserName;
	// ��Ϣ����ʱ�� �����ͣ�
	private long CreateTime;
	// ��Ϣ���ͣ�text/music/news��
	private String MsgType;
	
	// λ0x0001����־ʱ���Ǳ���յ�����Ϣ  
//    private int FuncFlag;	
	
	public BaseMessage(){
		
	}
	
	public BaseMessage(String fromUserName,String toUserName,String MsgType){
		this.FromUserName = fromUserName;
		this.ToUserName = toUserName;
		this.MsgType = MsgType;
//		this.FuncFlag = 0;
		this.CreateTime = new Date().getTime();
	}
	
//	public int getFuncFlag() {  
//        return FuncFlag;  
//    }  
//  
//    public void setFuncFlag(int funcFlag) {  
//        FuncFlag = funcFlag;  
//    } 

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}
