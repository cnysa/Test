package com.weixin.server.util;

import java.util.HashMap;
import java.util.Map;

public enum EnumManager {
    /*user client err*/
	MENU_XYJJ("11","ѧԺ���"),
    MENU_XYXW("12","ѧԺ����"),
    MENU_JXKY("13","��ѧ����"),
    MENU_GGTZ("14","����֪ͨ"),
    MENU_Xl("21","У��"),
    MENU_ZYJS("22","רҵ����"),
    MENU_CJCX("23","�ɼ���ѯ"),
    MENU_TSCX("24","ͼ���ѯ"),
    MENU_LSLXFS("25","���ֵ绰"),
    MENU_BBQ("31","���ǽ"),
    MENU_DQHD("32","��ǰͶƱ"),
    MENU_SYBZ("33","ʹ�ð���"),
    
    NOW_VOTE_TYPE_ONLY("0","ÿ���˺�һƱ"),
    NOW_VOTE_TYPE_DAY_ONLY("1","ÿ���˺�ÿ��һƱ"),
    
    ;
    /**
     * ��Ӧ��
     */
    private String respCode = null;
    /**
     * ��Ӧ��Ϣ
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
     * @param respCode Ҫ���õ� respCode 
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
     * @param respMsg Ҫ���õ� respMsg 
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
	 * @Description: ת��ΪMap���� 
	 */
	public static Map<String, String> toMap() {
		Map<String, String> enumDataMap = new HashMap<String, String>();
		for (EnumManager type : values()) {
			enumDataMap.put(type.getRespCode(), type.getRespMsg());
		}
		return enumDataMap;
	}
}
