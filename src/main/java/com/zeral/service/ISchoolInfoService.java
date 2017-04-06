package com.zeral.service;

import java.util.List;

import com.zeral.po.SchoolInfo;

public interface ISchoolInfoService extends IBaseService<SchoolInfo> {
	/**
	 * @Description 获取院信息，用于初始化操作
	 * @return
	 * @author ZeralZhang
	 * @date 2016年9月11日
	 */
	public List<SchoolInfo> findColleges();
	
	/**
	 * @Description 通过院id查找系信息
	 * @param pid
	 * @return
	 * @author ZeralZhang
	 * @date 2016年9月11日
	 */
	public List<SchoolInfo> findByCollegeId(String pid);
	
	/**
	 * 根据code查找学院信息
	 * @param code
	 * @return
	 */
	public SchoolInfo findByCode(String code);
}
