package com.weixin.server.servlets.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.server.Manager.MenuManager;
import com.weixin.server.Manager.pojo.AccessToken;
import com.weixin.server.util.WeixinUtil;

/**
 * 
 * @ClassName: TokenThread
 * @Description: TODO(��ʱ��ȡaccess_token���߳� ) 
 * @author: zhanggd
 * @date 2016��3��2������7:09:39
 */
public class TokenThread implements Runnable {
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);  
    // �������û�Ψһƾ֤  
    public static String appid = "";  
    // �������û�Ψһƾ֤��Կ  
    public static String appsecret = "";  
    public static AccessToken accessToken = null;  
  
    /**
     * 
     * @Title: run 
     * @Description: TODO()
     * @author: zhanggd
     * @date: 2016��3��2������7:10:11
     */
    public void run() {  
        while (true) {  
            try {  
                accessToken = WeixinUtil.getAccessToken(appid, appsecret);
                if (null != accessToken) {
        			// ���ýӿڴ����˵�
        			int result = WeixinUtil.createMenu(MenuManager.getMenu(), accessToken.getToken());
        			// �жϲ˵��������
        			if (0 == result){
        				log.info("�˵������ɹ���");
        			}else{
        				log.info("�˵�����ʧ�ܣ������룺" + result);
        				log.info("���������");
        				break;
        			}
        		}
                if (null != accessToken) {  
                    log.info("��ȡaccess_token�ɹ�����Чʱ��{}�� token:{}", accessToken.getExpiresIn(), accessToken.getToken());  
                    log.info("��ʼ����7000��");   
                    Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);  
                } else {  
                    log.info("��ȡaccess_tokenʧ�ܣ�60����ٻ�ȡ");  
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
