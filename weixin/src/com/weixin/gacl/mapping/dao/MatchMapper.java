package com.weixin.gacl.mapping.dao;

import com.weixin.gacl.mapping.beans.Match;

public interface MatchMapper {
    int deleteByPrimaryKey(String matchId);

    int insert(Match record);

    int insertSelective(Match record);

    Match selectByPrimaryKey(String matchId);

    int updateByPrimaryKeySelective(Match record);

    int updateByPrimaryKey(Match record);
}