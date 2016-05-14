package com.weixin.server.servlets.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.server.message.process.TulingApiProcess;
import com.weixin.server.message.resp.RespTextMessage;
import com.weixin.server.util.EnumManager;
import com.weixin.server.util.MessageUtil;

/**
 * 
 * @ClassName: CoreService
 * @Description: TODO(���ķ�����) 
 * @author: zhanggd
 * @date 2016��3��3������7:16:46
 */
public class CoreService {
	
	private static Logger log = LoggerFactory.getLogger(CoreService.class);

	/**
	 * 
	 * @Title: processRequest 
	 * @Description: TODO(��������)
	 * @param request
	 * @return
	 * @author: zhanggd
	 * @date: 2016��3��3������7:20:11
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// Ĭ�Ϸ��ص��ı���Ϣ����
			String respContent = "�������쳣�����Ժ��ԣ�";

			// xml�������
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// ���ͷ��ʺţ�open_id��
			String fromUserName = requestMap.get("FromUserName");
			// �����ʺ�
			String toUserName = requestMap.get("ToUserName");
			// ��Ϣ����
			String msgType = requestMap.get("MsgType");

			log.info("���ͷ��ʺ�:{}",fromUserName);
			log.info("�����ʺ�:{}",toUserName);
			log.info("��Ϣ����:{}",msgType);
			RespTextMessage textMessage = new RespTextMessage(toUserName, fromUserName);
			// �ı���Ϣ
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				log.info("��Ϣ����:�ı�[{}]",requestMap.get("Content"));
				respContent = TulingApiProcess.getTulingResult(requestMap.get("Content"));
//				respContent = "�����͵����ı���Ϣ��";
			}
			// ͼƬ��Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				log.info("��Ϣ����:����[{}]",requestMap.get("PicUrl"));
				respContent = "�����͵���ͼƬ��Ϣ��";
			}
			// ����λ����Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				log.info("��Ϣ����:��γ[{},{}],",
						requestMap.get("Location_X"),
						requestMap.get("Location_Y"));
				log.info("��Ϣ����:���Ŵ�С[{}],����λ��[{}]",
						requestMap.get("Scale"),
						requestMap.get("Label"));
				respContent = "�����͵��ǵ���λ����Ϣ��";
			}
			// ������Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				log.info("��Ϣ����:����[{}]",requestMap.get("Title"));
				log.info("��Ϣ����:����[{}]",requestMap.get("Description"));
				log.info("��Ϣ����:����[{}]",requestMap.get("Url"));
				respContent = "�����͵���������Ϣ��";
			}
			// ��Ƶ��Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				log.info("��Ϣ����:ý��ID[{}]",requestMap.get("MediaId"));
				log.info("��Ϣ����:������ʽ[{}]",requestMap.get("Format"));
				respContent = "�����͵�����Ƶ��Ϣ��";
			}
			// ��Ƶ��Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				log.info("��Ϣ����:ý��ID[{}]",requestMap.get("MediaId"));
				log.info("��Ϣ����:��Ƶ��ʽ[{}]",requestMap.get("ThumbMediaId"));
				respContent = "�����͵�����Ƶ��Ϣ��";
			}
			// ��Ƶ��Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORT_VIDEO)) {
				log.info("��Ϣ����:ý��ID[{}]",requestMap.get("MediaId"));
				log.info("��Ϣ����:��Ƶ��ʽ[{}]",requestMap.get("ThumbMediaId"));
				respContent = "�����͵���С��Ƶ��Ϣ��";
			}
			// �¼�����
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// �¼�����
				String eventType = requestMap.get("Event");
				// ����
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "�ǳ���л���Ĺ�ע��";
				}
				// ȡ������
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO ȡ�����ĺ��û����ղ������ںŷ��͵���Ϣ����˲���Ҫ�ظ���Ϣ
				}
				// �Զ���˵�����¼�
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					String eventKey = requestMap.get("EventKey");
					respContent = EnumManager.getEnumByCode(eventKey).getRespMsg()+"�˵�������";
				}
			}
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
//			log.info(respMessage.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}	
}
