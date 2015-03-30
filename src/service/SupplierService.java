package service;

import java.util.List;

import pojo.Supplier;

public interface SupplierService extends BaseService<Supplier> {
	
	public abstract Supplier findById(String supplierid);
	public abstract List<Supplier> findByKeyword(String keyword);
	
}
