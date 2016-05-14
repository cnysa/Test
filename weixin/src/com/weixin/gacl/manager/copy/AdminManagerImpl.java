package com.weixin.gacl.manager.copy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.gacl.manager.interfaces.AdminManager;
import com.weixin.gacl.mapping.beans.Admin;
import com.weixin.gacl.mapping.dao.AdminMapper;

/**
 * 
 * @ClassName: AdminServiceImpl
 * @Description: TODO(管理员操作实现类)
 * @author zhanggd
 * @date 2016年5月14日 下午4:46:23
 */
@Service("AdminManagerImpl")
public class AdminManagerImpl implements AdminManager{
	
    /**
     * 使用@Autowired注解标注userMapper变量，
     * 当需要使用UserMapper时，Spring就会自动注入UserMapper
     */
    @Autowired
    private AdminMapper userMapper;//注入dao

    @Override
    public void addUser(Admin user) {
        userMapper.insert(user);
    }

    @Override
    public Admin getAdminByUsername(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

	@Override
	public Admin verifLofin(String username) {
		return userMapper.selectByPrimaryKey(username);
	}
}
