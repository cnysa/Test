package com.weixin.web.action;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weixin.server.Manager.MenuManager;
import com.weixin.server.Manager.pojo.AccessToken;
import com.weixin.server.servlets.threads.TokenThread;
import com.weixin.server.util.WeixinUtil;

@Controller(value="homeAction")
public class HomeAction extends BaseAction{
	
	private static Logger log = LoggerFactory.getLogger(HomeAction.class);
    
    @RequestMapping(value="/home")
    public String home(final ModelMap model){
    	return "home";
    }
	
	@RequestMapping(value="/menuManager")
	public String goHome(final ModelMap model){
		log.info("go menu Manager!");
		String result = queryMenu();
		if(result != null){
			model.put("menu", result);
			return "menuManager";
		}
		model.put("msg", "�˵���ѯʧ�ܣ�");
		return "resultAdmin";
	}
	
	@RequestMapping(value="/createMenu")
	public String createMenu(
			final @RequestParam(value = "787", required = true) String url1,
			final @RequestParam(value = "788", required = true) String url2,
			final @RequestParam(value = "789", required = true) String url3,
			final @RequestParam(value = "790", required = true) String url4,
			final @RequestParam(value = "791", required = true) String url5,
			final @RequestParam(value = "792", required = true) String url6,
			final ModelMap model){
		log.info("url1:"+url1);
		log.info("url2:"+url2);
		log.info("url3:"+url3);
		log.info("url4:"+url4);
		log.info("url5:"+url5);
		log.info("url6:"+url6);
	    AccessToken accessToken = TokenThread.accessToken;
        if (null != accessToken) {
			int result = WeixinUtil.createMenu(MenuManager.getMenu(url1,url2,url3,url4,url5,url6), accessToken.getToken());
			if (0 == result){
				log.info("�˵������ɹ���");
				model.put("msg", "�˵������ɹ���");
			}else{
				log.info("�˵�����ʧ�ܣ������룺" + result);
				model.put("msg", "�˵�����ʧ�ܣ�");
			}
		}
        return "resultAdmin";
	}
	
	public String queryMenu(){
		AccessToken accessToken = TokenThread.accessToken;
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
