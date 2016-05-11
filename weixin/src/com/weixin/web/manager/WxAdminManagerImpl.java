package com.weixin.web.manager;

import org.springframework.beans.factory.annotation.Autowired;

import com.weixin.gacl.domain.Admin;
import com.weixin.gacl.servece.AdminService;
import com.weixin.web.manager.interfaces.WxAdminManager;

public class WxAdminManagerImpl implements WxAdminManager{

	@Autowired
	private AdminService asi;
	
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
