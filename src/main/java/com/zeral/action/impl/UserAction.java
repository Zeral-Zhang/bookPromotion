package com.zeral.action.impl;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zeral.action.IUserAction;
import com.zeral.bean.PageBean;
import com.zeral.bean.WeixinOauth2Token;
import com.zeral.constant.WenlibackyardConstant;
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

/**
 * @author Zeral_Zhang
 * 
 */
@Controller
public class UserAction extends BaseAction implements IUserAction {

	private static final long serialVersionUID = 1L;

	private UserInfo userInfo;
	private UserDetailInfo userDetail;
	private String userid;
	private String path;
	private PageBean pageBean;
	/**
	 * 学院信息
	 */
	private List<SchoolInfo> schoolInfolst;
	/**
	 * 系信息
	 */
	private List<SchoolInfo> departmentlst;

	@Autowired
	private IUserService userService;
	@Autowired
	private ISchoolInfoService schoolInfoService;
	@Autowired
	private IProductInfoService productInfoService;
	@Autowired
	private IOrderMainService orderMainService;
	
	@Override
	@RequestMapping(value="/validateUser/{path}", method = RequestMethod.GET)
	public String validateUser(HttpServletRequest request, @PathVariable String path) {
		Properties prop = PropertiesConfigUtil.getProperties("account.properties");
		// 用户同意授权后，能获取到code
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		path = StringUtils.isEmpty(state) ? "toProductList" : state;
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
			userInfo = HttpsUtil.getSNSUserInfo(accessToken, openId);
			// 设置要传递的参数
			request.getSession().setAttribute("userInfo", userInfo);
			return "success";
		} else {
			return "error";
		}
	}

	@Override
	public String initLogin() {
		return "success";
	}

	@Override
	public String toUserDetail() {
		try {
			UserInfo user = getLoginUser();
			user.setUserDetailInfo(userService.findUserDetail(getLoginUser().getUserId()));
			schoolInfolst = schoolInfoService.findColleges();
			if (null != user.getUserDetailInfo() && null != user.getUserDetailInfo().getSchoolInfo()) {
				departmentlst = schoolInfoService.findByCollegeId(user.getUserDetailInfo().getSchoolInfo().getPCode());
			}
			setLoginUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}

	@Override
	public String toUserInfo() {
		if (null == super.getLoginUser()) {
			return "redirect:"+HttpsUtil.AuthLogin(WenlibackyardConstant.VALIDATE_URL, "toUserInfo");
		}
		return "success";
	}

	@Override
	public String toUserSaling() {
		try {
			if (null == super.getLoginUser()) {
				return "redirect:"+HttpsUtil.AuthLogin(WenlibackyardConstant.VALIDATE_URL, "toUserSaling");
			}
			pageBean = pageBean == null ? new PageBean() : pageBean;
			List<ProductInfo> lsemp = productInfoService.findByUserId(pageBean, getLoginUser().getUserId());
			pageBean.setPagelist(lsemp);
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}
	
	@Override
	public String toUserPayed() {
		try {
			if (null == super.getLoginUser()) {
				return "redirect:"+HttpsUtil.AuthLogin(WenlibackyardConstant.VALIDATE_URL, "toUserPayed");
			}
			pageBean = pageBean == null ? new PageBean() : pageBean;
			List<OrderMain> lsemp = orderMainService.findOrderMain(pageBean, getLoginUser().getUserId());
			pageBean.setPagelist(lsemp);
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}
	
	@Override
	public String update() {
		try {
			userDetail.setUserInfo(getLoginUser().getUserId());
			// 将修改后的用户信息保存到session域中
			getLoginUser().setUserDetailInfo(userService.updateUser(userDetail));
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}


	public UserDetailInfo getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetailInfo userDetail) {
		this.userDetail = userDetail;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<SchoolInfo> getSchoolInfolst() {
		return schoolInfolst;
	}

	public void setSchoolInfolst(List<SchoolInfo> schoolInfolst) {
		this.schoolInfolst = schoolInfolst;
	}

	public List<SchoolInfo> getDepartmentlst() {
		return departmentlst;
	}

	public void setDepartmentlst(List<SchoolInfo> departmentlst) {
		this.departmentlst = departmentlst;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
}
