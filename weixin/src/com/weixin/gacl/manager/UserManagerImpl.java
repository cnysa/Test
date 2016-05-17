/**
 * @Project Name:weixin
 * @File Name:UserManagerImpl.java
 * @Package Name:com.weixin.gacl.manager
 * @author zhanggd
 * @Date:2016��5��14������5:16:13
 */

package com.weixin.gacl.manager;

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

	@Autowired
    private UserMapper userMapper;//ע��dao

	@Override
	public boolean isUser(String username) {
		User user = userMapper.selectByPrimaryKey(username);
		if(user != null){
			return true;
		}
		return false;
	}

	@Override
	public void insertUser(String username) {
		User user = new User();
		user.setWxUserId(username);
		userMapper.insert(user);
	}
	
}
