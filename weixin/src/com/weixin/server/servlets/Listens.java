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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weixin.gacl.manager.interfaces.TelManager;
import com.weixin.gacl.manager.interfaces.UserManager;
import com.weixin.gacl.mapping.beans.Tel;
import com.weixin.server.message.process.TulingApiProcess;
import com.weixin.server.message.resp.Article;
import com.weixin.server.message.resp.RespNewsMessage;
import com.weixin.server.message.resp.RespTextMessage;
import com.weixin.server.servlets.threads.TokenThread;
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
	@Autowired
	private TelManager telManagerImpl;

	@RequestMapping(value="/listen")
	public void listens(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		log.debug("����listens()");
		if ("GET".equalsIgnoreCase(request.getMethod())){
			log.info("ִ��GET����");
			String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String echostr = request.getParameter("echostr");

			PrintWriter out = response.getWriter();
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				out.print(echostr);
			}
			out.close();
			out = null;
			
			}else{
				log.info("ִ��POST����");
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
		log.debug("�뿪listens()");
	}
	
	public String processRequest(HttpServletRequest request) {
		log.debug("����processRequest()");
		String respMessage = null;
		boolean isNewTextClick = false;
		try {
			// Ĭ�Ϸ��ص��ı���Ϣ����
			String respContent = "�������쳣�����Ժ��ԣ�";
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			String msgType = setWxid(requestMap);
			RespTextMessage textMessage = new RespTextMessage(requestMap.get("ToUserName"), requestMap.get("FromUserName"));
			// �ı���Ϣ
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				log.info("��Ϣ����:�ı�[{}]",requestMap.get("Content"));
				String tel = null;
				String reques = requestMap.get("Content");
				if(reques.charAt(0) == '#'){
					tel = reques.substring(1);
					tel = queryTel(tel);
				}
				if(tel == null){
					respContent = TulingApiProcess.getTulingResult(requestMap.get("Content"));
				}else{
					respContent = tel;
				}
			}
			// ͼƬ��Ϣ ����λ����Ϣ ������Ϣ ��Ƶ��Ϣ С��Ƶ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)
					|| msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)
					|| msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)
					|| msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)
					|| msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)
					|| msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORT_VIDEO)
					) {
				respContent = "�ݲ�֧�ָ�������Ϣ��";
			}
			// �¼�����
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// �¼�����
				String eventType = requestMap.get("Event");
				// ����
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "�ǳ���л���Ա�ƽ̨��֧�����ע��ϣ�������ڱ�ƽ̨�ҵ������ǵĹ��ܡ���ƽ̨��������³��ҵ��ѧ��ϢѧԺȫ��ʦ����ӵ�н�ǿ����Ա�������Ȳ������ڶ��Ƚ�������Ϊ֧�š�������Ա�ƽ̨�ķ�չ��һ���Ľ��飬�������ƽ̨��ҳ�������Լ����ֹ����и��õ���⣬������뽫���Ĵ�����ƽ̨��ʱ�����������������ƽ̨һչ���Ż��������������༭�ʼ�������315316371@qq.com��������ע�����������ͻ�����ϵ��ʽ����";
				}
				// ȡ������
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO ȡ�����ĺ��û����ղ������ںŷ��͵���Ϣ����˲���Ҫ�ظ���Ϣ
				}
				// �Զ���˵�����¼�
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					String eventKey = requestMap.get("EventKey");
					if(eventKey.equals(EnumManager.MENU_XYJJ.getRespCode())){
						respContent = createTw(requestMap.get("ToUserName"), requestMap.get("FromUserName"));
						isNewTextClick = true;
					}else{
						respContent = EnumManager.getEnumByCode(eventKey).getRespMsg()+"�˵�������";
					}
				}
			}
			if(isNewTextClick){
				respMessage = respContent;
			}else{
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
		} catch (Exception e) {
			log.error("{}", e);
		}
		log.debug("�뿪listens():{}",respMessage);
		return respMessage;
	}
	
	/**
	 * 
	 * createTw
	 * @Title: createTw
	 * @Description: TODO(��ͼ����Ϣ)
	 * @param fromUserName
	 * @param toUserName: void
	 */
	public String createTw(String fromUserName,String toUserName){
		log.debug("����createTw(fromUserName={}, toUserName={})",fromUserName,toUserName);
        // ����ͼ����Ϣ  
        RespNewsMessage newsMessage = new RespNewsMessage();  
        newsMessage.setToUserName(toUserName);  
        newsMessage.setFromUserName(fromUserName);  
        newsMessage.setCreateTime(new Date().getTime());  
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();  
        article.setTitle("��³��ҵ��ѧ��ϢѧԺ���");  
        article.setDescription("��ϢѧԺǰ��������ѧ�뼼��ϵ������1998��8�£�1992����ԭ���繤��ϵ�����˼����רҵ����2003��7�£�������Ϣ��ѧ�뼼��ѧԺ��2011��4���ָ���Ϊ��ϢѧԺ����ϢѧԺ���м����Ӧ�ü���������������������Ϣ������������ѧ��˶ʿ�㣬");  
        article.setPicUrl(TokenThread.weburl+"/weixin/images/xxxy.jpg");  
        article.setUrl("http://baike.baidu.com/link?url=jVO6aOjpuxJ_WXLgSXPmqFOIomfhzkGHGJa82dgqAovxYoEuNBkEQ6uGYs1OzBdsiRftl8LGQ5apVrVCTmBD0aNbmgFHId82PH6vnroDLFXGLvonRNfie3_jjXaIvzho7xxKToIqM257hgvi7xjG7rnq7CHShePJpxQ67kFVPpEVUNx_uQFu1n7Io58deNOaO5e5SiaQb8jB7KGhrUK8Ga");  
        articleList.add(article);  
        // ����ͼ����Ϣ����  
        newsMessage.setArticleCount(articleList.size());  
        // ����ͼ����Ϣ������ͼ�ļ���  
        newsMessage.setArticles(articleList);  
        // ��ͼ����Ϣ����ת����xml�ַ���  
        log.debug("�뿪createTw()");
        return MessageUtil.newsMessageToXml(newsMessage); 
	}
	
	public String setWxid(Map<String, String> requestMap){
		log.debug("����setWxid()");
		String fromUserName = requestMap.get("FromUserName");
		String msgType = requestMap.get("MsgType");
		if(!userManagerImpl.isUser(fromUserName)){
			log.info("���û� {}",fromUserName);
			userManagerImpl.addUser(fromUserName);
		}else{
			log.info("���û� {}",fromUserName);
		}
		log.debug("�뿪setWxid():{}",msgType);
		return msgType;
	}
	
	public String queryTel(String name){
		log.debug("����queryTel(name={})",name);
		String result = null;
		StringBuilder re = new StringBuilder();
		Tel[] tels = telManagerImpl.queryMh(name);
		if(tels != null){
			for(int i=0; i<tels.length; i++){
				re.append(tels[i].getTelName());
				re.append(" : ");
				re.append("\n");
				re.append(tels[i].getTelNum1());
				if(tels[i].getTelNum2()!=null){
					re.append("\n");
					re.append(tels[i].getTelNum2());
				}
				re.append("\n");
			}
			result = re.toString();
		}else{
			result = "�ܱ�Ǹ��û����ؽ����������õ�����ϵ��ʽ����ֱ�ӻظ���ϵ������ϵ��ʽ���ǳ���л��";
		}
		log.debug("�뿪queryTel():{}",result);
		return result;
	}
}
