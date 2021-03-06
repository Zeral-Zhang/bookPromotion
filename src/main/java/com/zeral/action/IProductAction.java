package com.zeral.action;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.zeral.bean.PageBean;
import com.zeral.po.ProductInfo;

public interface IProductAction {
	/**
	 * 添加商品，添加成功返回商品列表界面
	 * @param productInfo 表单的商品信息
	 * @return
	 */
	public String addProduct(ProductInfo productInfo);
	
	/**
	 * 删除商品
	 * @param productId 商品id
	 */
	public void deleProduct(String productId, HttpServletResponse response);
	
	/**
	 * 初始化商品类别信息
	 * 
	 */
	public void initProductType(HttpServletResponse response);
	
	/**
	 * 加载商品列表信息
	 * @param pageBean 分页对象
	 * @param search 查询字段
	 * @return
	 */
	public String toProductList(PageBean pageBean, String search, Model model);
	
	/**
	 * 查找商品详细信息
	 * @return
	 */
	public String toProductDetail(String productId, Model model);

	/**
	 * 查找某个类别的商品列表
	 * @param pageBean 分页对象
	 * @param search 查询字段
	 * @param productTypeId 商品类别id
	 * @return
	 */
	public String toProductCateList(PageBean pageBean, String productTypeId, String search, Model model);

	/**
	 * 到商品添加页面
	 * @param response
	 * @return
	 */
	String toProductAdd(HttpServletResponse response);
}
