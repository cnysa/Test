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
//        //���ģ������ �����������POJO����  
//        mv.addObject("message", "hahaha");  
//        //�����߼���ͼ������ͼ����������ݸ����ֽ������������ͼҳ��  
//        mv.setViewName("hello"); 
		model.put("message", "jaja");
        return "/hello";
	}

}
