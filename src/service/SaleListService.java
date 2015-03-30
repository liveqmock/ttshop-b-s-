package service;

import java.util.List;

import pojo.SaleRecordList;

public interface SaleListService extends BaseService<SaleRecordList> {
	
	public abstract List<SaleRecordList> listByInvoiceNo(String invoiceNo);
	public abstract List<Object[]> countSale(String begindate,String enddate);
	public abstract List<SaleRecordList> findByImei(String imei);
	public abstract List<Object[]> backup();
	public abstract List<SaleRecordList> listAll();
	public abstract List<SaleRecordList> listByWarehouseAndDate(String warehouse,String begindate,String enddate);
	public abstract List<SaleRecordList> listByWarehouseAndDate(String warehouse,String begindate,String enddate,int firstresult,int maxresult);
	public abstract List<SaleRecordList> listByWarehouseAndDate(String begindate,String enddate);
	public abstract List<SaleRecordList> listByWarehouseAndDate(String begindate,String enddate,int firstresult,int maxresult);
	public abstract String findAllImes(String barcode);
	public abstract List<Object[]> sumByDateGroupbyType(String begindate,String enddate);
//	public abstract List<Object[]> listRecordAndDetailByInvoice(String invoiceNo,String begindate,String enddate);
	public abstract Integer sumTotalByBarcode(String barcode);
	
}
