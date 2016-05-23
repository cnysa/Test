/**
 * @Project Name:weixin
 * @File Name:UserManagerImpl.java
 * @Package Name:com.weixin.gacl.manager
 * @author zhanggd
 * @Date:2016��5��14������5:16:13
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
 * @Description: TODO(����)
 * @author zhanggd
 * @date 2016��5��14�� ����5:16:13
 */
@Service("userManagerImpl")
public class UserManagerImpl implements UserManager{
	
	private static Logger log = LoggerFactory.getLogger(UserManagerImpl.class);

	@Autowired
    private UserMapper userMapper;//ע��dao

	@Override
	public boolean isUser(String username) {
		log.debug("����isUser(username={})",username);
		User user = userMapper.selectByPrimaryKey(username);
		if(user != null){
			log.debug("�뿪isUser():true");
			return true;
		}
		log.debug("�뿪isUser():false");
		return false;
	}

	@Override
	public boolean addUser(String username) {
		log.debug("����addUser(username={})",username);
		User user = new User();
		user.setWxUserId(username);
		int result = userMapper.insert(user);
		if(result <=0 ){
			log.debug("�뿪addUser():false");
			return false;
		}
		log.debug("�뿪addUser():true");
		return true;
	}

	@Override
	public User queryUser(String username) {
		log.debug("����queryUser(username={})",username);
		log.debug("�뿪queryUser()");
		return userMapper.selectByPrimaryKey(username);
	}

	@Override
	public boolean updateUser(String id, String username, String password) {
		log.debug("����updateUser(id="+id+", username="+username+", password="+password+")");
		User user = new User();
		user.setWxUserId(id);
		user.setWxUserXh(username);
		user.setWxUserMm(password);
		int result = userMapper.updateByPrimaryKey(user);
		if(result <=0 ){
			log.debug("�뿪updateUser():false");
			return false;
		}
		log.debug("�뿪updateUser():true");
		return true;
	}
	
}
