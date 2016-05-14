package com.weixin.server.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.server.servlets.threads.TokenThread;

/**
 * 
 * @ClassName: InitServlet
 * @Description: TODO(用于保存并更新access_token值) 
 * @author: zhanggd
 * @date 2016年3月2日下午7:05:10
 */
public class InitServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(InitServlet.class);
	
    /**
     * 
     * @throws ServletException
     */
    public void init(){
    	// 获取web.xml中配置的参数  
        TokenThread.appid = getInitParameter("appid");  
        TokenThread.appsecret = getInitParameter("appsecret");  
  
        log.info("weixin api appid:{} ", TokenThread.appid);  
        log.info("weixin api appsecret:{} ", TokenThread.appsecret);  
  
        if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {  
            log.error("appid and appsecret 配置出错，请重新配置");  
        } else {  
        	log.info("启动定时获取access_token的线程  ");
            new Thread(new TokenThread()).start();  
        }
    }

}
