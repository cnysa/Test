/**
 * @Project Name:weixin
 * @File Name:TelManagerImpl.java
 * @Package Name:com.weixin.gacl.manager
 * @author zhanggd
 * @Date:2016年5月17日下午5:19:28
 */

package com.weixin.gacl.manager;

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
	
	@Autowired
    private TelMapper telMapper;

	@Override
	public boolean addTel(String telName, String telNum1, String telNum2) {
		Tel tel = new Tel();
		tel.setTelName(telName);
		tel.setTelNum1(telNum1);
		tel.setTelNum2(telNum2);
		int result = telMapper.insert(tel);
		if(result<=0){
			return false;
		}
		return true;
	}

	@Override
	public boolean delTel(String telName) {
		int result = telMapper.deleteByPrimaryKey(telName);
		if(result <=0 ){
			return false;
		}
		return true;
	}

	@Override
	public boolean update(String telName, String telNum1, String telNum2) {
		Tel tel = new Tel();
		tel.setTelName(telName);
		tel.setTelNum1(telNum1);
		tel.setTelNum2(telNum2);
		int result = telMapper.updateByPrimaryKey(tel);
		if(result <=0 ){
			return false;
		}
		return true;
	}

	@Override
	public Tel query(String telName) {
		return telMapper.selectByPrimaryKey(telName);
	}

	@Override
	public Tel[] queryAll() {
		return telMapper.findAll();
	}

	@Override
	public Tel[] queryMh(String telName) {
		return telMapper.findMh(telName);
	}

}
