package com.zeral.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeral.bean.AccessToken;
import com.zeral.bean.PageBean;
import com.zeral.constant.BookPromotionConstant;
import com.zeral.dao.BaseDao;
import com.zeral.dao.FavoriteDao;
import com.zeral.dao.UserDetailInfoDao;
import com.zeral.po.Favorite;
import com.zeral.po.UserDetailInfo;
import com.zeral.po.UserInfo;
import com.zeral.service.IBasicConfigService;
import com.zeral.service.ISchoolInfoService;
import com.zeral.service.IUserService;
import com.zeral.util.HttpsUtil;
@Service
public class UserServiceImpl extends BaseServiceImpl<UserDetailInfo> implements IUserService {
	@Autowired
	private UserDetailInfoDao userInfoDao;
	@Autowired
	private ISchoolInfoService schoolInfoService;
	@Autowired
	private IBasicConfigService basicConfigService;
	@Autowired
	private FavoriteDao favoriteDao;

	public UserDetailInfo findUserDetail(String openId) {
		return userInfoDao.findByUserId(openId);
	}

	@Override
	public UserDetailInfo updateUser(UserDetailInfo userInfo) {
		UserDetailInfo userDetailInfo = userInfoDao.findByUserId(userInfo.getUserInfo());
		if(null == userDetailInfo) {
			userDetailInfo = new UserDetailInfo();
			userDetailInfo.setUserTel(userInfo.getUserTel());
			userDetailInfo.setUserAge(userInfo.getUserAge());
			userDetailInfo.setUserClass(userInfo.getUserClass());
			userDetailInfo.setUserGrade(userInfo.getUserGrade());
			userDetailInfo.setUserInfo(userInfo.getUserInfo());
			userDetailInfo.setSchoolInfo(schoolInfoService.findByCode(userInfo.getSchoolInfo().getCode()));
			super.save(userDetailInfo);
		} else {
			userDetailInfo.setUserTel(userInfo.getUserTel());
			userDetailInfo.setUserAge(userInfo.getUserAge());
			userDetailInfo.setUserClass(userInfo.getUserClass());
			userDetailInfo.setUserGrade(userInfo.getUserGrade());
			userDetailInfo.setUserInfo(userInfo.getUserInfo());
			userDetailInfo.setSchoolInfo(schoolInfoService.findByCode(userInfo.getSchoolInfo().getCode()));
			super.update(userDetailInfo);
		}
		return userDetailInfo;
	}

	/**
	 * 通过微信openId查找用户数据
	 * @param openId
	 * @return
	 */
	@Override
	public UserInfo findByOpenId(String openId) {
		AccessToken accessToken = basicConfigService.getValidAccessToken();
		if(null != accessToken) {
			return HttpsUtil.getUserInfo(accessToken.getToken(), openId);
		} else {
			return null;
		}
	}

	@Override
	public BaseDao<UserDetailInfo, String> getDao() {
		return userInfoDao;
	}

	@Override
	public void removeFavorite(String productId, String userId) {
		favoriteDao.deleteByProductIdAndUserId(productId, userId);
	}

	@Override
	public void addFavorite(String productId, String userId) {
		Favorite favorite = new Favorite();
		favorite.setUserInfoId(userId);
		favorite.setProductId(productId);
		favorite.setCreateDate(new Date());
		favoriteDao.save(favorite);
	}

	@Override
	public Favorite findFavorite(String productId, String userId) {
		return favoriteDao.findByProductIdAndUserId(productId, userId);
	}

	@Override
	public List<Favorite> findFavorites(PageBean pageBean, String userId) {
		String hql = "from Favorite where userInfoId = ? and productInfo.state = " + BookPromotionConstant.SALLING;
		return favoriteDao.findByHQL(hql, pageBean, userId);
	}

	@Override
	public void removeFavorite(String favoriteId) {
		favoriteDao.delete(favoriteId);
	}
}
