package com.weixin.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weixin.gacl.servece.AdminService;

@Controller(value="helloWorldController")
public class HelloWorldController{
	private static Logger log = LoggerFactory.getLogger(HelloWorldController.class);
	@Autowired
	private AdminService umi;
	@RequestMapping(value="/hello1")
	public String helloWorld(final ModelMap model){
		log.info("=======hello");
//		ModelAndView mv = new ModelAndView();
//        //���ģ������ �����������POJO����  
//        mv.addObject("message", "hahaha");  
//        //�����߼���ͼ������ͼ����������ݸ����ֽ������������ͼҳ��  
//        mv.setViewName("hello"); 
//        Admin Admin = new Admin();
//        Admin.setUsername("admin1212");
//        Admin.setPassword("555555");
//        umi.addUser(Admin);
//		model.put("message", "jaja");
        return "/hello";
	}
	
	@RequestMapping(value="/xiaoli")
	public String ShowXiaoli(final ModelMap model){
		return "/two/xiaoli";
	}

}
