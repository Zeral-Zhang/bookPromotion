package com.zeral.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeral.dao.BaseDao;
import com.zeral.dao.OrderDetailDao;
import com.zeral.po.OrderDetail;
import com.zeral.service.IOrderDetailService;

@Service
public class OrderDetailServiceImpl extends BaseServiceImpl<OrderDetail> implements IOrderDetailService {
	
	@Autowired
	private OrderDetailDao OrderDetailDao;
	
	@Override
	public OrderDetail findDetail(String orderDetailId) {
		return OrderDetailDao.findById(orderDetailId);
	}

	@Override
	public BaseDao<OrderDetail, String> getDao() {
		return OrderDetailDao;
	}

}
