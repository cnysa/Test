/**
 * @Project Name:weixin
 * @File Name:UserService.java
 * @Package Name:com.weixin.gacl.servece
 * @author zhanggd
 * @Date:2016年5月14日下午5:07:20
 */

package com.weixin.gacl.manager.interfaces;

import com.weixin.gacl.mapping.beans.User;

/**
 * @ClassName: UserService
 * @Description: TODO(用户操作实现类)
 * @author zhanggd
 * @date 2016年5月14日 下午5:07:20
 */
public interface UserManager {

	void addUser(User user);
	
	User getUser(String wxUserId);
}
