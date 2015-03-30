package dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;

public interface BaseDao<T> {
	
	public abstract void add(T t) throws RuntimeException;
	public abstract void update(T t) throws RuntimeException;
	public abstract void merge(T t) throws RuntimeException;
	public abstract void delete(T t) throws RuntimeException;
	public abstract void remove(Class<T> clazz,Integer id) throws RuntimeException;
	public abstract T get(Class<T> clazz,Integer id) throws RuntimeException;
	public abstract T load(Class<T> clazz,Integer id) throws RuntimeException;
	public abstract Object getUniqueResult(String hql) throws RuntimeException;
	
	public abstract List<T> findByHQL(String hql,Object...objects) throws RuntimeException;
	public abstract List<T> findByHQL(String hql,int fisrtResult,int maxResult,Object...objects) throws RuntimeException;
	public abstract Query createSQLQuery(String sql) throws RuntimeException;
	public abstract List<Object[]> createHqlQuery(String hql) throws RuntimeException;
	public abstract List<T> createCriteriaQuery(Class<T> clazz) throws RuntimeException;
	public abstract List<T> createCriteriaQuery(Class<T> clazz, List<Criterion> criterions ,List<Projection> projections,List<Order> orders) throws RuntimeException;
	public abstract List<T> createCriteriaQuery(Class<T> clazz, List<Criterion> criterions ,List<Projection> projections,List<Order> orders,int first,int maxresult) throws RuntimeException;
	public abstract List<Object[]> createSqlQuery(String sql) throws RuntimeException;
	
	public abstract void addManyObjects(List<T> objects) throws RuntimeException;
	public abstract void updateManyObjects(List<T> objects) throws RuntimeException;
	public abstract void mergeManyObjects(List<T> objects) throws RuntimeException;
	public abstract void deleteManyObjects(List<T> objects) throws RuntimeException;
	
	
}
