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
 * @Description: TODO(xml文件处理) 
 * @author: zhanggd
 * @date 2016年3月2日下午6:10:36
 */
public class XmlProcess {

	private static Logger log = LoggerFactory.getLogger(XmlProcess.class);

	/**
	 * 
	 * @Title: getMsgEntity 
	 * @Description: TODO(解析微信xml消息)
	 * @param strXml
	 * @return
	 * @author: zhanggd16816
	 * @date: 2016年3月2日下午6:10:52
	 */
	public static XmlEntity getMsgEntity(String strXml){
		XmlEntity msg = null;
		try {
			if (strXml.length() <= 0 || strXml == null)
				return null;
			 
			// 将字符串转化为XML文档对象
			Document document = DocumentHelper.parseText(strXml);
			// 获得文档的根节点
			Element root = document.getRootElement();
			// 遍历根节点下所有子节点
			Iterator<?> iter = root.elementIterator();
			
			// 遍历所有结点
			msg = new XmlEntity();
			//利用反射机制，调用set方法
			//获取该实体的元类型
			Class<?> c = Class.forName("com.weixin.server.entity.XmlEntity");
			msg = (XmlEntity)c.newInstance();//创建这个实体的对象
			
			while(iter.hasNext()){
				Element ele = (Element)iter.next();
				//获取set方法中的参数字段（实体类的属性）
				Field field = c.getDeclaredField(ele.getName());
				//获取set方法，field.getType())获取它的参数数据类型
				Method method = c.getDeclaredMethod("set"+ele.getName(), field.getType());
				//调用set方法
				method.invoke(msg, ele.getText());
			}
		} catch (Exception e) {
			log.info("xml 格式异常："+strXml);
			e.printStackTrace();
		}
		return msg;
	}
}
