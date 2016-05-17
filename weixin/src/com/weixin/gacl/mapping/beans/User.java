/**
 * @Project Name:weixin
 * @File Name:User.java
 * @Package Name:com.weixin.gacl.domain
 * @author zhanggd
 * @Date:2016年5月14日下午4:59:06
 */

package com.weixin.gacl.mapping.beans;
/**
 * @ClassName: User1
 * @Description: TODO(用户类，对应数据库的wx_user表)
 * @author zhanggd
 * @date 2016年5月14日 下午4:59:06
 */
public class User {
    private String wxUserId;

    private String wxUserXh;

    private String wxUserMm;

    public String getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(String wxUserId) {
        this.wxUserId = wxUserId == null ? null : wxUserId.trim();
    }

    public String getWxUserXh() {
        return wxUserXh;
    }

    public void setWxUserXh(String wxUserXh) {
        this.wxUserXh = wxUserXh == null ? null : wxUserXh.trim();
    }

    public String getWxUserMm() {
        return wxUserMm;
    }

    public void setWxUserMm(String wxUserMm) {
        this.wxUserMm = wxUserMm == null ? null : wxUserMm.trim();
    }
}
