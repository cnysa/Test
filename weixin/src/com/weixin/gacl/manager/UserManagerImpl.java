/**
 * @Project Name:weixin
 * @File Name:UserManagerImpl.java
 * @Package Name:com.weixin.gacl.manager
 * @author zhanggd
 * @Date:2016年5月14日下午5:16:13
 */

package com.weixin.gacl.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.gacl.manager.interfaces.UserManager;
import com.weixin.gacl.mapping.beans.User;
import com.weixin.gacl.mapping.dao.UserMapper;

/**
 * @ClassName: UserManagerImpl
 * @Description: TODO(描述)
 * @author zhanggd
 * @date 2016年5月14日 下午5:16:13
 */
@Service("userManagerImpl")
public class UserManagerImpl implements UserManager{
	
	private static Logger log = LoggerFactory.getLogger(UserManagerImpl.class);

	@Autowired
    private UserMapper userMapper;//注入dao

	@Override
	public boolean isUser(String username) {
		log.debug("进入isUser(username={})",username);
		User user = userMapper.selectByPrimaryKey(username);
		if(user != null){
			log.debug("离开isUser():true");
			return true;
		}
		log.debug("离开isUser():false");
		return false;
	}

	@Override
	public boolean addUser(String username) {
		log.debug("进入addUser(username={})",username);
		User user = new User();
		user.setWxUserId(username);
		int result = userMapper.insert(user);
		if(result <=0 ){
			log.debug("离开addUser():false");
			return false;
		}
		log.debug("离开addUser():true");
		return true;
	}

	@Override
	public User queryUser(String username) {
		log.debug("进入queryUser(username={})",username);
		log.debug("离开queryUser()");
		return userMapper.selectByPrimaryKey(username);
	}

	@Override
	public boolean updateUser(String id, String username, String password) {
		log.debug("进入updateUser(id="+id+", username="+username+", password="+password+")");
		User user = new User();
		user.setWxUserId(id);
		user.setWxUserXh(username);
		user.setWxUserMm(password);
		int result = userMapper.updateByPrimaryKey(user);
		if(result <=0 ){
			log.debug("离开updateUser():false");
			return false;
		}
		log.debug("离开updateUser():true");
		return true;
	}
	
}
