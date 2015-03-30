package service;

import java.util.List;

import pojo.ReturnSale;

public interface ReturnSaleService extends BaseService<ReturnSale> {
	
	public abstract List<ReturnSale> listReturnSales(String bdate,String enddate);
	public abstract List<ReturnSale> listReturnSales(String warehouse,String bdate,String enddate);
	public abstract ReturnSale findByReturnsale(String returnno);
	public abstract ReturnSale findByInvoiceno(String invoiceno);
	public abstract List<Object[]> sumTotalReturnSale(String begindate,String enddate);
	
}
