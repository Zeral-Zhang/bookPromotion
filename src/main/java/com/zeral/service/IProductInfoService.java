package com.zeral.service;

import java.util.List;

import com.zeral.bean.PageBean;
import com.zeral.po.ProductInfo;

public interface IProductInfoService extends IBaseService<ProductInfo> {
	
	public int findMaxPage(int rows);

	public ProductInfo findDetail(String productId);
	
	public void update(ProductInfo productInfo);

	/**
	 * 通过商品发布用户查找商品分页信息
	 */
	public List<ProductInfo> findByUserId(PageBean pageBean, String userId);

	/**
	 * 通过商品名称模糊查询
	 * @param pageBean
	 * @param name
	 * @return
	 */
	List<ProductInfo> findByNameLike(PageBean pageBean, String name);

	/**
	 * 根据商品类型和名称模糊查询
	 * @param pageBean
	 * @param name
	 * @return
	 */
	List<ProductInfo> findByTypeAndNameLike(PageBean pageBean, String productTypeId, String name);

	/**
	 * 根据商品类型查询商品列表信息
	 * @param pageBean
	 * @param productTypeId
	 * @return
	 */
	List<ProductInfo> findByType(PageBean pageBean, String productTypeId);

	/**
	 * 根据商品发布用户的学院查找商品列表信息
	 * @param pageBean
	 * @param schoolInfoId
	 * @return
	 */
	public List<ProductInfo> findByUserSchoolInfoId(PageBean pageBean, String schoolInfoId);
}
