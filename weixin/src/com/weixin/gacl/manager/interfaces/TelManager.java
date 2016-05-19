/**
 * @Project Name:weixin
 * @File Name:TelManager.java
 * @Package Name:com.weixin.gacl.manager.interfaces
 * @author zhanggd
 * @Date:2016��5��17������5:15:13
 */

package com.weixin.gacl.manager.interfaces;

import com.weixin.gacl.mapping.beans.Tel;

/**
 * @ClassName: TelManager
 * @Description: TODO(����)
 * @author zhanggd
 * @date 2016��5��17�� ����5:15:13
 */
public interface TelManager {

	boolean addTel(String telName,String telNum1, String telNum2);
	
	boolean delTel(String telName);
	
	boolean update(String telName,String telNum1, String telNum2);
	
	Tel query(String telName);
	
	Tel[] queryAll();
	
	Tel[] queryMh(String telName);
}
