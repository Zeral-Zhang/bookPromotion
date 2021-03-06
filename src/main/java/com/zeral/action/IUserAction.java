package com.zeral.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.zeral.bean.PageBean;
import com.zeral.po.UserDetailInfo;

/**
 * 用于用户请求处理，包括用户授权，模拟用户登陆
 * 
 * @author ZeralZhang
 *
 */
public interface IUserAction {

//	/**
//	 * 模拟用户登录，用于本地测试
//	 */
//	public void login();

	/**
	 * 修改用户信息
	 * 
	 * @return
	 */
	public String update(UserDetailInfo detailInfo);
	
	/**
	 * 修改用户积分信息
	 * 
	 * @return
	 */
	public void updateUserPoints(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 初始化登陆
	 * 
	 * @return
	 */
	public String initLogin();

	/**
	 * 初始化用户信息界面
	 * 
	 * @return
	 */
	public String toUserInfo();

	/**
	 * 初始化用户详情页面
	 * 
	 * @return
	 */
	public String toUserDetail(Model model);

	/**
	 * 用户授权登陆
	 * @return
	 */
	String validateUser(HttpServletRequest request);

	/**
	 * 初始化用户正在售卖页面
	 * @return
	 */
	String toUserSaling(PageBean pageBean);
	
	/**
	 * 初始化用户已经购买列表
	 * @return
	 */
	String toUserPayed(PageBean pageBean);
	
	/**
	 * 初始化用户收藏列表
	 * @return
	 */
	String toUserFavorite(PageBean pageBean);

	/**
	 * 用户收藏或者取消收藏商品
	 * @param request
	 * @param response
	 */
	void updateUserFavorite(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 删除用户收藏
	 * @param favoriteId
	 * @param response
	 */
	void deleteFavorite(String favoriteId, HttpServletResponse response);
	
}
