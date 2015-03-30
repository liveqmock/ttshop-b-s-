package service;

import java.util.List;

import pojo.SaleRecord;

public interface SaleService extends BaseService<SaleRecord> {
	
	public abstract SaleRecord findByInvoiceNo(String invoiceNo);
	public abstract List<SaleRecord> listAll();
	public abstract List<SaleRecord> findBykeyword(String keyword);
	public abstract List<SaleRecord> findByCustomer(String customer);
	public abstract List<SaleRecord> findByOperator(String operator);
	public abstract List<SaleRecord> findBykeyword(String warehuse,String keyword,String begindate,String enddate);
	public abstract Integer findBykeywordallrow(String invoiceno,String begindate,String enddate);
	public abstract Integer findBykeywordallrow(String invoiceno,String warehouse,String begindate,String enddate); //´øwarehouse
	public abstract List<SaleRecord> findBykeyword(String keyword,String begingdate ,String enddate,int firstResult,int maxresult);
	public abstract List<SaleRecord> findBykeyword(String keyword,String warehouse,String begingdate ,String enddate,int firstResult,int maxresult);//´ø warehouse
	public abstract List<SaleRecord> findHandUp(String warehouse);
	public abstract List<SaleRecord> listByCreateTime(String begindate,String enddate);
	public abstract List<Object[]> sumTotalBydate(String keyword,String begindate,String enddate);
	public abstract List<Object[]> sumTotalBydateNoGroup(String keyword,String begindate,String enddate);
	public abstract List<Object[]> sumTotalBydateByType(String keyword,String begindate,String enddate);
	public abstract List<Object[]> sumTotalBydateByProduct(String keyword,String begindate,String enddate);
	public abstract List<Object[]> sumTotalBydateByCustomer(String keyword,String begindate,String enddate);
	public abstract List<Object[]> sumSaleCost(String keyword,String begindate,String enddate);
	
	
}
