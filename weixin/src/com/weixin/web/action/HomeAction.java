package com.weixin.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(value="homeAction")
public class HomeAction extends BaseAction{
	private static Logger log = LoggerFactory.getLogger(HomeAction.class);
	
	@RequestMapping(value="/home")
	public String goHome(final ModelMap model){
		log.info("go home page!");
		return "home";
	}
}
