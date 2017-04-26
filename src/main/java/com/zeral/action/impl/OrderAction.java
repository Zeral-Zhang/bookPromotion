package com.zeral.action.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zeral.action.IOrderAction;
import com.zeral.bean.MyCar;
import com.zeral.bean.PageBean;
import com.zeral.constant.BookPromotionConstant;
import com.zeral.po.OrderMain;
import com.zeral.po.UserInfo;
import com.zeral.service.IOrderMainService;
import com.zeral.util.HttpsUtil;
import com.zeral.util.WebUtil;

@Controller
public class OrderAction implements IOrderAction {
	@Autowired
	private IOrderMainService orderMainService;

	@Override
	@RequestMapping(value = "/addOrder", method = RequestMethod.GET)
	public String addOrder(HttpServletResponse response) {
		HttpSession session = WebUtil.getSession();
		try {
			MyCar myCar = (MyCar) session.getAttribute("mycar");
			UserInfo user = (UserInfo) session.getAttribute("userInfo");
			if (null == user) {
				response.sendRedirect(HttpsUtil.AuthLogin(BookPromotionConstant.VALIDATE_URL, "addOrder"));
			}
			// 购买成功，商品数量减少
			orderMainService.saveOrderAndReomveProduct(myCar, user);
			session.removeAttribute("mycar");
			return "shopCar";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	@RequestMapping(value = "/findAllMain", method = RequestMethod.GET)
	public String findAllMain(PageBean pageBean, Model model) {
		HttpSession session = WebUtil.getSession();
		try {
			UserInfo user = (UserInfo) session.getAttribute("userInfo");
			String userId = user.getUserId();
			pageBean = new PageBean();
			pageBean.setPage(1);
			pageBean.setPageSize(8);
			List<OrderMain> mainlst = orderMainService.findAllMain(userId, pageBean);
			model.addAttribute(mainlst);
			return "userPayed";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

}
