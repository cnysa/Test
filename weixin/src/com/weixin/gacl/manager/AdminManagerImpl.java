package com.weixin.gacl.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.gacl.manager.interfaces.AdminManager;
import com.weixin.gacl.mapping.beans.Admin;
import com.weixin.gacl.mapping.dao.AdminMapper;

@Service("adminManagerImpl")
public class AdminManagerImpl implements AdminManager{

	@Autowired
	private AdminMapper userMapper;//×¢Èëdao
	
	@Override
	public boolean verifLofin(String username,String password) {
		Admin admin = userMapper.selectByPrimaryKey(username);
		if(admin!=null && admin.getPassword().equals(password))
			return true;
		return false;
	}

}
