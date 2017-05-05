package com.zeral.service;

import java.util.List;

import com.zeral.bean.MyCar;
import com.zeral.bean.PageBean;
import com.zeral.po.OrderMain;
import com.zeral.po.UserInfo;

public interface IOrderMainService extends IBaseService<OrderMain> {
	
	/**
	 * 分页查询用户的订单主表
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<OrderMain> findAllMain(String userId, PageBean pageBean);
	
	/**
	 * 保存用户订单信息
	 * @param myCar
	 * @param user
	 * @return
	 */
	public boolean saveOrder(MyCar myCar, UserInfo user);
	
	
	/**
	 * 通过用户查找自己购买商品的订单信息
	 * @param pageBean
	 * @param userId
	 * @return
	 */
	public List<OrderMain> findOrderMain(PageBean pageBean, String userId);

	/**
	 * 保存订单并相应移除商品数量
	 * @param myCar
	 * @param user
	 * @param isUsePoint
	 * @return
	 */
	public void saveOrderAndReomveProduct(MyCar myCar, UserInfo user, Boolean isUsePoint);
}
