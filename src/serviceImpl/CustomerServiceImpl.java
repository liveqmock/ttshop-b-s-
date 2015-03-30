package serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import pojo.Customer;
import service.CustomerService;
import tools.JsonTool;

public class CustomerServiceImpl extends BaseServiceImpl<Customer> implements
		CustomerService {

	public Customer findById(String customerId) {
		Customer customer = null;
		String hql = "FROM Customer as c WHERE c.customerId = ? and status =1";
		List<Customer> customers = this.findByHQL(hql, customerId);
		if(customers.size()>0 && customers.size()==1){
			customer = customers.get(0);
		}
		return customer;
	}

	public List<Customer> findByKeyword(String keyword) {
		List<Customer> customers = null;
		String hql = "FROM Customer as c WHERE (c.customerId LIKE ? OR c.customerName LIKE ? OR c.customerTel LIKE ? OR c.customerAddress LIKE ?) AND c.status = 1";
		customers = this.findByHQL(hql, "%"+keyword+"%","%"+keyword+"%","%"+keyword+"%","%"+keyword+"%");
		return customers;
	}

	public void createJson(List<Customer> customers,String filename) throws Exception {
		List<String[]> list = new ArrayList<String[]>();
		for (Customer customer : customers) {
			String[] strs = {customer.getCustomerName(),customer.getCustomerTel()+"|"+customer.getCustomerName(),customer.getCustomerAddress()};
			list.add(strs);
		}
		String json1 = "var customers = "+ JsonTool.getInstance().formList(list,"value","label","desc")+";";
		File file = new File(filename);
		if(!file.exists()){
			file.createNewFile();
		}
		FileOutputStream stream = new FileOutputStream(file);
		StringBuffer buffer = new StringBuffer(json1);
		stream.write(buffer.toString().getBytes("utf-8"));
		stream.flush();
		stream.close();
	}

	
	
	
}
