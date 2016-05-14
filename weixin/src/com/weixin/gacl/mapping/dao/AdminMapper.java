/**
 * @Project Name:weixin
 * @File Name:AdminMapper.java
 * @Package Name:com.weixin.gacl.dao
 * @author zhanggd
 * @Date:2016年5月14日下午4:58:04
 */
package com.weixin.gacl.mapping.dao;

import com.weixin.gacl.mapping.beans.Admin;

/**
 * 
 * @ClassName: AdminMapper
 * @Description: TODO(管理员映射接口)
 * @author zhanggd
 * @date 2016年5月14日 下午5:00:57
 */
public interface AdminMapper {
    int deleteByPrimaryKey(String username);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
}
