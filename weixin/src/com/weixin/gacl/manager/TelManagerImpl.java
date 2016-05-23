/**
 * @Project Name:weixin
 * @File Name:TelManagerImpl.java
 * @Package Name:com.weixin.gacl.manager
 * @author zhanggd
 * @Date:2016年5月17日下午5:19:28
 */

package com.weixin.gacl.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.gacl.manager.interfaces.TelManager;
import com.weixin.gacl.mapping.beans.Tel;
import com.weixin.gacl.mapping.dao.TelMapper;

/**
 * @ClassName: TelManagerImpl
 * @Description: TODO(描述)
 * @author zhanggd
 * @date 2016年5月17日 下午5:19:28
 */
@Service("telManagerImpl")
public class TelManagerImpl implements TelManager{
	
	private static Logger log = LoggerFactory.getLogger(TelManagerImpl.class);
	
	@Autowired
    private TelMapper telMapper;

	@Override
	public boolean addTel(String telName, String telNum1, String telNum2) {
		log.debug("进入addTel(telName="+telName+",  telNum1="+telNum1+",  telNum2="+telNum2+")");
		Tel tel = new Tel();
		tel.setTelName(telName);
		tel.setTelNum1(telNum1);
		tel.setTelNum2(telNum2);
		int result = telMapper.insert(tel);
		if(result<=0){
			log.debug("离开addTel():false");
			return false;
		}
		log.debug("离开addTel():true");
		return true;
	}

	@Override
	public boolean delTel(String telName) {
		log.debug("进入delTel(telName={})",telName);
		int result = telMapper.deleteByPrimaryKey(telName);
		if(result <=0 ){
			log.debug("离开delTel():false");
			return false;
		}
		log.debug("离开delTel():true;");
		return true;
	}

	@Override
	public boolean update(String telName, String telNum1, String telNum2) {
		log.debug("进入update(telName="+telName+", telNum1="+telNum1+", telNum2="+telNum2+")");
		Tel tel = new Tel();
		tel.setTelName(telName);
		tel.setTelNum1(telNum1);
		tel.setTelNum2(telNum2);
		int result = telMapper.updateByPrimaryKey(tel);
		if(result <=0 ){
			log.debug("离开update():false");
			return false;
		}
		log.debug("离开update():true");
		return true;
	}

	@Override
	public Tel query(String telName) {
		log.debug("进入query(telName={})",telName);
		log.debug("离开query()");
		return telMapper.selectByPrimaryKey(telName);
	}

	@Override
	public Tel[] queryAll() {
		log.debug("进入queryAll()");
		Tel[] tels = telMapper.findAll();
		if(tels != null && tels.length>0){
			log.debug("离开queryAll()");
			return tels;
		}
		log.debug("离开queryAll():null");
		return null;
	}

	@Override
	public Tel[] queryMh(String telName) {
		log.debug("进入queryMh(telName={})",telName);
		Tel[] tels = telMapper.findMh(telName);
		if(tels != null && tels.length>0){
			return tels;
		}
		log.debug("离开queryMh():null");
		return null;
	}

}
