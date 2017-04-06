package com.zeral.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.zeral.po.Favorite;

@Repository("FavoriteDAO")
public class FavoriteDao extends BaseDao<Favorite, String> {
	// property constants
	public static final String CONTEXT = "context";

	
	@SuppressWarnings("unchecked")
	public <T> List<T> findByProperty(String propertyName, Object value) {
			String queryString = "from Favorite as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
	}

	public List<Favorite> findByContext(Object context) {
		return findByProperty(CONTEXT, context);
	}
}