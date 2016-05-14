package com.weixin.web.manager;

import org.springframework.beans.factory.annotation.Autowired;

import com.weixin.gacl.manager.interfaces.AdminManager;
import com.weixin.gacl.mapping.beans.Admin;
import com.weixin.web.manager.interfaces.WxAdminManager;

public class WxAdminManagerImpl implements WxAdminManager{

	@Autowired
	private AdminManager asi;
	
	@Override
	public boolean verifLofin(String username,String password) {
		Admin admin = new Admin();
		admin.setUsername(username);
		Admin a = asi.getAdminByUsername(username);
		if(a!=null && a.getPassword().equals(password))
			return true;
		return false;
	}

}
