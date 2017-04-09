package com.zeral.action.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.zeral.action.IOrderAction;
import com.zeral.bean.MyCar;
import com.zeral.bean.PageBean;
import com.zeral.bean.ShopCarItem;
import com.zeral.constant.WenlibackyardConstant;
import com.zeral.po.OrderMain;
import com.zeral.po.ProductInfo;
import com.zeral.po.UserInfo;
import com.zeral.service.IOrderMainService;
import com.zeral.service.IProductInfoService;
import com.zeral.util.HttpsUtil;
import com.zeral.util.WebUtil;

@Controller
public class OrderAction implements IOrderAction {
	@Autowired
	private IProductInfoService productInfoService;
	@Autowired
	private IOrderMainService orderMainService;

	private PageBean pageBean;
	private List<OrderMain> mainlst;

	@Override
	public String addOrder() {
		HttpSession session = WebUtil.getSession();
		try {
			MyCar myCar = (MyCar) session.getAttribute("mycar");
			UserInfo user = (UserInfo) session.getAttribute("userInfo");
			if (null == user) {
				WebUtil.getResponse().sendRedirect(HttpsUtil.AuthLogin(WenlibackyardConstant.VALIDATE_URL, "addOrder"));
				return null;
			}
			// 购买成功，商品数量减少
			if (orderMainService.saveOrder(myCar, user)) {
				session.removeAttribute("mycar");
				Map<String, ShopCarItem> items = myCar.getItems();
				for (String productInfoId : items.keySet()) {
					ProductInfo product = productInfoService.findDetail(productInfoId);
					product.setNumber(product.getNumber() - items.get(productInfoId).getNum());
					if(product.getNumber().equals(0)) {
						productInfoService.delete(productInfoId);
					} else {
						productInfoService.update(product);
					}
				}
				return "success";
			} else {
				return "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
	}

	@Override
	public String findAllMain() {
		HttpSession session = WebUtil.getSession();
		try {
			UserInfo user = (UserInfo) session.getAttribute("userInfo");
			String userId = user.getUserId();
			pageBean = new PageBean();
			pageBean.setPage(1);
			pageBean.setPageSize(8);
			mainlst = orderMainService.findAllMain(userId, pageBean);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public List<OrderMain> getMainlst() {
		return mainlst;
	}

	public void setMainlst(List<OrderMain> mainlst) {
		this.mainlst = mainlst;
	}
	
}
