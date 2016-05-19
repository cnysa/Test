package com.weixin.gacl.mapping.dao;

import com.weixin.gacl.mapping.beans.Tel;

public interface TelMapper {
    int deleteByPrimaryKey(String telName);

    int insert(Tel record);

    int insertSelective(Tel record);

    Tel selectByPrimaryKey(String telName);

    int updateByPrimaryKeySelective(Tel record);

    int updateByPrimaryKey(Tel record);
    
    Tel[] findAll();
    
    Tel[] findMh(String telName);
}