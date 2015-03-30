package serviceImpl;

import java.util.List;
import javax.annotation.Resource;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;

import service.BaseService;
import dao.BaseDao;

public class BaseServiceImpl<T> implements BaseService<T> {
	
	@Resource(name="baseDao")
	BaseDao<T> baseDao;
	
	public BaseDao<T> getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	public void add(T t) {
		this.baseDao.add(t);
	}

	public void addManyObjects(List<T> objects) {
		this.baseDao.addManyObjects(objects);
	}

	public List<Object[]> createHqlQuery(String hql) {
		return this.baseDao.createHqlQuery(hql);
	}

	public List<Object[]> createSqlQuery(String sql) {
		return this.baseDao.createSqlQuery(sql);
	}

	public void deleteManyObjects(List<T> objects) {
		this.baseDao.deleteManyObjects(objects);
	}

	public List<T> findByHQL(String hql, Object... objects) {
		return this.baseDao.findByHQL(hql, objects);
	}

	public List<T> findByHQL(String hql, int fisrtResult, int maxResult,
			Object... objects) {
		return this.baseDao.findByHQL(hql, fisrtResult, maxResult, objects);
	}

	public T get(Class<T> clazz, Integer id) {
		return this.baseDao.get(clazz, id);
	}

	public T load(Class<T> clazz, Integer id) {
		return this.baseDao.load(clazz, id);
	}

	public void remove(Class<T> clazz, Integer id) {
		this.baseDao.remove(clazz, id);
	}

	public void update(T t) {
		this.baseDao.update(t);
	}
	
	public void delete(T t) {
		this.baseDao.delete(t);
	}

	public void updateManyObjects(List<T> objects) {
		this.baseDao.updateManyObjects(objects);
	}
	public List<T> createCriteriaQuery(Class<T> clazz) {
		return this.baseDao.createCriteriaQuery(clazz);
	}
	public List<T> createCriteriaQuery(Class<T> clazz, List<Criterion> criterions ,
			List<Projection> projections,List<Order> orders) {
		return this.baseDao.createCriteriaQuery(clazz, criterions, projections, orders);
	}
	public List<T> createCriteriaQuery(Class<T> clazz, List<Criterion> criterions ,
			List<Projection> projections,
			List<Order> orders,int first,int maxresult) {
		return this.baseDao.createCriteriaQuery(clazz, criterions, projections, orders, first, maxresult);
	}
	public void merge(T t) {
		this.baseDao.merge(t);
	}

}
