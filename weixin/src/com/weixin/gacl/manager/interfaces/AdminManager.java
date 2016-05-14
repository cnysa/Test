package com.weixin.gacl.manager.interfaces;

import com.weixin.gacl.mapping.beans.Admin;

/**
 * 
 * @ClassName: AdminService
 * @Description: TODO(管理员操作接口)
 * @author zhanggd
 * @date 2016年5月14日 下午5:01:47
 */
public interface AdminManager {
	/**
     * 添加用户
     * @param user
     */
    void addUser(Admin user);
    
    Admin verifLofin(String username);
    /**
     * 根据用户id获取用户
     * @param userId
     * @return
     */
    Admin getAdminByUsername(String username);
}
