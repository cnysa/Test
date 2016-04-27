package com.weixin.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController{
	private static Logger log = LoggerFactory.getLogger(HelloWorldController.class);
	
	@RequestMapping(value="/hello")
	public String helloWorld(final ModelMap model){
		log.info("=======hello");
//		ModelAndView mv = new ModelAndView();
//        //添加模型数据 可以是任意的POJO对象  
//        mv.addObject("message", "hahaha");  
//        //设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面  
//        mv.setViewName("hello"); 
		model.put("message", "jaja");
        return "/hello";
	}

}
