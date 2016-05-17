package com.weixin.gacl.mapping.dao;

import com.weixin.gacl.mapping.beans.Tel;

public interface TelMapper {
    int insert(Tel record);

    int insertSelective(Tel record);
}