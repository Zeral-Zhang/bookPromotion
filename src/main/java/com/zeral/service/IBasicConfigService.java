package com.zeral.service;

import java.util.List;

import com.zeral.bean.AccessToken;
import com.zeral.po.BasicConfig;

/**
 * 系统配置信息接口类
 * 
 */
public interface IBasicConfigService {
	
	/**
	 * 保存一条系统配置信息
	 * @param basicConfig 待保存对象
	 */
	void save(BasicConfig basicConfig);
	
	/**
	 * 根据code删除一条数据
	 * @param basicConfigId code值
	 */
	void delete(String basicConfigId);
	
	/**
	 * 根据basicConfigId和unitId删除数据
	 * @param basicConfigId code值
	 * @param unitId 单位id
	 */
	void deleteIdAndUnitId(String basicConfigId, String unitId);
	
	/**
	 * 更新系统配置信息
	 * @param basicConfig 待更新对象
	 */
	void update(BasicConfig basicConfig);
	
	/**
	 * 保存或更新系统配置信息
	 * @param basicConfig 待操作对象
	 */
	void saveOrUpdate(BasicConfig basicConfig);
	
	/**
	 * 根据id查询数据
	 * @param id id值
	 * @return 查询出的对象
	 */
	BasicConfig findById(String id);
	
	/**
	 * 根据code和单位id查询
	 * @param code code值
	 * @param unitId 单位id
	 * @return 查询出的对象
	 */
	BasicConfig findByCodeAndUnit(String code, String unitId);
	
	/**
	 * 根据code查询一条数据的value值
	 * @param basicConfigId code值
	 * @return value值
	 */
	String findValueById(String basicConfigId);
	/**
	 * 是否存在
	 * @param basicConfigId code值
	 * @return 是否存在  true存在  false不存在
	 */
	boolean isExist(String basicConfigId);

	/**
	 * 根据code查询
	 * @param code code值
	 * @return 查询出的对象
	 */
	BasicConfig findByCode(String code);
	
	/**
	 * 查找平台设置
	 * @return 数据集合
	 */
	List<BasicConfig> findPlatformSet();

	/**
	 * 保存平台设置
	 * @param basicConfigs 配置信息数据集合
	 * @param unitId 单位id
	 */
	void savePlatformSet(List<BasicConfig> basicConfigs, String unitId);
	
	/**
	 * 根据code取得所有BasicConfig信息
	 * 注：适合一个code对应多个值的情况
	 * 
	 * @param code code值
	 * @return 配置信息数据集合
	 */
	List<BasicConfig> findByCodeAll(String code);
	
	/** 
	 * 获取有效的accessToken
	 * @return
	 */
	AccessToken getValidAccessToken();
	
	/** 
	 * 获取有效的JsapiTicket
	 * @return
	 */
	AccessToken getValidJsapiTicket();
}
