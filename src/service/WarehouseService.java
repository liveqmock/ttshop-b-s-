package service;

import java.util.List;

import pojo.Warehouse;

public interface WarehouseService extends BaseService<Warehouse> {
	
	public abstract Warehouse finybyName(String wname);
	public abstract List<Warehouse> findByKeyword(String keyword);
	
}
