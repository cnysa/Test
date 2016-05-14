/**
 * @Project Name:weixin
 * @File Name:UserManagerImpl.java
 * @Package Name:com.weixin.gacl.manager
 * @author zhanggd
 * @Date:2016年5月14日下午5:16:13
 */

package com.weixin.gacl.manager.copy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.gacl.manager.interfaces.UserManager;
import com.weixin.gacl.mapping.beans.User;
import com.weixin.gacl.mapping.dao.AdminMapper;
import com.weixin.gacl.mapping.dao.UserMapper;

/**
 * @ClassName: UserManagerImpl
 * @Description: TODO(描述)
 * @author zhanggd
 * @date 2016年5月14日 下午5:16:13
 */
@Service("UserManagerImpl")
public class UserManagerImpl implements UserManager{

	@Autowired
    private UserMapper userMapper;//注入dao
	
	@Override
	public void addUser(User user) {
		userMapper.insert(user);
	}

	@Override
	public User getUser(String wxUserId) {
		return userMapper.selectByPrimaryKey(wxUserId);
	}

}
