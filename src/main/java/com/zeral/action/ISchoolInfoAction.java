package com.zeral.action;

/**
 * 院系信息action接口
 * @author ZeralZhang
 *
 */
public interface ISchoolInfoAction {
	/**
	 * 根源院code加载系数据
	 */
	public void loadDepartments();

	/**
	 *加载发现栏目 
	 * @return 
	 */
	public String toDiscovery();
}
