/**
 * @Project Name:weixin
 * @File Name:MatchManagerImpl.java
 * @Package Name:com.weixin.gacl.manager
 * @author zhanggd
 * @Date:2016年5月19日下午10:00:17
 */

package com.weixin.gacl.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static Logger log = LoggerFactory.getLogger(MatchManagerImpl.class);
	
	@Autowired
    private MatchMapper MatchMapper;

	@Override
	public boolean addMatch(String id,String name,String todo,String url,String group) {
		log.debug("进入addMatch(id={},name={},todo="+todo+",url="+url+",group="+group+")",id, name);
		Match match = new Match();
		match.setMatchId(id);
		match.setMatchName(name);
		match.setMatchTodo(todo);
		match.setMatchUrl(url);
		match.setMatchGroup(group);
		int result = MatchMapper.insert(match);
		if(result<=0){
			log.debug("离开addMatch():false");
			return false;
		}
		log.debug("离开addMatch():true");
		return true;
	}

	@Override
	public Match queryMatch(String matchId) {
		log.debug("进入queryMatch()");
		log.debug("离开queryMatch()");
		return MatchMapper.selectByPrimaryKey(matchId);
	}

	@Override
	public Match[] queryAll() {
		log.debug("进入queryAll()");
		Match[] match = MatchMapper.findAll();
		if(match != null && match.length > 0){
			log.debug("离开queryAll()");
			return match;
		}
		log.debug("离开queryAll():null");
		return null;
	}

	@Override
	public boolean delMatch(String matchId) {
		log.debug("进入delMatch(matchId={})",matchId);
		int result = MatchMapper.deleteByPrimaryKey(matchId);
		if(result <=0 ){
			log.debug("离开delMatch():false");
			return false;
		}
		log.debug("离开delMatch():true");
		return true;
	}

	@Override
	public boolean updateMatch(String id, String name, String todo, String url,
			String group) {
		log.debug("进入updateMatch(id={},name={},todo="+todo+",url="+url+",group="+group+")",id, name);
		Match match = new Match();
		match.setMatchId(id);
		match.setMatchName(name);
		match.setMatchTodo(todo);
		match.setMatchUrl(url);
		match.setMatchGroup(group);
		int result = MatchMapper.updateByPrimaryKey(match);
		if(result<=0){
			log.debug("updateMatch离开():false");
			return false;
		}
		log.debug("updateMatch离开():true");
		return true;
	}

}
