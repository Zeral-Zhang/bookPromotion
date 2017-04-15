package com.zeral.action.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zeral.action.IProductAction;
import com.zeral.bean.PageBean;
import com.zeral.constant.BookPromotionConstant;
import com.zeral.po.ProductInfo;
import com.zeral.po.ProductType;
import com.zeral.service.IProductInfoService;
import com.zeral.service.IProductTypeService;
import com.zeral.service.IUserService;
import com.zeral.util.HttpsUtil;
import com.zeral.util.WebUtil;


@Controller
public class ProductAction extends BaseAction implements IProductAction {
	
	@Autowired
	private IProductInfoService productInfoService;
	@Autowired
	private IProductTypeService productTypeService;
	@Autowired
	private IUserService userService;

	@Override
	@RequestMapping(value="/toProductAdd", method = RequestMethod.GET)
	public String toProductAdd(HttpServletResponse response) {
		try {
			if (null == super.getLoginUser()) {
				response.sendRedirect(HttpsUtil.AuthLogin(BookPromotionConstant.VALIDATE_URL, "toProductAdd"));
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "productAdd";
	}

	@Override
	@RequestMapping(value="/productAdd", method = RequestMethod.POST)
	public String addProduct(ProductInfo productInfo) {
		try {
			// 添加商品发布日期
			productInfo.setPbDate(new Date());
			// 给商品添加用户信息
			productInfo.setUserInfoId(getLoginUser().getUserId());
			productInfoService.save(productInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "redirect:/toProductList";
	}

	@Override
	@RequestMapping(value = "/initProductType", method = RequestMethod.GET)
	public void initProductType(HttpServletResponse response) {
		try {
			List<ProductType> productTypelst = productTypeService.findAll(Order.asc("order"));
			WebUtil.sendJSONArrayResponse(productTypelst, new String[] { "productInfos" }, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@RequestMapping(value = "/toProductList", method = RequestMethod.GET)
	public String toProductList(PageBean pageBean, String search, Model model) {
		try {
			List<ProductInfo> lsemp = null;
			if(StringUtils.isNotBlank(search)) {
				lsemp = productInfoService.findByNameLike(pageBean, search);
			} else {
				// 获取当前页的记录集合
				lsemp = productInfoService.findAll(pageBean, Order.desc("pbDate"));
			}
			// 封装数据到PageBean
			pageBean.setPagelist(lsemp);
			model.addAttribute(pageBean);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "productList";
	}
	
	@Override
	@RequestMapping(value = "/toProductCateList/{productTypeId}", method = RequestMethod.GET)
	public String toProductCateList(PageBean pageBean, @PathVariable String productTypeId, String search, Model model) {
		try {
			List<ProductInfo> lsemp = null;
			if(StringUtils.isNotBlank(search)) {
				lsemp = productInfoService.findByTypeAndNameLike(pageBean, productTypeId, search);
			} else {
				// 获取当前页的记录集合
				lsemp = productInfoService.findByType(pageBean, productTypeId);
			}
			// 封装数据到PageBean
			pageBean.setPagelist(lsemp);
			ProductType productType = productTypeService.findById(productTypeId);
			model.addAttribute(productType).addAttribute(pageBean);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "productCateList";
	}

	@Override
	@RequestMapping(value="/toProductDetail/{productId}")
	public String toProductDetail(@PathVariable String productId, Model model) {
		try {
			if (null != productId) {
				ProductInfo productInfo = productInfoService.findDetail(productId);
				productInfo.setUserInfo(userService.findByOpenId(productInfo.getUserInfoId()));
				model.addAttribute(productInfo);
				if(null == getLoginUser()) {
					return "redirect:"+HttpsUtil.AuthLogin(BookPromotionConstant.VALIDATE_URL, "toProductDetail/"+productId);
				}
			}
			return "productDetail";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	@RequestMapping(value="/deleteProduct/{productId}")
	public void deleProduct(@PathVariable String productId, HttpServletResponse response) {
		try {
			if(StringUtils.isNotBlank(productId)) {
				productInfoService.delete(productId);
			}
			WebUtil.sendSuccessMsg("删除商品成功", response);
		} catch (Exception e) {
			WebUtil.sendErrorMsg("删除商品出错", response);
		}
		
	}
}
