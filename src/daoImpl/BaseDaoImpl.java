package daoImpl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import dao.BaseDao;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(T t) throws RuntimeException {
		this.getHibernateTemplate().save(t);
	}
	
	@Transactional(propagation = Propagation.REQUIRED , rollbackFor = RuntimeException.class)
	public void addManyObjects(List<T> objects) throws RuntimeException {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		for (int i=0;i<objects.size();i++) {
			T t = objects.get(i);
			session.save(t);
			if(i%50==0){
				session.flush();
			}
		}
		transaction.commit();
		session.close();
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Object[]> createHqlQuery(String hql) throws RuntimeException {
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Object[]> createSqlQuery(String sql) throws RuntimeException {
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query.list();
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(T t) throws RuntimeException {
		this.getHibernateTemplate().delete(t);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED , rollbackFor = RuntimeException.class)
	public void deleteManyObjects(List<T> objects) throws RuntimeException {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		for (int i=0;i<objects.size();i++) {
			T t = objects.get(i);
			session.delete(t);
			if(i%50==0){
				session.flush();
			}
		}
		transaction.commit();
		session.close();
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<T> findByHQL(String hql, Object... objects)
			throws RuntimeException {
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		if(objects!=null && objects.length>0){
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i, objects[i]);
			}
		}
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<T> findByHQL(String hql, int fisrtResult, int maxResult,
			Object... objects) throws RuntimeException {
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		query.setFirstResult(fisrtResult);
		query.setMaxResults(maxResult);
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public T get(Class<T> clazz, Integer id) throws RuntimeException {
		return (T) this.getSessionFactory().getCurrentSession().get(clazz, id);
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public T load(Class<T> clazz, Integer id) throws RuntimeException {
		return (T) this.getSessionFactory().getCurrentSession().get(clazz, id);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(Class<T> clazz, Integer id) throws RuntimeException {
		T t = this.get(clazz, id);
		this.delete(t);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(T t) throws RuntimeException {
		this.getHibernateTemplate().update(t);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED , rollbackFor = RuntimeException.class)
	public void updateManyObjects(List<T> objects) throws RuntimeException {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		for (int i=0;i<objects.size();i++) {
			T t = objects.get(i);
			session.update(t);
			if(i%50==0){
				session.flush();
			}
		}
		transaction.commit();
		session.close();
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED , rollbackFor = RuntimeException.class)
	public void mergeManyObjects(List<T> objects) throws RuntimeException {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		for (int i=0;i<objects.size();i++) {
			T t = objects.get(i);
			session.merge(t);
			if(i%50==0){
				session.flush();
			}
		}
		transaction.commit();
		session.close();
	}
	
	
	@Transactional(readOnly=true)
	public Query createSQLQuery(String sql) throws RuntimeException {
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Object getUniqueResult(String hql) throws RuntimeException {
		Object t = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).uniqueResult();
		return t;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<T> createCriteriaQuery(Class<T> clazz)
			throws RuntimeException {
		List<T> list = null;
		Criteria criteria = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(clazz);
		list = criteria.list();
		return list;
	}
	
	/**
	 * 
	 * @param clazz
	 * @param objects
	 * @param otherParam ÅÅÐòÌõ¼þ
	 * @return 
	 * @throws RuntimeException
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<T> createCriteriaQuery(Class<T> clazz, List<Criterion> criterions ,List<Projection> projections,List<Order> orders)
			throws RuntimeException {
		List<T> list = null;
		Criteria criteria = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(clazz);
		if(criterions!=null && !criterions.isEmpty()){
			for (int i = 0; i < criterions.size(); i++) {
				criteria.add(criterions.get(i));
			}
		}
		if(projections!=null && !projections.isEmpty()){
			for (int i = 0; i < projections.size(); i++) {
				criteria.setProjection(projections.get(i));
			}
		}
		if(orders!=null && !orders.isEmpty()){
			for (int i = 0; i < orders.size(); i++) {
				criteria.addOrder(orders.get(i));
			}
		}
		list = criteria.list();
		return list;
	}
	
	@Transactional(propagation = Propagation.REQUIRED , rollbackFor = RuntimeException.class)
	public void merge(T t) throws RuntimeException {
		this.getHibernateTemplate().merge(t);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<T> createCriteriaQuery(Class<T> clazz,
			List<Criterion> criterions, List<Projection> projections,
			List<Order> orders, int first, int maxresult)
			throws RuntimeException {
		List<T> list = null;
		Criteria criteria = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(clazz);
		if(criterions!=null && !criterions.isEmpty()){
			for (int i = 0; i < criterions.size(); i++) {
				criteria.add(criterions.get(i));
			}
		}
		if(orders!=null && !orders.isEmpty()){
			for (int i = 0; i < orders.size(); i++) {
				criteria.addOrder(orders.get(i));
			}
		}
		if(projections!=null && !projections.isEmpty()){
			for (int i = 0; i < projections.size(); i++) {
				criteria.setProjection(projections.get(i));
			}
		}
		criteria.setFirstResult(first);
		criteria.setMaxResults(maxresult);
		list = criteria.list();
		return list;
	}
	

}
