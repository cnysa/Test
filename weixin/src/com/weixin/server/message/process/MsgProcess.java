package com.weixin.server.message.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.server.message.entity.XmlEntity;

public class MsgProcess {

	private static Logger log = LoggerFactory.getLogger(MsgProcess.class);

	/**
	 * 
	 * @Title: wechatMsgProcess 
	 * @Description: TODO(��������xml����ȡ���ܻظ������ͨ��ͼ�������api�ӿڣ�)
	 * @param xml���յ���΢������
	 * @return ���յĽ��������xml��ʽ���ݣ�
	 * @author: zhanggd
	 * @date: 2016��3��2������5:58:28
	 */
	public String wechatMsgProcess(String xml){
		// ����xml���� 
		XmlEntity xmlEntity = XmlProcess.getMsgEntity(xml);
		log.info("xml Content: "+xmlEntity.getContent());
		// ���ı���ϢΪ��������ͼ�������api�ӿڣ���ȡ�ظ����� 
		String result = "";
		if("text".endsWith(xmlEntity.getMsgType())){
			result = TulingApiProcess.getTulingResult(xmlEntity.getContent());
		}else{
			result = "�װ��ģ���������˵��ʲô��˼��";
		}
		
		// ��ʱ������û�������ǡ���á����ھ�������Ĺ���֮��resultΪ����Ҳ�á����Ƶ����� 
		// ��Ϊ���ջظ���΢�ŵ�Ҳ��xml��ʽ�����ݣ�������Ҫ�����װΪ�ı����ͷ�����Ϣ
		result = FormatXmlProcess.formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), result);
		
		return result;
	}
}
