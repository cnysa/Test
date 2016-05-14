/**
 * @Project Name:weixin
 * @File Name:AdminMapper.java
 * @Package Name:com.weixin.gacl.dao
 * @author zhanggd
 * @Date:2016��5��14������4:58:04
 */
package com.weixin.gacl.mapping.dao;

import com.weixin.gacl.mapping.beans.Admin;

/**
 * 
 * @ClassName: AdminMapper
 * @Description: TODO(����Աӳ��ӿ�)
 * @author zhanggd
 * @date 2016��5��14�� ����5:00:57
 */
public interface AdminMapper {
    int deleteByPrimaryKey(String username);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
}
