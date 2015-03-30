package service;

import java.util.List;

import pojo.Product;

public interface ProductService extends BaseService<Product> {
	
	public abstract Product findByImei(String imei) throws Exception;
	public abstract Product findByImeiAndWarehouse(String imei,String warehuose) throws Exception;
	public abstract Product findByImeiAndWarehouseNoLimit(String imei,String warehuose) throws Exception;
	public abstract List<Product> findImeis(String barcode,String warehouse) throws Exception;
	public abstract List<Product> findImeis(String barcode) throws Exception;
	public abstract List<Product> findByKeyword(String keyword) throws Exception;
	public abstract void saleOrTransferProduct(List<Product> products) throws Exception;
	public abstract void calcelSaleOrTransferProduct(List<Product> products) throws Exception;
	public abstract void inboundProduct(List<Product> products) throws Exception;
	public abstract void cancelInboundProduct(List<Product> products) throws Exception;
	public abstract List<Object[]> findByDateAndWarehouse(String warehouse,String begindate,String finaldate) throws Exception;
}
