package service;

import java.util.List;

import pojo.ProductInfo;

public interface ProductinfoService extends BaseService<ProductInfo> {
	
	
	public abstract List<ProductInfo> findByBarcodeOrDesc(String keyword,Integer itype);
	public abstract ProductInfo findByBaecode(String barcode);
	public abstract List<ProductInfo> findByKeyword(String keyword);
	public abstract List<ProductInfo> findWebShopList(String keyword,int first,int max);
	public abstract List<ProductInfo> findWebShopList(String keyword);
}
