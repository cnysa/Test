package com.weixin.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weixin.web.manager.interfaces.WxAdminManager;

@Controller
public class LoginAction extends BaseAction{
	Logger logger = LoggerFactory.getLogger(LoginAction.class);
	
	@Autowired
	private WxAdminManager wxAdminManagerImpl;
	
	@RequestMapping(value="/loginPage")
	public String loginPage(final ModelMap model) {
		logger.info("========loginPage");
		return "helloword";
	}
}
