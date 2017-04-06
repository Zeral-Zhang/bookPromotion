package com.zeral.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeral.dao.BaseDao;
import com.zeral.dao.RegionsDao;
import com.zeral.po.Regions;
import com.zeral.service.IRegionService;

@Service("RegionBizImpl")
public class RegionServiceImpl extends BaseServiceImpl<Regions> implements IRegionService {
	@Autowired
	private RegionsDao regionsDao;

	@Override
	public List<Regions> findProvince() {
		return regionsDao.findByLevel(0);
	}

	@Override
	public List<Regions> findCitys(Integer fcode) {
		return regionsDao.findByPCode(fcode);
	}

	@Override
	public BaseDao<Regions, String> getDao() {
		return regionsDao;
	}

	@Override
	public String findNameFromCode(String code) {
		Integer codeInt = Integer.parseInt(code);
		List<Regions> provincelst = regionsDao.findByCode(codeInt);
		String value = null;
		if(provincelst.size() > 0) {
			value = provincelst.get(0).getName();
		}
		return value;
	}
}
