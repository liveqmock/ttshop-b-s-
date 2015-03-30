package serviceImpl;

import java.util.List;

import pojo.SaleRecord;
import service.SaleService;

public class SaleServiceImpl extends BaseServiceImpl<SaleRecord> implements
		SaleService {
	
	/**
	 * 通过 invoiceno 获取销售单
	 */
	public SaleRecord findByInvoiceNo(String invoiceNo) {
		SaleRecord saleRecord = null;
		String hql = "FROM SaleRecord as s WHERE s.invoiceno = ?";
		List<SaleRecord> list = this.baseDao.findByHQL(hql, invoiceNo);
		if(list.size()>0){
			saleRecord = list.get(0);
		}
		return saleRecord;
	}
	
	/**
	 * 获取销售记录,参数:时间段
	 */
	public List<SaleRecord> listByCreateTime(String begindate, String enddate) {
		List<SaleRecord> list = null;
		String hql = "FROM SaleRecord as s WHERE s.createtime >= ? AND s.createtime <= ?";
		list = this.baseDao.findByHQL(hql, begindate, enddate+"59:59:59");
		return list;
	}
	
	/**
	 * 获取销售记录,关键字查询: invoiceno & customerid
	 */
	public List<SaleRecord> findBykeyword(String keyword) {
		List<SaleRecord> list = null;
		String hql = "FROM SaleRecord as s WHERE (s.invoiceno like ? OR s.customerid like ?) ORDER BY s.id DESC LIMIT 0,500";
		list = this.baseDao.findByHQL(hql, "%"+keyword+"%","%"+keyword+"%");
		return list;
	}
	/**
	 * 获取销售记录,参数:customerid
	 */
	public List<SaleRecord> findByCustomer(String customer) {
		List<SaleRecord> list = null;
		String hql = "FROM SaleRecord as s WHERE s.customerid = ? AND s.status = 1 ORDER BY s.id DESC";
		list = this.baseDao.findByHQL(hql, customer);
		return list;
	}
	/**
	 * 获取总行数
	 */
	public Integer findBykeywordallrow(String invoiceno,String begingdate ,String enddate) {
		Integer allrows = null;
		String hql = "select count(s.id) FROM SaleRecord as s WHERE s.invoiceno like '"+invoiceno+"%"+"' AND s.createtime >= '"+begingdate+"' AND s.createtime <= '"+enddate+"' ORDER BY s.id DESC";
		allrows = (Integer) this.baseDao.getUniqueResult(hql);
		return allrows;
	}
	/**
	 * 获取销售单,分页显示,
	 */
	public List<SaleRecord> findBykeyword(String keyword,String begingdate ,String enddate,int firseResult,int maxresult) {
		List<SaleRecord> list = null;
		//int firstResult = (currentPage-1)*maxresult;
		String hql = "FROM SaleRecord as s WHERE s.invoiceno like ? and s.createtime >=? and s.createtime <= ? ORDER BY s.id DESC";
		list = this.baseDao.findByHQL(hql,firseResult,maxresult,keyword+"%",begingdate,enddate);
		return list;
	}

	public List<Object[]> sumTotalBydate(String keyword,String begindate,
			String enddate) {
		List<Object[]> list = null;
		String sql = "select sum(s.totalquantity),sum(s.totalamount),s.currency,s.paidtype,count(s.id) from salerecord as s where s.createtime >='"+begindate+
				"' and s.createtime<= '"+enddate+"' and s.invoiceno like '%"+keyword+"%' and s.status>=1 group by s.currency,s.paidtype";
		list = this.baseDao.createSqlQuery(sql);
		return list;
	}
	
	public List<Object[]> sumTotalBydateNoGroup(String keyword,String begindate,
			String enddate) {
		List<Object[]> list = null;
		String sql = "select sum(s.totalquantity),sum(s.totalamount) from salerecord as s where s.createtime >='"+begindate+
				"' and s.createtime<= '"+enddate+"' and s.invoiceno like '%"+keyword+"%' and s.status=1";
		list = this.baseDao.createSqlQuery(sql);
		return list;
	}
	
	public List<SaleRecord> listAll() {
		String hql = "FROM SaleRecord as s ORDER BY s.id desc";
		List<SaleRecord> list = this.baseDao.findByHQL(hql);
		return list;
	}

	public List<Object[]> sumTotalBydateByType(String keyword,
			String begindate, String enddate) {
		List<Object[]> list = null;
		String sql = "select sum(s.quantity),sum(s.amount),p.ptype from salerecordlist as s left outer join productinfo as p on(s.barcode=p.barcode) where s.updatetime >='"+begindate+
				"' and s.updatetime<= '"+enddate+"' and s.invoiceno like '%"+keyword+"%' and s.status=1 group by p.ptype";
		list = this.baseDao.createSqlQuery(sql);
		return list;
	}
	
	public List<Object[]> sumTotalBydateByProduct(String keyword,
			String begindate, String enddate) {
		List<Object[]> list = null;
		String sql = "select sum(s.quantity),sum(s.amount),s.barcode,s.pdesc from salerecordlist as s where s.updatetime >='"+begindate+
				"' and s.updatetime<= '"+enddate+"' and s.invoiceno like '"+keyword+"%' and s.status=1 group by s.barcode";
		list = this.baseDao.createSqlQuery(sql);
		return list;
	}

	public List<Object[]> sumTotalBydateByCustomer(String keyword,
			String begindate, String enddate) {
		List<Object[]> list = null;
		String sql = "select sum(s.totalquantity),sum(s.totalamount),s.customerid from salerecord as s where s.createtime >='"+begindate+
				"' and s.createtime<= '"+enddate+"' and s.invoiceno like '%"+keyword+"%' and s.status=1 group by s.customerid";
		list = this.baseDao.createSqlQuery(sql);
		return list;
	}

	public List<Object[]> sumSaleCost(String keyword, String begindate,
			String enddate) {
		List<Object[]> list = null;
		String sql = "SELECT ROUND(SUM(n.quantity*n.price)/SUM(n.quantity),1) FROM innotelist AS n "+
				    " WHERE n.updatetime>='"+begindate+"' AND n.updatetime<='"+enddate+"' AND n.status=1";
		list = this.baseDao.createSqlQuery(sql);
		return list;
	}

	public List<SaleRecord> findBykeyword(String warehuse, String keyword,
			String begindate, String enddate) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SaleRecord> findHandUp(String warehouse) {
		String hql = "FROM SaleRecord as s WHERE s.warehouse = ? and s.invoiceno LIKE ? and s.status = 2"; //2为设定的挂起状态
		List<SaleRecord> saleRecords = this.baseDao.findByHQL(hql,warehouse,"%Handup%");
		return saleRecords;
	}

	public List<SaleRecord> findByOperator(String operator) {
		List<SaleRecord> list = null;
		String hql = "from SaleRecord as s where s.operatorid = ? and s.status = 1 order by s.id desc";
		list = this.baseDao.findByHQL(hql, operator);
		return list;
	}
	/**
	 * 查找总行数,带 warehouse 
	 */
	public Integer findBykeywordallrow(String invoiceno, String warehouse,
			String begindate, String enddate) {
		Integer allrows = null;
		String hql = "select count(s.id) FROM SaleRecord as s " +
				"WHERE s.invoiceno like '"+invoiceno+"%"+"' AND s.warehouse = '"+warehouse+"' " +
						"AND s.createtime >= '"+begindate+"' AND s.createtime <= '"+enddate+"' ORDER BY s.id DESC";
		allrows = (Integer) this.baseDao.getUniqueResult(hql);
		return allrows;
	}
	/**
	 * 带 warehouse 参数
	 */
	public List<SaleRecord> findBykeyword(String keyword, String warehouse,
			String begingdate, String enddate, int firstResult, int maxresult) {
		List<SaleRecord> list = null;
		String hql = "FROM SaleRecord as s WHERE s.invoiceno like ? and s.warehouse = ? and s.createtime >=? and s.createtime <= ? ORDER BY s.id DESC";
		list = this.baseDao.findByHQL(hql,firstResult,maxresult,keyword+"%",warehouse,begingdate,enddate);
		return list;
	}

	
	

	
}
