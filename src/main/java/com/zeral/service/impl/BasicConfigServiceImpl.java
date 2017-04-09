package com.zeral.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zeral.dao.BasicConfigDao;
import com.zeral.exception.BaseException;
import com.zeral.po.BasicConfig;
import com.zeral.service.IBasicConfigService;

@Service
public class BasicConfigServiceImpl implements IBasicConfigService {

	@Autowired
	private BasicConfigDao basicConfigDao;
	
	
	@Override
	public void save(BasicConfig basicConfig) {
		basicConfigDao.save(basicConfig);
	}

	@Override
	public void delete(String basicConfigId) {
		basicConfigDao.deleteObject(findByCode(basicConfigId));
	}

	@Override
	public void update(BasicConfig basicConfig) {
		basicConfigDao.update(basicConfig);
	}
	
	@Override
	public void saveOrUpdate(BasicConfig basicConfig) {
		basicConfigDao.saveOrUpdate(basicConfig);
	}

	@Override
	public BasicConfig findById(String id) {
		return basicConfigDao.findById(id);
	}
	
	@Override
	public String findValueById(String basicConfigId) {
		BasicConfig basicConfig = findByCode(basicConfigId);
		if(basicConfig == null){
			throw new BaseException("Not find BasicConfig by id:"+basicConfigId);
		}
		return basicConfig.getValue();
	}
	
	@Override
	public boolean isExist(String basicConfigId) {
		return findByCode(basicConfigId) != null;
	}

	@Override
	public BasicConfig findByCodeAndUnit(String code, String unitId) {
		BasicConfig bc = new BasicConfig();
		bc.setBasicConfigId(code);
		return basicConfigDao.findUnique(bc);
	}

	@Override
	public BasicConfig findByCode(String code) {
		List<BasicConfig> basicConfigs = findByCodeAll(code);
		if (!CollectionUtils.isEmpty(basicConfigs)) {
			return basicConfigs.get(0);
		}
		return null;
	}

	@Override
	public void deleteIdAndUnitId(String basicConfigId, String unitId) {
		basicConfigDao.deleteIdAndUnitId(basicConfigId, unitId);
	}
	
	@Override
	public void savePlatformSet(List<BasicConfig> basicConfigs,String unitId) {
		for (BasicConfig basicConfig : basicConfigs) {
			saveOrUpdate(basicConfig);
		}
	}

	@Override
	public List<BasicConfig> findPlatformSet() {
		return basicConfigDao.findPlatformSet();
	}

	@Override
	public List<BasicConfig> findByCodeAll(String code) {
		BasicConfig bc = new BasicConfig();
		bc.setBasicConfigId(code);
		return basicConfigDao.findByExample(bc);
	}
}
