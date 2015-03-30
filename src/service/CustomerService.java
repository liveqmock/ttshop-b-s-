package service;

import java.util.List;

import pojo.Customer;

public interface CustomerService extends BaseService<Customer> {
	
	public abstract Customer findById(String customerId);
	public abstract List<Customer> findByKeyword(String keyword);
	public abstract void createJson(List<Customer> customers,String filename) throws Exception;
	
}
