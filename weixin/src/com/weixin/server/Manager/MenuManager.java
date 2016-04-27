package com.weixin.server.Manager;

import com.weixin.server.pojo.Button;
import com.weixin.server.pojo.CommonButton;
import com.weixin.server.pojo.ComplexButton;
import com.weixin.server.pojo.Menu;
import com.weixin.server.pojo.ViewButton;
import com.weixin.server.util.EnumManager;

/**
 * 
 * @ClassName: MenuManager
 * @Description: TODO(菜单管理器类) 
 * @author: zhanggd
 * @date 2016年3月11日上午11:48:50
 */
public class MenuManager {

	/**
	 * 
	 * @Title: getMenu 
	 * @Description: TODO(组装菜单数据)
	 * @return
	 * @author: zhanggd16816
	 * @date: 2016年3月11日上午11:58:11
	 */
	public static Menu getMenu() {
		CommonButton btn11 = new CommonButton();
		btn11.setName(EnumManager.MENU_XYJJ.getRespMsg());
		btn11.setType("click");
		btn11.setKey(EnumManager.MENU_XYJJ.getRespCode());

		ViewButton btn12 = new ViewButton();
		btn12.setName(EnumManager.MENU_XYXW.getRespMsg());
		btn12.setType("view");
		btn12.setUrl("http://it.qlu.edu.cn/list.php?catid=54");

		ViewButton btn13 = new ViewButton();
		btn13.setName(EnumManager.MENU_JXKY.getRespMsg());
		btn13.setType("view");
		btn13.setUrl("http://it.qlu.edu.cn/list.php?catid=55");

		ViewButton btn14 = new ViewButton();
		btn14.setName(EnumManager.MENU_GGTZ.getRespMsg());
		btn14.setType("view");
		btn14.setUrl("http://it.qlu.edu.cn/list.php?catid=53");

		CommonButton btn21 = new CommonButton();
		btn21.setName(EnumManager.MENU_Xl.getRespMsg());
		btn21.setType("click");
		btn21.setKey(EnumManager.MENU_Xl.getRespCode());

		CommonButton btn22 = new CommonButton();
		btn22.setName(EnumManager.MENU_ZYJS.getRespMsg());
		btn22.setType("click");
		btn22.setKey(EnumManager.MENU_ZYJS.getRespCode());

		CommonButton btn23 = new CommonButton();
		btn23.setName(EnumManager.MENU_CJCX.getRespMsg());
		btn23.setType("click");
		btn23.setKey(EnumManager.MENU_CJCX.getRespCode());

		CommonButton btn24 = new CommonButton();
		btn24.setName(EnumManager.MENU_YKTCZ.getRespMsg());
		btn24.setType("click");
		btn24.setKey(EnumManager.MENU_YKTCZ.getRespCode());
		
		CommonButton btn25 = new CommonButton();
		btn25.setName(EnumManager.MENU_LSLXFS.getRespMsg());
		btn25.setType("click");
		btn25.setKey(EnumManager.MENU_LSLXFS.getRespCode());

		CommonButton btn31 = new CommonButton();
		btn31.setName(EnumManager.MENU_BBQ.getRespMsg());
		btn31.setType("click");
		btn31.setKey(EnumManager.MENU_BBQ.getRespCode());

		CommonButton btn32 = new CommonButton();
		btn32.setName(EnumManager.MENU_DQHD.getRespMsg());
		btn32.setType("click");
		btn32.setKey(EnumManager.MENU_DQHD.getRespCode());
		
		ViewButton btn34 = new ViewButton();
		btn34.setName(EnumManager.MENU_SYBZ.getRespMsg());
		btn34.setType("view");
		btn34.setUrl("http://www.baidu.com/");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("T资讯");
		mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("T帮帮");
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24, btn25 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("T娱乐");
		mainBtn3.setSub_button(new Button[] { btn31, btn32, btn34 });

		/**
		 * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}
