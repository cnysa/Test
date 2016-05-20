/**
 * @Project Name:weixin
 * @File Name:MatchManagerImpl.java
 * @Package Name:com.weixin.gacl.manager
 * @author zhanggd
 * @Date:2016年5月19日下午10:00:17
 */

package com.weixin.gacl.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.gacl.manager.interfaces.MatchManager;
import com.weixin.gacl.mapping.beans.Match;
import com.weixin.gacl.mapping.dao.MatchMapper;

/**
 * @ClassName: MatchManagerImpl
 * @Description: TODO(描述)
 * @author zhanggd
 * @date 2016年5月19日 下午10:00:17
 */
@Service("matchManagerImpl")
public class MatchManagerImpl implements MatchManager{
	
	@Autowired
    private MatchMapper MatchMapper;

	@Override
	public boolean addMatch(String id,String name,String todo,String url,String group) {
		Match match = new Match();
		match.setMatchId(id);
		match.setMatchName(name);
		match.setMatchTodo(todo);
		match.setMatchUrl(url);
		match.setMatchGroup(group);
		int result = MatchMapper.insert(match);
		if(result<=0){
			return false;
		}
		return true;
	}

	@Override
	public Match queryMatch(String matchId) {
		return MatchMapper.selectByPrimaryKey(matchId);
	}

	@Override
	public Match[] queryAll() {
		return MatchMapper.findAll();
	}

	@Override
	public boolean delMatch(String matchId) {
		int result = MatchMapper.deleteByPrimaryKey(matchId);
		if(result <=0 ){
			return false;
		}
		return true;
	}

	@Override
	public boolean updateMatch(String id, String name, String todo, String url,
			String group) {
		Match match = new Match();
		match.setMatchId(id);
		match.setMatchName(name);
		match.setMatchTodo(todo);
		match.setMatchUrl(url);
		match.setMatchGroup(group);
		int result = MatchMapper.updateByPrimaryKey(match);
		if(result<=0){
			return false;
		}
		return true;
	}

}
