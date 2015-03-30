package service;

import java.util.List;

import pojo.ReturnSaleList;

public interface ReturnSaleListService extends BaseService<ReturnSaleList> {
	
	public abstract List<Object[]> findByReturnNo(String returnno);
	public abstract List<ReturnSaleList> listByReturnNo(String returnno);
	public abstract List<ReturnSaleList> findByreturnnoandbarcode(String returnno,String barcode);
	public abstract Integer findOldQuantityByInvoiceAndbarcode(String invoiceno,String barcode);
	public abstract List<Object[]> sumTotalreturnsalelist(String begindate,String enddate);
	
	
}
