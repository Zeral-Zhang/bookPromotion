package com.zeral.service;

import java.util.List;

import com.zeral.po.Regions;

public interface IRegionService extends IBaseService<Regions> {
	/**
	 * 查找省份信息，用于初始化级联查询
	 * @return
	 */
	public List<Regions> findProvince();
	
	/**
	 * 根据省份id查找城市信息
	 * @return
	 */
	public List<Regions> findCitys(Integer fcode);

	/**
	 * 根据code查找城市名称
	 * @param code
	 * @return
	 */
	String findNameFromCode(String code);
}
