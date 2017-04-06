package com.zeral.action.impl;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.simpleframework.xml.Namespace;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zeral.action.ICarAction;
import com.zeral.bean.MyCar;
import com.zeral.bean.ShopCarItem;
import com.zeral.po.ProductInfo;
import com.zeral.util.WebUtil;

@Controller
@RequestMapping("/")
public class CarAction implements ICarAction {
	private String productId;
	private Integer num;

	@RequestMapping(value = "/add_Car", method = RequestMethod.GET)
	public String add() {
		HttpSession session = WebUtil.getSession();
		try {
			MyCar car = (MyCar) session.getAttribute("mycar");
			if (car == null) {
				car = new MyCar();
				WebUtil.getSession().setAttribute("mycar", car);
			}
			if (productId != null) {
				ProductInfo productInfo = bizs.getProductInfobiz().findDetail(productId);
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

	@Action(value = "shopCar", results = { 
			@Result(name = "success", location = "/WEB-INF/new_front/shopCar.jsp") 
		})
	public String initShopCar() {
		return "success";
	}

	@Action(value = "removeFromCar")
	@Override
	public void removeFromCar() {
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

	@Action(value = "changeQuantity", results = { 
			@Result(name = "success", location = "/WEB-INF/new_front/shopCar.jsp") 
		})
	@Override
	public String changeQuantity() {
		HttpSession session = WebUtil.getSession();
		try {
			MyCar car = (MyCar) session.getAttribute("mycar");
			/*if (car == null) {
				WebUtil.sendInfoMsg("购物车为空");
				return;
			}*/
			if (productId != null) {
				ProductInfo product = bizs.getProductInfobiz().findDetail(productId);
				Map<String, ShopCarItem> items = car.add(product, num);
				car.setItems(items);
				car.sumPrice();
				session.setAttribute("mycar", car);
			}
			return "success"; 
		} catch (Exception e) {
//			WebUtil.sendErrorMsg("修改商品数量出错");
			return "failed";
		}
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
