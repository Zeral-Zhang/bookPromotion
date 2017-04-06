package com.zeral.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.zeral.bean.PageBean;
import com.zeral.po.OrderMain;

@Repository("OrderMainDAO")
public class OrderMainDao extends BaseDao<OrderMain, String> {
	// property constants
	public static final String STATE = "state";
	public static final String SUM_PRICE = "sumPrice";
	public static final String CONTEXT = "context";


	@SuppressWarnings("unchecked")
	public <T> List<T> findByProperty(String propertyName, Object value) {
			String queryString = "from Ordermain as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
	}

	public List<OrderMain> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List<OrderMain> findBySumPrice(Object sumPrice) {
		return findByProperty(SUM_PRICE, sumPrice);
	}

	public List<OrderMain> findByContext(Object context) {
		return findByProperty(CONTEXT, context);
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderMain> findAllByUser(String userId, PageBean pageBean) {
		String hql="from Ordermain o where o.userinfo.userId = ? order by o.confirmDate desc";
		Query query=getCurrentSession().createQuery(hql).setParameter(0, userId);
		query.setFirstResult(pageBean.getOffset());
		query.setMaxResults(pageBean.getPageSize());
		return query.list();
	}

}