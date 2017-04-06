package com.zeral.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeral.dao.BaseDao;
import com.zeral.dao.SchoolInfoDao;
import com.zeral.po.SchoolInfo;
import com.zeral.service.ISchoolInfoService;

@Service
public class SchoolInfoServiceImpl extends BaseServiceImpl<SchoolInfo> implements ISchoolInfoService {

	@Autowired
	private SchoolInfoDao schoolInfoDao;
	
	@Override
	public List<SchoolInfo> findColleges() {
		return schoolInfoDao.findByLevel(0);
	}

	@Override
	public List<SchoolInfo> findByCollegeId(String pid) {
		return schoolInfoDao.findByPCode(pid);
	}

	@Override
	public SchoolInfo findByCode(String code) {
		return schoolInfoDao.findByCode(code);
	}
	
	@Override
	public BaseDao<SchoolInfo, String> getDao() {
		return schoolInfoDao;
	}
}
