package com.zeral.service;

import java.util.List;

import com.zeral.bean.PageBean;
import com.zeral.po.Favorite;
import com.zeral.po.UserDetailInfo;
import com.zeral.po.UserInfo;

public interface IUserService extends IBaseService<UserDetailInfo> {
	/**
	 * 通过openId查找用户详情
	 * @param openId
	 * @return
	 */
	public UserDetailInfo findUserDetail(String openId);
	
	/**
	 * 修改用户信息，若用户信息不存在，则新建用户信息并保存
	 * @param userinfo
	 * @return UserDetailInfo 用户信息
	 */
	public UserDetailInfo updateUser(UserDetailInfo userInfo);
	
	/**
	 * 通过openId查找用户
	 * @param openId
	 * @return
	 */
	public UserInfo findByOpenId(String openId);

	/**
	 * 移除收藏
	 * @param productId
	 * @param userId
	 */
	public void removeFavorite(String productId, String userId);
	
	/**
	 * 移除收藏
	 * @param favoriteId
	 */
	public void removeFavorite(String favoriteId);

	/**
	 * 添加收藏
	 * @param productId
	 * @param userId
	 */
	public void addFavorite(String productId, String userId);

	/**
	 * 查找用户单个收藏
	 * @param productId
	 * @param userId
	 * @return
	 */
	public Favorite findFavorite(String productId, String userId);

	/**
	 * 分页查找用户收藏信息
	 * @param pageBean
	 * @param userId
	 * @return
	 */
	public List<Favorite> findFavorites(PageBean pageBean, String userId);
}
