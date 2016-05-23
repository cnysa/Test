package com.weixin.web.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.weixin.web.util.UserInSession;

public class HandlerInterceptor1 extends HandlerInterceptorAdapter {
	private static Logger log = LoggerFactory.getLogger(HandlerInterceptor1.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		UserInSession userInSession = (UserInSession) request.getSession(true)
                .getAttribute("userInSession");
		log.debug("进入preHandle()");
		boolean iscontain = true;
		String uri = request.getRequestURI(); 
		String contextPath = request.getContextPath();
		log.info("请求链接 {}",uri);
		if(uri.contains("css") || uri.contains("images") || uri.contains("js") || uri.contains("listen")||uri.contains("wx")){
			log.info("资源请求或微信访问");
			log.debug("离开preHandle()");
			return true;
		}
		if(uri.contains("/login")){
			iscontain = false;
        }else{
        	iscontain = true;
        }
		// 必须是登录后的才能访问
        if (iscontain) {
            if (userInSession != null && userInSession.getLogged()) {
            	log.info("登陆用户{}",userInSession.getId());
            	log.debug("离开preHandle()");
                return true;
            }else {
                StringBuilder url = new StringBuilder();
                //未登录重定向
                url.append(contextPath).append("/loginPage.htm");
                response.sendRedirect(url.toString());
                log.debug("离开preHandle()");
                return false;
            }
        }else {// 必须是未登录的才能访问
            if (userInSession == null || !userInSession.getLogged()) {
            	log.debug("离开preHandle()");
                return true;
            }else {
            	StringBuilder url = new StringBuilder();
                //未登录重定向
                url.append(contextPath).append("/home.htm");
                response.sendRedirect(url.toString());
                log.debug("离开preHandle()");
                return false;
            }
        }
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
	/**
     * 
     * @Method: getReturnUrl 
     * @Description: 重定向页面处理
     * @param request
     * @return
     */
    @SuppressWarnings("unused")
	private String getReturnUrl(HttpServletRequest request) {
        try {
            StringBuffer sb=new StringBuffer();
            sb.append(request.getRequestURI());
            String contextPath=request.getContextPath();
            appendRequestParameters(sb, request);
            String requestURI= sb.toString();
            requestURI = requestURI.replaceAll(contextPath, "");
            return URLEncoder.encode(requestURI,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
    
    /**
     * 
     * @Method: appendRequestParameters 
     * @Description: 重定向页面参数处理
     * @param sb
     * @param request
     */
    protected void appendRequestParameters(StringBuffer sb,
            HttpServletRequest request) {
        Enumeration<?> en = request.getParameterNames();
        if (!en.hasMoreElements()) {
            return;
        }
        sb.append('?');
        while (en.hasMoreElements()) {
            String name = (String) en.nextElement();
            String[] values = request.getParameterValues(name);
            if (values == null || values.length == 0) {
                continue;
            }
            for (String v : values) {
                try {
                    v = URLEncoder.encode(v, request.getCharacterEncoding());
                } catch (UnsupportedEncodingException ignore) {
                }
                sb.append(name).append('=').append(v).append('&');
            }
        }
        sb.deleteCharAt(sb.length() - 1);
    }

}
