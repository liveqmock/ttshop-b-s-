package service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;

public interface BaseService<T> {
	
	public abstract void add(T t);
	public abstract void update(T t);
	public abstract void merge(T t);
	public abstract void delete(T t);
	public abstract void remove(Class<T> clazz,Integer id);
	
	public abstract T get(Class<T> clazz,Integer id);
	public abstract T load(Class<T> clazz,Integer id);
	
	public abstract List<T> findByHQL(String hql,Object...objects);
	public abstract List<T> findByHQL(String hql,int fisrtResult,int maxResult,Object...objects);
	public abstract List<Object[]> createHqlQuery(String hql);
	public abstract List<Object[]> createSqlQuery(String sql);
	public abstract List<T> createCriteriaQuery(Class<T> clazz);
	public abstract List<T> createCriteriaQuery(Class<T> clazz, List<Criterion> criterions ,List<Projection> projections,List<Order> orders);
	public abstract List<T> createCriteriaQuery(Class<T> clazz, List<Criterion> criterions ,List<Projection> projections,List<Order> orders,int first,int maxresult);
	
	public abstract void addManyObjects(List<T> objects);
	public abstract void updateManyObjects(List<T> objects);
	public abstract void deleteManyObjects(List<T> objects);
	
}
