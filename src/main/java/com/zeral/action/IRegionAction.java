package com.zeral.action;

import javax.servlet.http.HttpServletResponse;

public interface IRegionAction {
	/**
	 * 初始化省份信息
	 * 
	 */
	public void initProvince(HttpServletResponse response);
	
	/**
	 * 根据省id查询城市信息
	 * 
	 */
	public void loadCitys(HttpServletResponse response);
}
