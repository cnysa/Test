package com.weixin.server.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.weixin.server.message.resp.Article;
import com.weixin.server.message.resp.RespMusicMessage;
import com.weixin.server.message.resp.RespNewsMessage;
import com.weixin.server.message.resp.RespTextMessage;

/**
 * 
 * @ClassName: MessageUtil
 * @Description: TODO(��Ϣ������) 
 * @author: zhanggd
 * @date 2016��3��3������6:55:49
 */
public class MessageUtil {
	private static Logger log = LoggerFactory.getLogger(MessageUtil.class);
	/**
	 * ������Ϣ���ͣ��ı�
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * ������Ϣ���ͣ�����
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * ������Ϣ���ͣ�ͼ��
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * ������Ϣ���ͣ��ı�
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * ������Ϣ���ͣ�ͼƬ
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	/**
	 * ������Ϣ���ͣ���Ƶ
	 */
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	/**
	 * ������Ϣ���ͣ�С��Ƶ
	 */
	public static final String REQ_MESSAGE_TYPE_SHORT_VIDEO = "shortvideo";

	/**
	 * ������Ϣ���ͣ�����
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * ������Ϣ���ͣ�����λ��
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * ������Ϣ���ͣ���Ƶ
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * ������Ϣ���ͣ�����
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * �¼����ͣ�subscribe(����)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * �¼����ͣ�unsubscribe(ȡ������)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * �¼����ͣ�CLICK(�Զ���˵�����¼�)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * ����΢�ŷ���������XML��
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		log.debug("����parseXml()");
		// ����������洢��HashMap��
		Map<String, String> map = new HashMap<String, String>();

		// ��request��ȡ��������
		InputStream inputStream = request.getInputStream();
		// ��ȡ������
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// �õ�xml��Ԫ��
		Element root = document.getRootElement();
		// �õ���Ԫ�ص������ӽڵ�
		List<Element> elementList = root.elements();

		// ���������ӽڵ�
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// �ͷ���Դ
		inputStream.close();
		inputStream = null;

		log.debug("�뿪parseXml()");
		return map;
	}

	/**
	 * �ı���Ϣ����ת����xml
	 * @param textMessage �ı���Ϣ����
	 * @return xml
	 */
	public static String textMessageToXml(RespTextMessage textMessage) {
		log.debug("����textMessageToXml()");
		xstream.alias("xml", textMessage.getClass());
		log.debug("�뿪textMessageToXml()");
		return xstream.toXML(textMessage);
	}

	/**
	 * ������Ϣ����ת����xml
	 * @param musicMessage ������Ϣ����
	 * @return xml
	 */
	public static String musicMessageToXml(RespMusicMessage musicMessage) {
		log.debug("����musicMessageToXml()");
		xstream.alias("xml", musicMessage.getClass());
		log.debug("�뿪musicMessageToXml()");
		return xstream.toXML(musicMessage);
	}

	/**
	 * ͼ����Ϣ����ת����xml
	 * @param newsMessage ͼ����Ϣ����
	 * @return xml
	 */
	public static String newsMessageToXml(RespNewsMessage newsMessage) {
		log.debug("����newsMessageToXml()");
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		log.debug("�뿪newsMessageToXml()");
		return xstream.toXML(newsMessage);
	}

	/**
	 * ��չxstream��ʹ��֧��CDATA��
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			log.debug("����createWriter()");
			log.debug("�뿪createWriter()");
			return new PrettyPrintWriter(out) {
				// ������xml�ڵ��ת��������CDATA���
				boolean cdata = true;

				public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
					if(name.equals("CreateTime") || name.equals("ArticleCount")){
						cdata = false;
					}else{
						cdata = true;
					}
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
}
