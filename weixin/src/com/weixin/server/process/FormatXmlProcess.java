/********************************************
 * �ļ�����: FormatXmlProcess.java
 * ϵͳ����: EGS����ϵͳV1.0.0
 * ģ������:
 * �����Ȩ: �������ӹɷ����޹�˾
 * ����˵��: 
 * ϵͳ�汾: V1.0.0
 * ������Ա: zhanggd16816
 * ����ʱ��: 2016��3��2������1:44:06
 * �����Ա:
 * ����ĵ�:
 * �޸ļ�¼: 
 * ����汾  �޸�����    �޸���Ա  �޸ĵ���      �޸�˵��   
 *
 *********************************************/
package com.weixin.server.process;

import java.util.Date;

public class FormatXmlProcess {

	/**
	 * ��װ������ķ�����Ϣ
	 * @param to
	 * @param from
	 * @param content
	 * @return
	 */
	public static String formatXmlAnswer(String to, String from, String content) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(to);
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(from);
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
		sb.append(content);
		sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
		return sb.toString();
	}
}
