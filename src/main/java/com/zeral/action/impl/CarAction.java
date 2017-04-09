package com.zeral.action.impl;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zeral.action.ICarAction;
import com.zeral.bean.MyCar;
import com.zeral.bean.ShopCarItem;
import com.zeral.po.ProductInfo;
import com.zeral.service.IProductInfoService;
import com.zeral.util.WebUtil;

@Controller
public class CarAction implements ICarAction {

	@Autowired
	private IProductInfoService productInfoService;
	
	@RequestMapping(value = "/addToCar", method = RequestMethod.GET)
	public String add(String productId, Integer num) {
		HttpSession session = WebUtil.getSession();
		try {
			MyCar car = (MyCar) session.getAttribute("mycar");
			if (car == null) {
				car = new MyCar();
				WebUtil.getSession().setAttribute("mycar", car);
			}
			if (productId != null) {
				ProductInfo productInfo = productInfoService.findDetail(productId);
				Map<String, ShopCarItem> items = car.add(productInfo, num);
				car.setItems(items);
				car.sumPrice();
				session.setAttribute("mycar", car);
			}
			return "redirect:shopCar";
		} catch (Exception e) {
			return "error";
		}

	}

	@RequestMapping(value = "/toShopCar", method = RequestMethod.GET)
	public String initShopCar() {
		return "shopCar";
	}

	@RequestMapping(value = "removeFromCar")
	@Override
	public void removeFromCar(String productId) {
		HttpSession session = WebUtil.getSession();
		try {
			MyCar car = (MyCar) session.getAttribute("mycar");
			if (car == null) {
				WebUtil.sendInfoMsg("购物车为空");
				return;
			}
			if (productId != null) {
				Map<String, ShopCarItem> items = car.remove(productId);
				car.setItems(items);
				car.sumPrice();
				session.setAttribute("mycar", car);
			}
		} catch (Exception e) {
			WebUtil.sendErrorMsg("删除商品出错");
		}
	}

	@RequestMapping(value = "changeQuantity", method = RequestMethod.GET)
	@Override
	public String changeQuantity(String productId, Integer num) {
		HttpSession session = WebUtil.getSession();
		try {
			MyCar car = (MyCar) session.getAttribute("mycar");
			if (productId != null) {
				ProductInfo product = productInfoService.findDetail(productId);
				Map<String, ShopCarItem> items = car.add(product, num);
				car.setItems(items);
				car.sumPrice();
				session.setAttribute("mycar", car);
			}
			return "shopCar"; 
		} catch (Exception e) {
			return "error";
		}
	}

}
