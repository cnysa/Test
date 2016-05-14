package com.weixin.server.message.process;

import java.lang.reflect.Field;  
import java.lang.reflect.Method;  
import java.util.Iterator;  

import org.dom4j.Document;  
import org.dom4j.DocumentHelper;  
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.server.message.entity.XmlEntity;

/**
 * 
 * @ClassName: XmlProcess
 * @Description: TODO(xml�ļ�����) 
 * @author: zhanggd
 * @date 2016��3��2������6:10:36
 */
public class XmlProcess {

	private static Logger log = LoggerFactory.getLogger(XmlProcess.class);

	/**
	 * 
	 * @Title: getMsgEntity 
	 * @Description: TODO(����΢��xml��Ϣ)
	 * @param strXml
	 * @return
	 * @author: zhanggd16816
	 * @date: 2016��3��2������6:10:52
	 */
	public static XmlEntity getMsgEntity(String strXml){
		XmlEntity msg = null;
		try {
			if (strXml.length() <= 0 || strXml == null)
				return null;
			 
			// ���ַ���ת��ΪXML�ĵ�����
			Document document = DocumentHelper.parseText(strXml);
			// ����ĵ��ĸ��ڵ�
			Element root = document.getRootElement();
			// �������ڵ��������ӽڵ�
			Iterator<?> iter = root.elementIterator();
			
			// �������н��
			msg = new XmlEntity();
			//���÷�����ƣ�����set����
			//��ȡ��ʵ���Ԫ����
			Class<?> c = Class.forName("com.weixin.server.entity.XmlEntity");
			msg = (XmlEntity)c.newInstance();//�������ʵ��Ķ���
			
			while(iter.hasNext()){
				Element ele = (Element)iter.next();
				//��ȡset�����еĲ����ֶΣ�ʵ��������ԣ�
				Field field = c.getDeclaredField(ele.getName());
				//��ȡset������field.getType())��ȡ���Ĳ�����������
				Method method = c.getDeclaredMethod("set"+ele.getName(), field.getType());
				//����set����
				method.invoke(msg, ele.getText());
			}
		} catch (Exception e) {
			log.info("xml ��ʽ�쳣��"+strXml);
			e.printStackTrace();
		}
		return msg;
	}
}
