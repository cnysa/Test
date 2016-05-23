/**
 * @Project Name:weixin
 * @File Name:MatchManagerImpl.java
 * @Package Name:com.weixin.gacl.manager
 * @author zhanggd
 * @Date:2016��5��19������10:00:17
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
 * @Description: TODO(����)
 * @author zhanggd
 * @date 2016��5��19�� ����10:00:17
 */
@Service("matchManagerImpl")
public class MatchManagerImpl implements MatchManager{
	
	private static Logger log = LoggerFactory.getLogger(MatchManagerImpl.class);
	
	@Autowired
    private MatchMapper MatchMapper;

	@Override
	public boolean addMatch(String id,String name,String todo,String url,String group) {
		log.debug("����addMatch(id={},name={},todo="+todo+",url="+url+",group="+group+")",id, name);
		Match match = new Match();
		match.setMatchId(id);
		match.setMatchName(name);
		match.setMatchTodo(todo);
		match.setMatchUrl(url);
		match.setMatchGroup(group);
		int result = MatchMapper.insert(match);
		if(result<=0){
			log.debug("�뿪addMatch():false");
			return false;
		}
		log.debug("�뿪addMatch():true");
		return true;
	}

	@Override
	public Match queryMatch(String matchId) {
		log.debug("����queryMatch()");
		log.debug("�뿪queryMatch()");
		return MatchMapper.selectByPrimaryKey(matchId);
	}

	@Override
	public Match[] queryAll() {
		log.debug("����queryAll()");
		Match[] match = MatchMapper.findAll();
		if(match != null && match.length > 0){
			log.debug("�뿪queryAll()");
			return match;
		}
		log.debug("�뿪queryAll():null");
		return null;
	}

	@Override
	public boolean delMatch(String matchId) {
		log.debug("����delMatch(matchId={})",matchId);
		int result = MatchMapper.deleteByPrimaryKey(matchId);
		if(result <=0 ){
			log.debug("�뿪delMatch():false");
			return false;
		}
		log.debug("�뿪delMatch():true");
		return true;
	}

	@Override
	public boolean updateMatch(String id, String name, String todo, String url,
			String group) {
		log.debug("����updateMatch(id={},name={},todo="+todo+",url="+url+",group="+group+")",id, name);
		Match match = new Match();
		match.setMatchId(id);
		match.setMatchName(name);
		match.setMatchTodo(todo);
		match.setMatchUrl(url);
		match.setMatchGroup(group);
		int result = MatchMapper.updateByPrimaryKey(match);
		if(result<=0){
			log.debug("updateMatch�뿪():false");
			return false;
		}
		log.debug("updateMatch�뿪():true");
		return true;
	}

}
