/**
 * @Project Name:weixin
 * @File Name:Listens.java
 * @Package Name:com.weixin.server.servlets
 * @author zhanggd
 * @Date:2016��5��15������4:45:45
 */

package com.weixin.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weixin.gacl.manager.interfaces.UserManager;
import com.weixin.server.message.process.TulingApiProcess;
import com.weixin.server.message.resp.RespTextMessage;
import com.weixin.server.util.EnumManager;
import com.weixin.server.util.MessageUtil;
import com.weixin.server.util.SignUtil;

/**
 * @ClassName: Listens
 * @Description: TODO(����)
 * @author zhanggd
 * @date 2016��5��15�� ����4:45:45
 */
@Controller
public class Listens {
	
	private static Logger log = LoggerFactory.getLogger(Listens.class);
	@Autowired
    private UserManager userManagerImpl;//ע��dao

	@RequestMapping(value="/listen")
	public void listens(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		if ("GET".equalsIgnoreCase(request.getMethod())){
			log.info("==GET����==");
			// ΢�ż���ǩ��
			String signature = request.getParameter("signature");
			// ʱ���
			String timestamp = request.getParameter("timestamp");
			// �����
			String nonce = request.getParameter("nonce");
			// ����ַ���
			String echostr = request.getParameter("echostr");

			PrintWriter out = response.getWriter();
			// ͨ������signature���������У�飬��У��ɹ���ԭ������echostr����ʾ����ɹ����������ʧ��
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				out.print(echostr);
			}
			out.close();
			out = null;
			
			}else{
				log.info("==POST����==");
				// ��������Ӧ�ı��������ΪUTF-8����ֹ�������룩
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");

				// ���ú���ҵ���������Ϣ��������Ϣ
				String respMessage = processRequest(request);
				
				// ��Ӧ��Ϣ
				PrintWriter out = response.getWriter();
				out.print(respMessage);
				out.close();
			}
	}
	
	public String processRequest(HttpServletRequest request) {
		String respMessage = null;
		log.info("=====������Ϣ=====");
		try {
			// Ĭ�Ϸ��ص��ı���Ϣ����
			String respContent = "�������쳣�����Ժ��ԣ�";
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			String msgType = setWxid(requestMap);
			RespTextMessage textMessage = new RespTextMessage(requestMap.get("ToUserName"), requestMap.get("FromUserName"));
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
	
	public String setWxid(Map<String, String> requestMap){
		
		String fromUserName = requestMap.get("FromUserName");
//		String toUserName = requestMap.get("ToUserName");
		String msgType = requestMap.get("MsgType");
		if(!userManagerImpl.isUser(fromUserName)){
			log.info("���û�:{}",fromUserName);
			userManagerImpl.insertUser(fromUserName);
		}else{
			log.info("���û�:{}",fromUserName);
		}
		return msgType;
	}
}
