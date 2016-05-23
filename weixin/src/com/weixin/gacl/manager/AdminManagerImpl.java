package com.weixin.gacl.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.gacl.manager.interfaces.AdminManager;
import com.weixin.gacl.mapping.beans.Admin;
import com.weixin.gacl.mapping.dao.AdminMapper;

@Service("adminManagerImpl")
public class AdminManagerImpl implements AdminManager{
	
	private static Logger log = LoggerFactory.getLogger(AdminManagerImpl.class);

	@Autowired
	private AdminMapper userMapper;//注入dao
	
	@Override
	public boolean verifLofin(String username,String password) {
		log.debug("进入verifLofin(username={},password={})",username,password);
		Admin admin = userMapper.selectByPrimaryKey(username);
		if(admin!=null && admin.getPassword().equals(password)){
			log.debug("离开verifLofin():true");
			return true;
		}
		log.debug("离开verifLofin():false");
		return false;
	}

}
