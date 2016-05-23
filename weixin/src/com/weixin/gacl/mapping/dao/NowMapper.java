package com.weixin.gacl.mapping.dao;

import com.weixin.gacl.mapping.beans.Now;

public interface NowMapper {
    int deleteByPrimaryKey(String nowId);

    int insert(Now record);

    int insertSelective(Now record);

    Now selectByPrimaryKey(String nowId);

    int updateByPrimaryKeySelective(Now record);

    int updateByPrimaryKey(Now record);
    
    Now[] findAll();
}