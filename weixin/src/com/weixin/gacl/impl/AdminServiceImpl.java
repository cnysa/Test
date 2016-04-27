package com.weixin.gacl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.gacl.dao.AdminMapper;
import com.weixin.gacl.domain.Admin;
import com.weixin.gacl.servece.AdminService;

/**
 * @author gacl
 * ʹ��@Serviceע�⽫UserServiceImpl���עΪһ��service
 * service��id��userService
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService{

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
}
