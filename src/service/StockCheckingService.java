package service;

import java.util.List;

import pojo.StockChecking;

public interface StockCheckingService extends BaseService<StockChecking> {
	
	public List<StockChecking> findCheckingListByDate(String date);
	public List<StockChecking> findCheckingListByDateAndWarehouse(String date,Integer warehouseid);
	
}
