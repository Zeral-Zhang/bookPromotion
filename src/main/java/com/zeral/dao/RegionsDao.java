package com.zeral.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.zeral.po.Regions;

@Repository("RegionsDAO")
public class RegionsDao extends BaseDao<Regions, String> {
	// property constants
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String _PCODE = "PCode";
	public static final String LEVEL = "level";

	@SuppressWarnings("unchecked")
	public List<Regions> findByProperty(String propertyName, Object value) {
			String queryString = "from Regions as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
	}

	public List<Regions> findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List<Regions> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Regions> findByPCode(Object PCode) {
		return findByProperty(_PCODE, PCode);
	}

	public List<Regions> findByLevel(Object level) {
		return findByProperty(LEVEL, level);
	}
	
}