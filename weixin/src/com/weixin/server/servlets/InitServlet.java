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
    	// ��ȡweb.xml�����õĲ���  
        TokenThread.appid = getInitParameter("appid");  
        TokenThread.appsecret = getInitParameter("appsecret");  
  
        log.info("weixin api appid:{} ", TokenThread.appid);  
        log.info("weixin api appsecret:{} ", TokenThread.appsecret);  
  
        if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {  
            log.error("appid and appsecret ���ó�������������");  
        } else {  
        	log.info("������ʱ��ȡaccess_token���߳�  ");
            new Thread(new TokenThread()).start();  
        }
    }

}
