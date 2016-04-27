package com.weixin.gacl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.gacl.dao.AdminMapper;
import com.weixin.gacl.domain.Admin;
import com.weixin.gacl.servece.AdminService;

/**
 * @author gacl
 * 使用@Service注解将UserServiceImpl类标注为一个service
 * service的id是userService
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService{

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
}
