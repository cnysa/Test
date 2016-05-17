/**
 * @Project Name:weixin
 * @File Name:UserMapper.java
 * @Package Name:com.weixin.gacl.dao
 * @author zhanggd
 * @Date:2016��5��14������4:58:04
 */

package com.weixin.gacl.mapping.dao;

import com.weixin.gacl.mapping.beans.User;

/**
 * @ClassName: UserMapper1
 * @Description: TODO(�û�ӳ��ӿ�)
 * @author zhanggd
 * @date 2016��5��14�� ����4:58:04
 */
public interface UserMapper {
    int deleteByPrimaryKey(String wxUserId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String wxUserId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
