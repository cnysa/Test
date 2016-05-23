package com.weixin.gacl.mapping.dao;

import com.weixin.gacl.mapping.beans.Vote;

public interface VoteMapper {
    int deleteByPrimaryKey(String voteId);

    int insert(Vote record);

    int insertSelective(Vote record);

    Vote selectByPrimaryKey(String voteId);

    int updateByPrimaryKeySelective(Vote record);

    int updateByPrimaryKey(Vote record);
    
    int findOnlyAll(String voteUserNumber);
    
    Vote[] findAll();
    
    Vote[] findForId(String voteUserId);
}