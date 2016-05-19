/**
 * @Project Name:weixin
 * @File Name:TelcxAction.java
 * @Package Name:com.weixin.web.action
 * @author zhanggd
 * @Date:2016��5��17������5:53:59
 */

package com.weixin.web.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weixin.gacl.manager.interfaces.TelManager;
import com.weixin.gacl.mapping.beans.Tel;

/**
 * @ClassName: TelcxAction
 * @Description: TODO(����)
 * @author zhanggd
 * @date 2016��5��17�� ����5:53:59
 */
@Controller
public class TelcxAction extends BaseAction{
	
	@Autowired
	private TelManager telManagerImpl;
	
	@RequestMapping(value="/wx_tel")
	public String cjcx(final ModelMap model){
		Tel[] tels = telManagerImpl.queryAll();
		model.put("tels", tels);
		return "tel";
	}
	
	@RequestMapping(value="/telDel", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public @ResponseBody String telDel(
			final @RequestParam(value = "telName", required = true) String telName,
			final ModelMap model){
		log.info(telName);
		Map<String,Object> map=new HashMap<String, Object>();
		if(telManagerImpl.delTel(telName)){
			map.put("status", "1");
			map.put("msg", "ɾ���ɹ���");
			log.info("ɾ���ɹ���");
		}else{
			map.put("status", "0");
			map.put("msg", "ɾ��ʧ�ܣ�");
			log.info("ɾ��ʧ�ܣ�");
		}
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping(value="/telUpdate", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public @ResponseBody String telUpdate(
			final @RequestParam(value = "telName", required = true) String telName,
			final @RequestParam(value = "telNnum1", required = true) String telNum1,
			final @RequestParam(value = "telNnum2", required = true) String telNum2,
			final ModelMap model){
		log.info(telName);
		log.info(telNum1);
		log.info(telNum2);
		Map<String,Object> map=new HashMap<String, Object>();
		String num = telName;
		String num1 = telNum1;
		String num2 = telNum2;
		if(num.equals("")){
			map.put("status", "0");
			map.put("msg", "�޸�ʧ�ܣ�");
			log.info("�޸�ʧ�ܣ�");
			return JSONObject.fromObject(map).toString();
		}
		if(num1.equals("")){
			num1 = null;
		}
		if(num2.equals("")){
			num2 = null;
		}
		if(telManagerImpl.update(num, num1, num2)){
			map.put("status", "1");
			map.put("msg", "�޸ĳɹ���");
			log.info("�޸ĳɹ���");
		}else{
			map.put("status", "0");
			map.put("msg", "�޸�ʧ�ܣ�");
			log.info("�޸�ʧ�ܣ�");
		}
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping(value="/telAdd", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public @ResponseBody String telAdd(
			final @RequestParam(value = "telName", required = true) String telName,
			final @RequestParam(value = "telNnum1", required = true) String telNum1,
			final @RequestParam(value = "telNnum2", required = true) String telNum2,
			final ModelMap model){
		log.info(telName);
		log.info(telNum1);
		log.info(telNum2);
		Map<String,Object> map=new HashMap<String, Object>();
		String num = telName;
		String num1 = telNum1;
		String num2 = telNum2;
		if(num.equals("")){
			map.put("status", "0");
			map.put("msg", "���ʧ�ܣ�");
			log.info("���ʧ�ܣ�");
			return JSONObject.fromObject(map).toString();
		}
		if(num1.equals("")){
			num1 = null;
		}
		if(num2.equals("")){
			num2 = null;
		}
		if(telManagerImpl.addTel(num, num1, num2)){
			map.put("status", "1");
			map.put("msg", "��ӳɹ���");
			log.info("��ӳɹ���");
		}else{
			map.put("status", "0");
			map.put("msg", "���ʧ�ܣ�");
			log.info("���ʧ�ܣ�");
		}
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping(value="/telManager")
	public String telManage(final ModelMap model){
		Tel[] tels = telManagerImpl.queryAll();
		model.put("tels", tels);
		return "telManager";
	}
	
}
