package com.weixin.server.servlets.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.server.Manager.MenuManager;
import com.weixin.server.Manager.pojo.AccessToken;
import com.weixin.server.util.WeixinUtil;

/**
 * 
 * @ClassName: TokenThread
 * @Description: TODO(定时获取access_token的线程 ) 
 * @author: zhanggd
 * @date 2016年3月2日下午7:09:39
 */
public class TokenThread implements Runnable {
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);  
    // 第三方用户唯一凭证  
    public static String appid = "";  
    // 第三方用户唯一凭证密钥  
    public static String appsecret = "";  
    public static AccessToken accessToken = null;  
  
    /**
     * 
     * @Title: run 
     * @Description: TODO()
     * @author: zhanggd
     * @date: 2016年3月2日下午7:10:11
     */
    public void run() {  
        while (true) {  
            try {  
                accessToken = WeixinUtil.getAccessToken(appid, appsecret);
                if (null != accessToken) {
        			// 调用接口创建菜单
        			int result = WeixinUtil.createMenu(MenuManager.getMenu(), accessToken.getToken());
        			// 判断菜单创建结果
        			if (0 == result){
        				log.info("菜单创建成功！");
        			}else{
        				log.info("菜单创建失败，错误码：" + result);
        				log.info("服务结束！");
        				break;
        			}
        		}
                if (null != accessToken) {  
                    log.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getToken());  
                    log.info("开始休眠7000秒");   
                    Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);  
                } else {  
                    log.info("获取access_token失败，60秒后再获取");  
                    Thread.sleep(60 * 1000);  
                }  
            } catch (InterruptedException e) {  
                try {  
                    Thread.sleep(60 * 1000);  
                } catch (InterruptedException e1) {  
                    log.error("{}", e1);  
                }  
                log.error("{}", e);  
            }  
        }  
    }  

}
