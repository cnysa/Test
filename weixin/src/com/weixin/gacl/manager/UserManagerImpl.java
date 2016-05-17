/**
 * @Project Name:weixin
 * @File Name:UserManagerImpl.java
 * @Package Name:com.weixin.gacl.manager
 * @author zhanggd
 * @Date:2016年5月14日下午5:16:13
 */

package com.weixin.gacl.manager;

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

	@Autowired
    private UserMapper userMapper;//注入dao

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
