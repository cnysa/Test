package com.weixin.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weixin.server.Manager.MenuManager;
import com.weixin.server.pojo.AccessToken;
import com.weixin.server.threads.TokenThread;
import com.weixin.server.util.WeixinUtil;

@Controller(value="homeAction")
public class HomeAction extends BaseAction{
	
	private static Logger log = LoggerFactory.getLogger(HomeAction.class);
	
	// �������û�Ψһƾ֤  
    public static String appid = "";  
    // �������û�Ψһƾ֤��Կ  
    public static String appsecret = "";  
    public static AccessToken accessToken = null; 
    
    @RequestMapping(value="/home")
    public String home(final ModelMap model){
    	return "home";
    }
	
	@RequestMapping(value="/menuManager")
	public String goHome(final ModelMap model){
		log.info("go menu Manager!");
		model.put("menu", queryMenu());
		return "menuManager";
	}
	
	public void createMenu(){
		accessToken = TokenThread.accessToken;
        if (null != accessToken) {
			// ���ýӿڴ����˵�
			int result = WeixinUtil.createMenu(MenuManager.getMenu(), accessToken.getToken());
			// �жϲ˵��������
			if (0 == result){
				log.info("�˵������ɹ���");
			}else{
				log.info("�˵�����ʧ�ܣ������룺" + result);
				log.info("���������");
			}
		}
	}
	
	public String queryMenu(){
		accessToken = TokenThread.accessToken;
        if (null != accessToken) {
			// ���ýӿڲ�ѯ�˵�
			String result = WeixinUtil.queryMenu(accessToken.getToken());
			// �жϲ˵���ѯ���
			if ("0".equals(result)){
				log.info("�˵���ѯʧ�ܣ������룺" + result);
				log.info("���������");
				return null;
			}
			return result;
		}
        return null;
	}
	
	
}
