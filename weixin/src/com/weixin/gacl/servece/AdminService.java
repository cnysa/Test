package com.weixin.gacl.servece;

import com.weixin.gacl.domain.Admin;

public interface AdminService {
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
