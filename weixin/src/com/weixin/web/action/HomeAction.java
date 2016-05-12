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
	
	// 第三方用户唯一凭证  
    public static String appid = "";  
    // 第三方用户唯一凭证密钥  
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
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(MenuManager.getMenu(), accessToken.getToken());
			// 判断菜单创建结果
			if (0 == result){
				log.info("菜单创建成功！");
			}else{
				log.info("菜单创建失败，错误码：" + result);
				log.info("服务结束！");
			}
		}
	}
	
	public String queryMenu(){
		accessToken = TokenThread.accessToken;
        if (null != accessToken) {
			// 调用接口查询菜单
			String result = WeixinUtil.queryMenu(accessToken.getToken());
			// 判断菜单查询结果
			if ("0".equals(result)){
				log.info("菜单查询失败，错误码：" + result);
				log.info("服务结束！");
				return null;
			}
			return result;
		}
        return null;
	}
	
	
}
