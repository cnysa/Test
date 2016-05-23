package com.weixin.server.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.server.servlets.threads.TokenThread;

/**
 * 
 * @ClassName: InitServlet
 * @Description: TODO(���ڱ��沢����access_tokenֵ) 
 * @author: zhanggd
 * @date 2016��3��2������7:05:10
 */
public class InitServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(InitServlet.class);
	
    /**
     * 
     * @throws ServletException
     */
    public void init(){
    	log.debug("����init()");
    	log.info("���� {}","��ȡweb.xml�����õĲ���");
        TokenThread.appid = getInitParameter("appid");  
        TokenThread.appsecret = getInitParameter("appsecret");
        TokenThread.weburl = getInitParameter("weburl");
        TokenThread.token = getInitParameter("token");
        log.info("���� {} appid:{} ", "��ȡ����",TokenThread.appid);  
        log.info("���� {} appsecret:{} ", "��ȡ����",TokenThread.appsecret);
        log.info("���� {} weburl:{} ", "��ȡ����",TokenThread.weburl); 
        log.info("���� {} token:{} ", "��ȡ����",TokenThread.token); 
        log.info("���� {}","��ȡweb.xml�����õĲ������");
  
        if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {  
            log.error("appid and appsecret ���ó�������������");  
        } else {  
        	log.info("���� {}","����TokenThread�߳�  ");
            new Thread(new TokenThread()).start();  
        }
        log.debug("�뿪init()");
    }

}
