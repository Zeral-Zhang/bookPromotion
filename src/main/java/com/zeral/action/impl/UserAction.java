package com.zeral.action.impl;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zeral.action.IUserAction;
import com.zeral.bean.PageBean;
import com.zeral.bean.WeixinOauth2Token;
import com.zeral.constant.BookPromotionConstant;
import com.zeral.po.Favorite;
import com.zeral.po.OrderMain;
import com.zeral.po.ProductInfo;
import com.zeral.po.SchoolInfo;
import com.zeral.po.UserDetailInfo;
import com.zeral.po.UserInfo;
import com.zeral.service.IOrderMainService;
import com.zeral.service.IProductInfoService;
import com.zeral.service.ISchoolInfoService;
import com.zeral.service.IUserService;
import com.zeral.util.HttpsUtil;
import com.zeral.util.PropertiesConfigUtil;
import com.zeral.util.WebUtil;

/**
 * @author Zeral_Zhang
 * 
 */
@Controller
public class UserAction extends BaseAction implements IUserAction {

	@Autowired
	private IUserService userService;
	@Autowired
	private ISchoolInfoService schoolInfoService;
	@Autowired
	private IProductInfoService productInfoService;
	@Autowired
	private IOrderMainService orderMainService;
	
	@Override
	@RequestMapping(value="/validateUser", method = RequestMethod.GET)
	public String validateUser(HttpServletRequest request) {
		Properties prop = PropertiesConfigUtil.getProperties("account.properties");
		// 用户同意授权后，能获取到code
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String path = StringUtils.isEmpty(state) ? "toProductList" : state;
		// 用户同意授权
		if (StringUtils.isNotBlank(code)) {
			// 获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = HttpsUtil
					.getOauth2AccessToken(prop.getProperty("appid"),
							prop.getProperty("appsecret"), code);
			// 网页授权接口访问凭证
			String accessToken = weixinOauth2Token.getToken();
			// 用户标识
			String openId = weixinOauth2Token.getOpenId();
			// 获取用户信息
			UserInfo userInfo = HttpsUtil.getSNSUserInfo(accessToken, openId);
			userInfo.setUserDetailInfo(userService.findUserDetail(getLoginUser().getUserId()));
			// 设置要传递的参数
			request.getSession().setAttribute("userInfo", userInfo);
			return "redirect:"+path;
		} else {
			return "error";
		}
	}

	@Override
	public String initLogin() {
		return "success";
	}

