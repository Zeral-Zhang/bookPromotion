package com.zeral.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeral.bean.PageBean;
import com.zeral.constant.BookPromotionConstant;
import com.zeral.dao.BaseDao;
import com.zeral.dao.ProductInfoDao;
import com.zeral.po.ProductInfo;
import com.zeral.po.ProductType;
import com.zeral.service.IProductInfoService;
import com.zeral.service.IProductTypeService;

@Service
public class ProductInfoServiceImpl extends BaseServiceImpl<ProductInfo> implements IProductInfoService {
	private static final Logger log = Logger.getLogger(ProductInfoServiceImpl.class);

	@Autowired
	private ProductInfoDao productInfoDao;
	@Autowired
	private IProductTypeService productTypeService;

	public int findMaxPage(int rows) {
		if (rows < 1)
			rows = 5;
		return productInfoDao.findMaxPage(rows);
	}

	@Override
	public ProductInfo findDetail(String productId) {
		ProductInfo productInfo = findById(productId);
		if (productInfo != null) {
			return productInfo;
		}
		return null;
	}

	@Override
	public void update(ProductInfo productInfo) {
		try {
			// 更新商品类别信息
			ProductType productType = productTypeService.findById(productInfo.getProductType().getProductTypeId());
			productInfo.setProductType(productType);
			super.update(productInfo);
		} catch (Exception e) {
			log.error("update Productinfo "+ productInfo.toString() +" failed", e);
			throw e;
		}
	}

	@Override
	public List<ProductInfo> findByUserId(PageBean pageBean, String userId) {
		String hql = "from ProductInfo info where info.userInfoId = ? and info.state != "+BookPromotionConstant.UNAVAILABLE+" order by info.pbDate desc";
		return productInfoDao.findByHQL(hql, pageBean, userId);
	}

	@Override
	public List<ProductInfo> findByNameLike(PageBean pageBean, String name) {
		String hql = "from ProductInfo info where info.productName like ? and info.state = " + BookPromotionConstant.SALLING;
		return productInfoDao.findByHQL(hql, pageBean, "%" + name + "%");
	}
	
	@Override
	public List<ProductInfo> findByTypeAndNameLike(PageBean pageBean, String productTypeId, String name) {
		String hql = "from ProductInfo info where info.productType.productTypeId = ? and info.productName like ? and info.state = " + BookPromotionConstant.SALLING;
		return productInfoDao.findByHQL(hql, pageBean, productTypeId, "%" + name + "%");
	}
	
	@Override
	public List<ProductInfo> findByType(PageBean pageBean, String productTypeId) {
		String hql = "from ProductInfo info where info.productType.productTypeId = ? and info.state = " + BookPromotionConstant.SALLING;
		return productInfoDao.findByHQL(hql, pageBean, productTypeId);
	}

	@Override
	public List<ProductInfo> findByUserSchoolInfoId(PageBean pageBean, String schoolInfoId) {
		return productInfoDao.findByUserSchoolInfoId(pageBean, schoolInfoId);
	}

	@Override
	public BaseDao<ProductInfo, String> getDao() {
		return productInfoDao;
	}

	@Override
	public void updateProductState(String productId, Integer state) {
		ProductInfo productInfo = findById(productId);
		productInfo.setState(state);
	}

	@Override
	public List<ProductInfo> findAllSalling(PageBean pageBean) {
		String hql = "from ProductInfo info where info.state = "+BookPromotionConstant.SALLING+" order by info.pbDate desc";
		return productInfoDao.findByHQL(hql, pageBean);
	}
}
