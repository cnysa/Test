/**
 * @Project Name:weixin
 * @File Name:NowVoteManagerImpl.java
 * @Package Name:com.weixin.gacl.manager
 * @author zhanggd
 * @Date:2016��5��21������11:51:38
 */

package com.weixin.gacl.manager;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.gacl.manager.interfaces.NowVoteManager;
import com.weixin.gacl.mapping.beans.Now;
import com.weixin.gacl.mapping.beans.Vote;
import com.weixin.gacl.mapping.dao.NowMapper;
import com.weixin.gacl.mapping.dao.VoteMapper;

/**
 * @ClassName: NowVoteManagerImpl
 * @Description: TODO(����)
 * @author zhanggd
 * @date 2016��5��21�� ����11:51:38
 */
@Service("nowVoteManagerImpl")
public class NowVoteManagerImpl implements NowVoteManager{

	private static Logger log = LoggerFactory.getLogger(NowVoteManagerImpl.class);
	
	@Autowired
    private NowMapper nowMapper;
	
	@Autowired
    private VoteMapper voteMapper;

	/**
	 * 
	 * @Title: addNow
	 * @Description:TODO(ÿ����һ��ͶƱ����ԭ����ͶƱ��¼ɾ��,�������vote��)
	 * @param nowType
	 * @param nowName
	 * @param nowTodo
	 * @param nowStartTime
	 * @param nowEndTime
	 * @return
	 * @see com.weixin.gacl.manager.interfaces.NowVoteManager#addNow(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean addNow(String nowType, String nowName,
			String nowTodo,String nowNumbers, String nowStartTime, String nowEndTime) {
		log.debug("����addNow(nowType="+nowType+",nowName="+nowName+",nowTodo="+nowTodo+",nowNumbers="+nowNumbers+",nowStartTime="+nowStartTime+",nowEndTime="+nowEndTime+")");
		
		Now[] nows = nowMapper.findAll();
		if(nows != null && nows.length >0){
			for(int i=0; i<nows.length; i++){
				nowMapper.deleteByPrimaryKey(nows[i].getNowId());
			}
		}
		
		Vote[] votes = voteMapper.findAll();
		if(votes != null && votes.length >0){
			for(int i=0; i<votes.length; i++){
				voteMapper.deleteByPrimaryKey(votes[i].getVoteId());
			}
		}
		
		UUID uuid = UUID.randomUUID();
		String nowid = uuid.toString().substring(0, 8);
		Now now = new Now();
		now.setNowId(nowid);
		now.setNowName(nowName);
		now.setNowTodo(nowTodo);
		now.setNowType(nowType);
		now.setNowStartTime(nowStartTime);
		now.setNowEndTime(nowEndTime);
		now.setNowNumbers(nowNumbers);
		int result = nowMapper.insert(now);
		if(result<=0){
			log.debug("�뿪addNow():false");
			return false;
		}
		log.debug("�뿪addNow():true");
		return true;
	}

	@Override
	public Now queryNow() {
		log.debug("����queryNow()");
		Now[] nows = nowMapper.findAll();
		if(nows != null && nows.length >0 ){
			log.debug("�뿪queryNow()");
			return nows[0];
		}
		log.debug("�뿪queryNow():null");
		return null;
	}

	@Override
	public int queryOnlyVote(String name) {
		log.debug("����queryOnlyVote()");
		log.debug("�뿪queryOnlyVote()");
		return voteMapper.findOnlyAll(name);
	}

	@Override
	public Vote[] queryForVote(String id) {
		log.debug("����queryForVote()");
		Vote[] votes = voteMapper.findForId(id);
		if(votes != null && votes.length > 0){
			log.debug("�뿪queryForVote()");
			return votes;
		}
		log.debug("�뿪queryForVote():null");
		return null;
	}

	@Override
	public boolean addVote(String userid, String date, String numbers) {
		log.debug("����addVote(userid="+userid+",  date="+date+",  numbers="+numbers+")");
		UUID uuid = UUID.randomUUID();
		String voteid = uuid.toString().substring(0, 8);
		while(voteMapper.selectByPrimaryKey(voteid) != null){
			voteid = uuid.toString().substring(0, 8);
		}
		Vote vote = new Vote();
		vote.setVoteId(voteid);
		vote.setVoteUserId(userid);
		vote.setVoteUserTime(date);
		vote.setVoteUserNumber(numbers);
		int result = voteMapper.insert(vote);
		if(result<=0){
			log.debug("�뿪addVote():false");
			return false;
		}
		log.debug("�뿪addVote():true");
		return true;
	}

}
