package com.zeral.service;

import com.zeral.po.OrderDetail;

public interface IOrderDetailService extends IBaseService<OrderDetail> {
	
	/**
	 * 查看订单详情信息
	 * @param orderDetailId
	 * @return
	 */
	public OrderDetail findDetail(String orderDetailId);

}
