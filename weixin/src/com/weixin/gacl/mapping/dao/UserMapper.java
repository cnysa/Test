/**
 * @Project Name:weixin
 * @File Name:UserMapper.java
 * @Package Name:com.weixin.gacl.dao
 * @author zhanggd
 * @Date:2016年5月14日下午4:58:04
 */

package com.weixin.gacl.mapping.dao;

import com.weixin.gacl.mapping.beans.User;

/**
 * @ClassName: UserMapper1
 * @Description: TODO(用户映射接口)
 * @author zhanggd
 * @date 2016年5月14日 下午4:58:04
 */
public interface UserMapper {
    int deleteByPrimaryKey(String wxUserId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String wxUserId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
