/**
 * @Project Name:weixin
 * @File Name:NowVoteManager.java
 * @Package Name:com.weixin.gacl.manager.interfaces
 * @author zhanggd
 * @Date:2016��5��21������11:48:28
 */

package com.weixin.gacl.manager.interfaces;

import com.weixin.gacl.mapping.beans.Now;
import com.weixin.gacl.mapping.beans.Vote;

/**
 * @ClassName: NowVoteManager
 * @Description: TODO(����)
 * @author zhanggd
 * @date 2016��5��21�� ����11:48:28
 */
public interface NowVoteManager {
	
	Now queryNow();

	boolean addNow(String nowType, String nowName, String nowTodo,String nowNumbers, String nowStartTime, String nowEndTime);

	int queryOnlyVote(String name);
	
	Vote[] queryForVote(String id);
	
	boolean addVote(String userid,String date,String numbers);
}
