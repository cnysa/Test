/**
 * @Project Name:weixin
 * @File Name:MatchManager.java
 * @Package Name:com.weixin.gacl.manager.interfaces
 * @author zhanggd
 * @Date:2016��5��19������9:58:13
 */

package com.weixin.gacl.manager.interfaces;

import com.weixin.gacl.mapping.beans.Match;

/**
 * @ClassName: MatchManager
 * @Description: TODO(����)
 * @author zhanggd
 * @date 2016��5��19�� ����9:58:13
 */
public interface MatchManager {

	boolean addMatch(String id,String name,String todo,String url,String group);
	
	boolean delMatch(String matchId);
	
	boolean updateMatch(String id,String name,String todo,String url,String group);
	
	Match queryMatch(String matchId);
	
	Match[] queryAll();
}