	@Override
	@RequestMapping(value="/toUserDetail", method = RequestMethod.GET)
	public String toUserDetail(Model model) {
		try {
			UserInfo user = getLoginUser();
			List<SchoolInfo> schoolInfolst = schoolInfoService.findColleges();
			if (null != user.getUserDetailInfo() && null != user.getUserDetailInfo().getSchoolInfo()) {
				List<SchoolInfo> departmentlst = schoolInfoService.findByCollegeId(user.getUserDetailInfo().getSchoolInfo().getPCode());
				model.addAttribute("departmentlst", departmentlst);
			}
			model.addAttribute("schoolInfolst", schoolInfolst);
			setLoginUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "userDetail";
	}

	@Override
	@RequestMapping(value="/toUserInfo", method = RequestMethod.GET)
	public String toUserInfo() {
		if (null == super.getLoginUser()) {
			return "redirect:"+HttpsUtil.AuthLogin(BookPromotionConstant.VALIDATE_URL, "toUserInfo");
		}
		return "userInfo";
	}

	@Override
	@RequestMapping(value="/toUserSaling", method = RequestMethod.GET)
	public String toUserSaling(PageBean pageBean) {
		try {
			if (null == super.getLoginUser()) {
				return "redirect:"+HttpsUtil.AuthLogin(BookPromotionConstant.VALIDATE_URL, "toUserSaling");
			}
			pageBean = pageBean == null ? new PageBean() : pageBean;
			List<ProductInfo> lsemp = productInfoService.findByUserId(pageBean, getLoginUser().getUserId());
			pageBean.setPagelist(lsemp);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "userSaling";
	}
	
	@Override
	@RequestMapping(value="/toUserPayed", method = RequestMethod.GET)
	public String toUserPayed(PageBean pageBean) {
		try {
			if (null == super.getLoginUser()) {
				return "redirect:"+HttpsUtil.AuthLogin(BookPromotionConstant.VALIDATE_URL, "toUserPayed");
			}
			pageBean = pageBean == null ? new PageBean() : pageBean;
			List<OrderMain> lsemp = orderMainService.findOrderMain(pageBean, getLoginUser().getUserId());
			pageBean.setPagelist(lsemp);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "userPayed";
	}
	
	@Override
	@RequestMapping(value="/updateUser", method = RequestMethod.POST)
	public String update(UserDetailInfo userDetail) {
		try {
			userDetail.setUserInfo(getLoginUser().getUserId());
			// 将修改后的用户信息保存到session域中
			getLoginUser().setUserDetailInfo(userService.updateUser(userDetail));
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "redirect:toUserDetail";
	}

	@Override
	@RequestMapping(value="/updateUserPoints", method = RequestMethod.GET)
	public void updateUserPoints(HttpServletRequest request, HttpServletResponse response) {
		String firstShareUserId = request.getParameter("firstShareUserId");
		String shareUserId = request.getParameter("shareUserId");
		if(StringUtils.isNotBlank(firstShareUserId) && StringUtils.isBlank(shareUserId)) {
			updateUserPoint(firstShareUserId);
			WebUtil.sendSuccessMsg("分享成功，你的积分+1", response);
		} else if(StringUtils.isNotBlank(shareUserId) && (!firstShareUserId.equals(shareUserId))) {
			// 有第一个分享者，也有第二个分享者时，并且不是同一个人时，两人积分都+1分
			updateUserPoint(firstShareUserId);
			updateUserPoint(shareUserId);
			WebUtil.sendSuccessMsg("分享成功，你和原始分享人的积分都+1", response);
		} else {
			WebUtil.sendErrorMsg("无效分享", response);
		}
	}
	
	@Override
	@RequestMapping(value="/updateUserFavorite", method = RequestMethod.GET)
	public void updateUserFavorite(HttpServletRequest request, HttpServletResponse response) {
		try {
			String productId = request.getParameter("bookId");
			Boolean isFavorite = Boolean.valueOf(request.getParameter("isFavorite"));
			if(isFavorite) {
				userService.removeFavorite(productId, getLoginUser().getUserId());
			} else {
				userService.addFavorite(productId, getLoginUser().getUserId());
			}
			WebUtil.sendSuccessMsg("更新状态成功", response);
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.sendErrorMsg("更新状态失败", response);
		}
	}
	
	
	public void updateUserPoint(String userId) {
		UserDetailInfo shareUser = userService.findUserDetail(userId);
		if(null != shareUser) {
			shareUser.setUserPoints((null == shareUser.getUserPoints() ? 0 : shareUser.getUserPoints())+1);
		}else {
			shareUser = new UserDetailInfo();
			shareUser.setUserInfo(userId);
			shareUser.setUserPoints(1);
		}
		userService.saveOrUpdate(shareUser);
	}

	@Override
	@RequestMapping(value="/toUserFavorite", method = RequestMethod.GET)
	public String toUserFavorite(PageBean pageBean) {
		try {
			pageBean = pageBean == null ? new PageBean() : pageBean;
			List<Favorite> lsemp = userService.findFavorites(pageBean, getLoginUser().getUserId());
			pageBean.setPagelist(lsemp);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "userFavorite";
	}
	
	@Override
	@RequestMapping(value="/deleteFavorite/{favoriteId}", method = RequestMethod.GET)
	public void deleteFavorite(@PathVariable String favoriteId, HttpServletResponse response) {
		try {
			userService.removeFavorite(favoriteId);
			WebUtil.sendSuccessMsg("删除成功", response);
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.sendErrorMsg("删除失败", response);
		}
	}

}
