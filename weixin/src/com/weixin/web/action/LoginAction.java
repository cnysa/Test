package com.weixin.web.action;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weixin.web.manager.interfaces.WxAdminManager;
import com.weixin.web.util.VerifyCode;

import freemarker.template.utility.StringUtil;

@Controller
public class LoginAction extends BaseAction{
	Logger logger = LoggerFactory.getLogger(LoginAction.class);
	
	@Autowired
	private WxAdminManager wxAdminManagerImpl;
	
	@RequestMapping(value="/loginPage")
	public String loginPage(
			final ModelMap model) {
		logger.info("========loginPage");
		return "login";
	}
	
	@RequestMapping(value="/login")
	public String login(
			final @RequestParam(value = "id", required = true) String id,
			final @RequestParam(value = "password", required = true) String password,
			final @RequestParam(value = "vcode", required = true) String vcode,
			final ModelMap model){
		log.info("id:"+id);
		log.info("password:"+password);
		if(StringUtils.isEmpty(id.trim()) || StringUtils.isEmpty(password.trim())){
			model.put("err", "用户名或密码不能为空");
			return "login";
		}
		if(StringUtils.isEmpty(vcode.trim())){
			model.put("err", "验证码不能为空");
			return "login";
		}
		if(!vcode.equals(((String)getSession().getAttribute("session_vcode")).toLowerCase())){
			log.info(getSession().getAttribute("session_vcode"));
			model.put("err", "验证码不正确");
			return "login";
		}
		if(!wxAdminManagerImpl.verifLofin(id, password)){
			model.put("err", "用户名或密码不正确");
			return "login";
		}
		
		return "home";
	}
	
}
