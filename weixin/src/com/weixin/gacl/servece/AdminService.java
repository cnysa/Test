package com.weixin.gacl.servece;

import com.weixin.gacl.domain.Admin;

public interface AdminService {
	/**
     * 添加用户
     * @param user
     */
    void addUser(Admin user);
    
    /**
     * 根据用户id获取用户
     * @param userId
     * @return
     */
    Admin getAdminByUsername(String username);
}
