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
    	log.debug("进入init()");
    	log.info("事务 {}","获取web.xml中配置的参数");
        TokenThread.appid = getInitParameter("appid");  
        TokenThread.appsecret = getInitParameter("appsecret");
        TokenThread.weburl = getInitParameter("weburl");
        TokenThread.token = getInitParameter("token");
        log.info("事务 {} appid:{} ", "获取参数",TokenThread.appid);  
        log.info("事务 {} appsecret:{} ", "获取参数",TokenThread.appsecret);
        log.info("事务 {} weburl:{} ", "获取参数",TokenThread.weburl); 
        log.info("事务 {} token:{} ", "获取参数",TokenThread.token); 
        log.info("事务 {}","获取web.xml中配置的参数完成");
  
        if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {  
            log.error("appid and appsecret 配置出错，请重新配置");  
        } else {  
        	log.info("事务 {}","启动TokenThread线程  ");
            new Thread(new TokenThread()).start();  
        }
        log.debug("离开init()");
    }

}
