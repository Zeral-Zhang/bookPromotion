package com.zeral.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeral.bean.MyCar;
import com.zeral.bean.PageBean;
import com.zeral.bean.ShopCarItem;
import com.zeral.constant.BookPromotionConstant;
import com.zeral.dao.BaseDao;
import com.zeral.dao.OrderMainDao;
import com.zeral.po.OrderDetail;
import com.zeral.po.OrderMain;
import com.zeral.po.ProductInfo;
import com.zeral.po.UserInfo;
import com.zeral.service.IOrderMainService;
import com.zeral.service.IProductInfoService;
import com.zeral.service.IUserService;

@Service
public class OrderMainServiceImpl extends BaseServiceImpl<OrderMain> implements IOrderMainService {
	
	private static final Logger log = Logger.getLogger(OrderMainServiceImpl.class);

	@Autowired
	private OrderMainDao orderMainDao;
	@Autowired
	private IProductInfoService productInfoService;
	@Autowired
	private IUserService userService;

	@Override
	public List<OrderMain> findAllMain(String userId, PageBean pageBean) {
		return orderMainDao.findAllByUser(userId, pageBean);
	}

	@Override
	public boolean saveOrder(MyCar myCar, UserInfo user) {
			// 取出购物车信息存入订单表中，并清空购物车, 同时减少商品数量
			Map<String, ShopCarItem> items = myCar.getItems();
			OrderMain orderMain = new OrderMain();
		try {
			// 订单详情信息
			List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
			for (String key : items.keySet()) {
				ShopCarItem item = items.get(key);
				OrderDetail orderdetail = new OrderDetail();
				orderdetail.setNum(item.getNum());
				orderdetail.setProductInfo(item.getProduct());
				orderdetail.setSumPrice(item.getPrice());
				orderdetail.setOrderMain(orderMain);
				orderDetails.add(orderdetail);
			}
			
			orderMain.setOrderDetails(orderDetails);
			orderMain.setSumPrice(myCar.getSumPrice());
			orderMain.setUserInfoId(user.getUserId());
			orderMain.setState(OrderMain.UN_HANDLE);
			orderMain.setBuyDate(new Date());
			// 存入订单主表信息
			orderMainDao.save(orderMain);
			log.info("保存" + orderMain.toString() + "订单成功");
			return true;
		} catch (Exception e) {
			log.error("保存" + orderMain.toString() + "订单失败", e);
			throw e;
		}
	}

	@Override
	public List<OrderMain> findOrderMain(PageBean pageBean, String userId) {
		String hql = "from OrderMain main where main.userInfoId = ?";
		return orderMainDao.findByHQL(hql, pageBean, userId);
	}
	
	@Override
	public BaseDao<OrderMain, String> getDao() {
		return orderMainDao;
	}

	@Override
	public void saveOrderAndReomveProduct(MyCar myCar, UserInfo user, Boolean isUsePoint) {
		// 使用积分，减少总价，消除积分
		if(isUsePoint) {
			myCar.setSumPrice(myCar.getSumPrice()-user.getUserDetailInfo().getUserPoints());
			// 保存订单
			saveOrder(myCar, user);
			// 消除积分
			user.getUserDetailInfo().setUserPoints(0);
			userService.updateUser(user.getUserDetailInfo());
		} else {
			// 保存订单
			saveOrder(myCar, user);
		}
		// 更新商品状态
		Map<String, ShopCarItem> items = myCar.getItems();
		for (String productInfoId : items.keySet()) {
			ProductInfo product = productInfoService.findDetail(productInfoId);
			product.setNumber(product.getNumber() - items.get(productInfoId).getNum());
			if(product.getNumber().equals(0)) {
				product.setState(BookPromotionConstant.SOLD_OUT);
			}
			productInfoService.update(product);
		}
	}
}
