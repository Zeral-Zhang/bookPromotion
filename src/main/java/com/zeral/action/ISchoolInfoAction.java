package com.zeral.action;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.zeral.bean.PageBean;

/**
 * 院系信息action接口
 * @author ZeralZhang
 *
 */
public interface ISchoolInfoAction {
	/**
	 * 根源院code加载系数据
	 * @param code 学院code
	 */
	public void loadDepartments(String code, HttpServletResponse response);

	/**
	 *加载发现栏目 
	 * @return 
	 */
	public String toDiscovery(Model model);
	
	/**
	 * 初始化某个学院的商品列表
	 * @return
	 */
	public String toSchoolInfoProduct(PageBean pageBean, String schoolInfoId, String search, Model model);

}
