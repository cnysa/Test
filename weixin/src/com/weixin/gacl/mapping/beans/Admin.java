/**
 * @Project Name:weixin
 * @File Name:Admin.java
 * @Package Name:com.weixin.gacl.domain
 * @author zhanggd
 * @Date:2016��5��14������4:59:06
 */

package com.weixin.gacl.mapping.beans;

/**
 * 
 * @ClassName: Admin
 * @Description: TODO(����Ա�࣬��Ӧ���ݿ��wx_admin��)
 * @author zhanggd
 * @date 2016��5��14�� ����5:00:12
 */
public class Admin {
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}