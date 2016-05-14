package com.weixin.gacl.manager.copy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.gacl.manager.interfaces.AdminManager;
import com.weixin.gacl.mapping.beans.Admin;
import com.weixin.gacl.mapping.dao.AdminMapper;

/**
 * 
 * @ClassName: AdminServiceImpl
 * @Description: TODO(����Ա����ʵ����)
 * @author zhanggd
 * @date 2016��5��14�� ����4:46:23
 */
@Service("AdminManagerImpl")
public class AdminManagerImpl implements AdminManager{
	
    /**
     * ʹ��@Autowiredע���עuserMapper������
     * ����Ҫʹ��UserMapperʱ��Spring�ͻ��Զ�ע��UserMapper
     */
    @Autowired
    private AdminMapper userMapper;//ע��dao

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
