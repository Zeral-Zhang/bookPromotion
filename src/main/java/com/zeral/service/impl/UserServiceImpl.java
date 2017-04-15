package com.zeral.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeral.bean.AccessToken;
import com.zeral.dao.BaseDao;
import com.zeral.dao.UserDetailInfoDao;
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
}
