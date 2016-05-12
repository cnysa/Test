package com.weixin.web.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
		log.info("===========HandlerInterceptor1 preHandle");
		UserInSession userInSession = (UserInSession) request.getSession(true)
                .getAttribute("userInSession");
		boolean iscontain = true;
		String uri = request.getRequestURI(); 
		String contextPath = request.getContextPath();
		log.info("url: "+uri);
		if(uri.contains("css") || uri.contains("images") || uri.contains("js")){
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
                return true;
            }else {
                StringBuilder url = new StringBuilder();
//                String reUrl = getReturnUrl(request);
//                log.info("重定向url1："+reUrl);
//                StringBuilder param=new StringBuilder();
                //未登录重定向
                url.append(contextPath).append("/loginPage.htm");
//                if (StringUtils.isNotBlank(reUrl)) {
//                    param.append("?reUrl=").append(reUrl);
//                }
//                if(param.length()!=0){
//                    url.append(param);
//                }
                response.sendRedirect(url.toString());
                return false;
            }
        }else {// 必须是未登录的才能访问
            if (userInSession == null || !userInSession.getLogged()) {
                return true;
            }else {
            	StringBuilder url = new StringBuilder();
//                String reUrl = getReturnUrl(request);
//                log.info("重定向url："+reUrl);
//                StringBuilder param=new StringBuilder();
                //未登录重定向
                url.append(contextPath).append("/home.htm");
//                if (StringUtils.isNotBlank(reUrl)) {
//                    param.append("?reUrl=").append(reUrl);
//                }
//                if(param.length()!=0){
//                    url.append(param);
//                }
                response.sendRedirect(url.toString());
                return false;
            }
        }
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("===========HandlerInterceptor1 postHandle");  
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("===========HandlerInterceptor1 afertHandle");  
	}
	
	/**
     * 
     * @Method: getReturnUrl 
     * @Description: 重定向页面处理
     * @param request
     * @return
     */
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
