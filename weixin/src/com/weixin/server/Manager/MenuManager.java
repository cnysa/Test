package com.weixin.server.Manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.server.Manager.pojo.Button;
import com.weixin.server.Manager.pojo.CommonButton;
import com.weixin.server.Manager.pojo.ComplexButton;
import com.weixin.server.Manager.pojo.Menu;
import com.weixin.server.Manager.pojo.ViewButton;
import com.weixin.server.servlets.threads.TokenThread;
import com.weixin.server.util.EnumManager;

/**
 * 
 * @ClassName: MenuManager
 * @Description: TODO(�˵���������) 
 * @author: zhanggd
 * @date 2016��3��11������11:48:50
 */
public class MenuManager {
	
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);
	/**
	 * 
	 * @Title: getMenu 
	 * @Description: TODO(��װ�˵�����)
	 * @return
	 * @author: zhanggd16816
	 * @date: 2016��3��11������11:58:11
	 */
	public static Menu getMenu(String url12,String url13,String url14,String url22,String url23,String url24,String url25,String url32,String url34) {
		log.debug("����getMenu()");
		CommonButton btn11 = new CommonButton();
		btn11.setName(EnumManager.MENU_XYJJ.getRespMsg());
		btn11.setType("click");
		btn11.setKey(EnumManager.MENU_XYJJ.getRespCode());

		ViewButton btn12 = new ViewButton();
		btn12.setName(EnumManager.MENU_XYXW.getRespMsg());
		btn12.setType("view");
		if(null == url12){
			btn12.setUrl("http://it.qlu.edu.cn/list.php?catid=54");
		}else{
			btn12.setUrl(url12);
		}

		ViewButton btn13 = new ViewButton();
		btn13.setName(EnumManager.MENU_JXKY.getRespMsg());
		btn13.setType("view");
		if(null == url13){
			btn13.setUrl("http://it.qlu.edu.cn/list.php?catid=55");
		}else{
			btn13.setUrl(url13);
		}
		

		ViewButton btn14 = new ViewButton();
		btn14.setName(EnumManager.MENU_GGTZ.getRespMsg());
		btn14.setType("view");
		if(null == url14){
			btn14.setUrl("http://it.qlu.edu.cn/list.php?catid=53");
		}else{
			btn14.setUrl(url14);
		}
		
		ViewButton btn22 = new ViewButton();
		btn22.setName(EnumManager.MENU_ZYJS.getRespMsg());
		btn22.setType("view");
		if(null == url22){
			btn22.setUrl(TokenThread.weburl+"/weixin/wx_match");
		}else{
			btn22.setUrl(url22);
		}

//		String cjcxUrl = TokenThread.weburl+"/weixin/wx_cjcx";
		String cjcxUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+TokenThread.appid+"&redirect_uri="+TokenThread.weburl+"/weixin/wx_cjcx.htm&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
		System.out.println(cjcxUrl);
		ViewButton btn23 = new ViewButton();
		btn23.setName(EnumManager.MENU_CJCX.getRespMsg());
		btn23.setType("view");
		if(null == url23){
			btn23.setUrl(cjcxUrl);
		}else{
			btn23.setUrl(url23);
		}
		
		ViewButton btn24 = new ViewButton();
		btn24.setName(EnumManager.MENU_TSCX.getRespMsg());
		btn24.setType("view");
		btn24.setUrl(EnumManager.MENU_TSCX.getRespCode());
		if(null == url24){
			btn24.setUrl("http://m.5read.com/634");
		}else{
			btn24.setUrl(url24);
		}
		
		ViewButton btn25 = new ViewButton();
		btn25.setName(EnumManager.MENU_LSLXFS.getRespMsg());
		btn25.setType("view");
		if(null == url25){
			btn25.setUrl(TokenThread.weburl+"/weixin/wx_tel");
		}else{
			btn25.setUrl(url25);
		}
		
		ViewButton btn32 = new ViewButton();
		btn32.setName(EnumManager.MENU_DQHD.getRespMsg());
		btn32.setType("view");
		if(null == url32){
			btn32.setUrl(TokenThread.weburl+"/weixin/wx_vote");
		}else{
			btn32.setUrl(url34);
		}
		
		ViewButton btn34 = new ViewButton();
		btn34.setName(EnumManager.MENU_SYBZ.getRespMsg());
		btn34.setType("view");
		if(null == url34){
			btn34.setUrl(TokenThread.weburl+"/weixin/wx_explain");
		}else{
			btn34.setUrl(url34);
		}
		

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("T��Ѷ");
		mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("T���");
		mainBtn2.setSub_button(new Button[] { btn22, btn23, btn24, btn25 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("T����");
		mainBtn3.setSub_button(new Button[] { btn32, btn34 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });
		log.debug("�뿪getMenu()");
		return menu;
	}
}
