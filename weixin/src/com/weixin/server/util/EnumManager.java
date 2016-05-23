package com.weixin.server.util;

import java.util.HashMap;
import java.util.Map;

public enum EnumManager {
    /*user client err*/
	MENU_XYJJ("11","学院简介"),
    MENU_XYXW("12","学院新闻"),
    MENU_JXKY("13","教学科研"),
    MENU_GGTZ("14","公告通知"),
    MENU_Xl("21","校历"),
    MENU_ZYJS("22","专业竞赛"),
    MENU_CJCX("23","成绩查询"),
    MENU_TSCX("24","图书查询"),
    MENU_LSLXFS("25","各种电话"),
    MENU_BBQ("31","表白墙"),
    MENU_DQHD("32","当前投票"),
    MENU_SYBZ("33","使用帮助"),
    
    NOW_VOTE_TYPE_ONLY("0","每个账号一票"),
    NOW_VOTE_TYPE_DAY_ONLY("1","每个账号每天一票"),
    
    ;
    /**
     * 响应码
     */
    private String respCode = null;
    /**
     * 响应信息
     */
    private String respMsg = null;

    private EnumManager(String respCode, String respMsg) {
		this.respCode = respCode;
		this.respMsg = respMsg;
    }
    /** 
     * @return respCode 
     */
    public String getRespCode() {
            return respCode;
    }
    /** 
     * @param respCode 要设置的 respCode 
     */
    public void setRespCode(String respCode) {
            this.respCode = respCode;
    }
    /** 
     * @return respMsg 
     */
    public String getRespMsg() {
            return respMsg;
    }
    /** 
     * @param respMsg 要设置的 respMsg 
     */
    public void setRespMsg(String respMsg) {
            this.respMsg = respMsg;
    }
	/**
	 * 
	 * @Method: getEnumByCode 
	 */
	public static EnumManager getEnumByCode(String respCode) {
		if (null == respCode) {
			return null;
		}
		for (EnumManager type : values()) {
			if (type.getRespCode().equals(respCode.trim()))
				return type;
		}
		return null;
	}

	/**
	 * 
        * @return 
	 * @Method: toMap 
	 * @Description: 转换为Map对象 
	 */
	public static Map<String, String> toMap() {
		Map<String, String> enumDataMap = new HashMap<String, String>();
		for (EnumManager type : values()) {
			enumDataMap.put(type.getRespCode(), type.getRespMsg());
		}
		return enumDataMap;
	}
}
