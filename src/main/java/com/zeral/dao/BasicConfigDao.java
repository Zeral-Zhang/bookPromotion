package com.zeral.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zeral.bean.AccessToken;
import com.zeral.po.BasicConfig;


/**
 * BasicConfigDAO类
 * 
 * @author Zeral
 * @date 2016年7月7日
 */
@Repository("BasicConfigDAO")
public class BasicConfigDao extends BaseDao<BasicConfig, String> {
	/**
	 * 根据basicConfigId查询一条记录出来
	 * 
	 * @author Say
	 * @date 2015年7月7日
	 * @param basicConfigId
	 * @return
	 */
	public BasicConfig findByBasicConfigId(String basicConfigId) {
		String hql = "from BasicConfig a where a.basicConfigId = ?";
		return (BasicConfig) getCurrentSession().createQuery(hql).setParameter(0, basicConfigId).uniqueResult();
	}

	/**
	 * 根据basicConfigId和unitId删除数据
	 * @author Say
	 * @date 2015年7月9日
	 * @param basicConfigId
	 * @param unitId
	 */
	public void deleteIdAndUnitId(String basicConfigId, String unitId) {
		String hql = "delete from BasicConfig where basicConfigId = ? and unitId = ?";
		executeBulk(hql, basicConfigId, unitId);
	}
	
	/**
	 * 查询平台参数
	 * @author Melody
	 * @date 2015-10-15
	 * @return
	 *
	 */
	public List<BasicConfig> findPlatformSet() {
		String hql = "from BasicConfig bc where bc.basicConfigId like ?";
		return findByHQL(hql, "max%Size");
	}

	public AccessToken getToken(String basicConfigId) {
		AccessToken accessToken = null;
		BasicConfig token = findByBasicConfigId(basicConfigId);
		if(null != token) {
			accessToken = new AccessToken();
			accessToken.setToken(token.getValue());
			accessToken.setExpiresIn(Long.parseLong(token.getName()));
		}
		return accessToken;
	}
	
	public void setToken(AccessToken token, String basicConfigId) throws Exception {
		if(null != token) {
			BasicConfig config = findByBasicConfigId(basicConfigId);
			if(null == config) {
				config = new BasicConfig(basicConfigId, String.valueOf(System.currentTimeMillis()/1000+token.getExpiresIn()), token.getToken());
			} else {
				config.setName(String.valueOf(System.currentTimeMillis()/1000+token.getExpiresIn()));
				config.setValue(token.getToken());
			}
			saveOrUpdate(config);
		}
	}

}
