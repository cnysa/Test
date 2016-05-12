package com.weixin.web.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.weixin.web.util.UserInSession;

public class BaseAction {
    
    protected final Log log = LogFactory.getLog(getClass());
    
    public static HttpSession getSession() {
        HttpSession session = null;
        try {
            session = getRequest().getSession(true);
        } catch (Exception e) {
        }
        return session;
    }
    
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return attrs.getRequest();
    }
    
    public static HttpServletResponse getResponse() {
    	ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return attrs.getResponse();
    }
    
    /**
     * 
     * @Title: getLoginUser 
     * @Description: (获取登录用户信息) 
     * @param @return    设定文件 
     * @return UserInSession    返回类型 
     * @throws
     */
    public UserInSession getLoginUser() {
        UserInSession userInSession = (UserInSession) BaseAction.getRequest()
                .getSession(true).getAttribute("userInSession");
        if (userInSession != null && userInSession.getLogged())
            return userInSession;
        else
            return null;
    }
    
    public String getIpAddress() {
        HttpServletRequest request = getRequest();
        String ip = "";
        ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ip)
                || StringUtils.equalsIgnoreCase(ip, "unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip)
                || StringUtils.equalsIgnoreCase(ip, "unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip)
                || StringUtils.equalsIgnoreCase(ip, "unknown")) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip)
                || StringUtils.equalsIgnoreCase(ip, "unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip)
                || StringUtils.equalsIgnoreCase(ip, "unknown")) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isNotBlank(ip) && StringUtils.indexOf(ip, ",") > 0) {
            String[] ipArray = StringUtils.split(ip, ",");
            ip = ipArray[0];
        }
        return ip;
    }
    
}
