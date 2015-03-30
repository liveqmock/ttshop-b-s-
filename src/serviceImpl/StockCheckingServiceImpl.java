package serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import pojo.StockChecking;
import service.StockCheckingService;

public class StockCheckingServiceImpl extends BaseServiceImpl<StockChecking> implements
		StockCheckingService {
	
	/**
	 * @param String date
	 * @return list<stockchecking>
	 */
	public List<StockChecking> findCheckingListByDate(String date) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Expression.between("updatetime", date+" 00:00:00", date+" 23:59:59"));
		criterions.add(Expression.eq("status", 1));
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.desc("id"));
		List<StockChecking> list = this.baseDao.createCriteriaQuery(StockChecking.class, criterions,null, orders);
		return list;
	}
	
	/**
	 * @param String date ,int wid
	 * @return list<stockchecking>
	 */
	public List<StockChecking> findCheckingListByDateAndWarehouse(String date,Integer warehouseid) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Expression.between("updatetime", date+" 00:00:00", date+" 23:59:59"));
		criterions.add(Expression.eq("status", 1));
		criterions.add(Expression.eq("warehouse.id", warehouseid));
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.desc("id"));
		List<StockChecking> list = this.baseDao.createCriteriaQuery(StockChecking.class, criterions,null, orders);
		return list;
	}
	
	

	
	
}
