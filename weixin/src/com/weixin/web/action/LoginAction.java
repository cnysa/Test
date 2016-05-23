package com.weixin.web.action;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weixin.gacl.manager.interfaces.AdminManager;
import com.weixin.web.util.UserInSession;

@Controller
public class LoginAction extends BaseAction{
	Logger logger = LoggerFactory.getLogger(LoginAction.class);
	
	@Autowired
	private AdminManager adminManagerImpl;
	
	@RequestMapping(value="/loginPage")
	public String loginPage(
			final ModelMap model) {
		log.debug("进入loginPage()");
		log.debug("离开loginPage()");
		return "login";
	}
	
	@RequestMapping(value="/login")
	public String login(
			final @RequestParam(value = "id", required = true) String id,
			final @RequestParam(value = "password", required = true) String password,
			final @RequestParam(value = "vcode", required = true) String vcode,
			final ModelMap model){
		log.debug("进入login(id="+id+",password="+password+",vcode="+vcode+")");
		UserInSession u = null;
		u = getLoginUser();
		if (u != null && u.getLogged()) {
			log.debug("离开login():{}","home");
			return  "home"; 
		}
		if(StringUtils.isEmpty(id.trim()) || StringUtils.isEmpty(password.trim())){
			model.put("err", "用户名或密码不能为空");
			log.debug("离开login():{}:{}","login",model.get("err"));
			return "login";
		}
		if(StringUtils.isEmpty(vcode.trim())){
			model.put("err", "验证码不能为空");
			log.debug("离开login():{}:{}","login",model.get("err"));
			return "login";
		}
		if((String)getSession().getAttribute("session_vcode") == null ){
			log.debug("离开login():{}","login");
			return "login";
		}
		if(!vcode.equals(((String)getSession().getAttribute("session_vcode")).toLowerCase())){
			model.put("err", "验证码不正确");
			log.debug("离开login():{}:{}","login",model.get("err"));
			return "login";
		}
		if(!adminManagerImpl.verifLofin(id, password)){
			model.put("err", "用户名或密码不正确");
			return "login";
		}
		u = new UserInSession();
		u.setId(id);
		u.setPassword(password);
		u.setLogged(true);
		getSession().setAttribute("userInSession", u);
		model.put("admin", u.getId());
		log.debug("离开login():{}","home");
		return "home";
	}
	
	@RequestMapping(value="/logged")
	public String logged(final ModelMap model){
		log.debug("进入logged()");
		UserInSession u = null;
		u=getLoginUser();
		if (u != null && u.getLogged()) {
			u.setLogged(false);
			getSession().setAttribute("userInSession", null);
		}
		log.debug("离开logged():{}","login");
		return "login";
	}
	
	
	
}
