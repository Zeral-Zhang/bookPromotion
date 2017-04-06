package com.zeral.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeral.dao.BaseDao;
import com.zeral.dao.ProductTypeDao;
import com.zeral.po.ProductType;
import com.zeral.service.IProductTypeService;

@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType> implements IProductTypeService  {
	
	@Autowired
	private ProductTypeDao productTypeDao;
	
	@Override
	public BaseDao<ProductType, String> getDao() {
		return productTypeDao;
	}

}
