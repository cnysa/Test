package com.weixin.gacl.manager.interfaces;

import com.weixin.gacl.mapping.beans.Admin;

/**
 * 
 * @ClassName: AdminService
 * @Description: TODO(����Ա�����ӿ�)
 * @author zhanggd
 * @date 2016��5��14�� ����5:01:47
 */
public interface AdminManager {
	/**
     * ����û�
     * @param user
     */
    void addUser(Admin user);
    
    Admin verifLofin(String username);
    /**
     * �����û�id��ȡ�û�
     * @param userId
     * @return
     */
    Admin getAdminByUsername(String username);
}
