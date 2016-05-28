package com.weixin.web.action;

import java.io.IOException;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weixin.gacl.manager.interfaces.UserManager;
import com.weixin.gacl.mapping.beans.User;
import com.weixin.server.Manager.MenuManager;
import com.weixin.server.Manager.pojo.AccessToken;
import com.weixin.server.servlets.threads.TokenThread;
import com.weixin.server.util.WeixinUtil;

@Controller(value="homeAction")
public class HomeAction extends BaseAction{
	
	@Autowired
    private UserManager userManagerImpl;//注入dao
	
    @RequestMapping(value="/home")
    public String home(final ModelMap model){
    	log.debug("进入home()");
    	log.debug("离开home()");
    	return "home";
    }
	
	@RequestMapping(value="/menuManager")
	public String homeManager(final ModelMap model){
		log.debug("进入homeManager()");
		String result = queryMenu();
		if(result != null){
			model.put("menu", result);
			log.debug("离开homeManager():menuManager");
			return "menuManager";
		}
		model.put("msg", "菜单查询失败！");
		log.debug("离开homeManager():resultAdmin");
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
			final @RequestParam(value = "793", required = true) String url7,
			final @RequestParam(value = "794", required = true) String url8,
			final @RequestParam(value = "795", required = true) String url9,
			final ModelMap model){
		log.debug("进入createMenu()");
	    AccessToken accessToken = TokenThread.accessToken;
        if (null != accessToken) {
			int result = WeixinUtil.createMenu(MenuManager.getMenu(url1,url2,url3,url4,url5,url6,url7,url8,url9), accessToken.getToken());
			if (0 == result){
				model.put("msg", "菜单创建成功！");
			}else{
				model.put("msg", "菜单创建失败！");
			}
		}
        log.debug("离开createMenu():resultAdmin:{}",model.get("msg"));
        return "resultAdmin";
	}
	
	public String queryMenu(){
		log.debug("进入queryMenu()");
		AccessToken accessToken = TokenThread.accessToken;
        if (null != accessToken) {
			// 调用接口查询菜单
			String result = WeixinUtil.queryMenu(accessToken.getToken());
			// 判断菜单查询结果
			if ("0".equals(result)){
				log.info("事务 菜单查询失败，错误码：" + result);
				log.debug("离开queryMenu():null");
				return null;
			}
			log.debug("离开queryMenu():{}",result);
			return result;
		}
        log.debug("离开queryMenu():null");
        return null;
	}
	
	@RequestMapping(value="/wx_bindPage")
	public String wxBingPage(
			final @RequestParam(value = "code", required = false) String code,
			final @RequestParam(value = "state", required = false) String state,
			final ModelMap model){
		log.debug("进入wxBingPage(code="+code+",state="+state+")");
		if(code != null){
			String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+TokenThread.appid
					+"&secret="+TokenThread.appsecret
					+"&code="+code
					+"&grant_type=authorization_code";
			JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
			String json = jsonObject.toString();
			if (null != jsonObject && json.indexOf("openid")>0) {
				String openid = jsonObject.getString("openid");
				User user = userManagerImpl.queryUser(openid);
				if(user != null && user.getWxUserXh()!=null && !user.getWxUserXh().equals("") && user.getWxUserMm()!=null && !user.getWxUserMm().equals("")){
					model.put("msg", "您已经绑定！");
					model.put("status", "0");
					log.debug("离开wxBingPage():{}:{}","result",model.get("msg"));
					return "result";
				}
				model.put("openid", openid);
				log.debug("离开wxBingPage():{}:{}","result",model.get("msg"));
				return "bindPage";
			}
		}
		model.put("msg", "网络错误,绑定失败！");
		model.put("status", "0");
		log.debug("离开wxBingPage():{}:{}","result",model.get("msg"));
		return "result";
	}
	
	 @RequestMapping(value="/wx_bind")
		public String wxBing(
				final @RequestParam(value = "username", required = true) String username,
				final @RequestParam(value = "password", required = true) String password,
				final @RequestParam(value = "openid", required = true) String openid,
				final ModelMap model) throws IOException{
			 log.debug("进入wxBing(username="+username+",password="+password+",openid="+openid+")");
			Map<String,Object> map = CjcxAction.cjcxPost(username,password,"","");
			String status = (String)map.get("status");
			if("0".equals(status)){
				model.put("msg", "网络错误绑定失败！");
				model.put("status", "0");
				model.put("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+TokenThread.appid+"&redirect_uri="+TokenThread.weburl+"/weixin/wx_bindPage.htm&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
				log.debug("离开wxBing():{}:{}","result",model.get("msg"));
				return "result";
			}else if("2".equals(status)){
				model.put("msg", "账号或者密码错误！");
				model.put("status", "0");
				model.put("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+TokenThread.appid+"&redirect_uri="+TokenThread.weburl+"/weixin/wx_bindPage.htm&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
				log.debug("离开wxBing():{}:{}","result",model.get("msg"));
				return "result";
			}
			
			if(userManagerImpl.updateUser(openid, username, password)){
				model.put("msg", "绑定成功！");
				model.put("status", "1");
				log.debug("离开wxBing():{}:{}","result",model.get("msg"));
				return "result";
			}
			model.put("msg", "网络错误绑定失败！");
			model.put("status", "0");
			model.put("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+TokenThread.appid+"&redirect_uri="+TokenThread.weburl+"/weixin/wx_bindPage.htm&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
			log.debug("离开wxBing():{}:{}","result",model.get("msg"));
			return "result";
		}
	 
	 @RequestMapping(value="/wx_explain")
	 public String wxExplain(final ModelMap model){
		 log.debug("进入wxExplain()");
		 model.put("appid", TokenThread.appid);
		 String weburl = TokenThread.weburl+"/weixin/wx_bindPage.htm";
		 model.put("weburl", weburl);
		 log.debug("离开wxExplain():{}","explain");
		 return "explain";
	 }
	
}
