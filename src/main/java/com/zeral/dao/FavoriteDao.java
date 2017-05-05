package com.zeral.dao;

import org.springframework.stereotype.Repository;

import com.zeral.po.Favorite;

@Repository("FavoriteDAO")
public class FavoriteDao extends BaseDao<Favorite, String> {
	// property constants
	public static final String CONTEXT = "context";

	public Favorite findByProductIdAndUserId(String productId, String userId) {
		Favorite favorite = new Favorite();
		favorite.setProductId(productId);
		favorite.setUserInfoId(userId);
		return findUnique(favorite);
	}

	public void deleteByProductIdAndUserId(String productId, String userId) {
		String hql = "delete from Favorite where productInfo.productId = ? and userInfoId = ?";
		executeBulk(hql, productId, userId);
	}
}