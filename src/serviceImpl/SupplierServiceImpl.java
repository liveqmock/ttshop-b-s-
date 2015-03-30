package serviceImpl;

import java.util.List;

import pojo.Supplier;
import service.SupplierService;

public class SupplierServiceImpl extends BaseServiceImpl<Supplier> implements
		SupplierService {

	public Supplier findById(String supplierid) {
		Supplier supplier = null;
		String hql = "FROM Supplier as s WHERE s.supplierId = ? AND s.status =1";
		List<Supplier> suppliers = this.findByHQL(hql, supplierid);
		if(suppliers.size()>0 && suppliers.size()==1){
			supplier = suppliers.get(0);
		}
		return supplier;
	}
	
	public List<Supplier> findByKeyword(String keyword) {
		List<Supplier> suppliers = null;
		String hql = "FROM Supplier as supplier WHERE (supplier.supplierId LIKE ? OR supplier.supplierName LIKE ? OR supplier.supplierTel LIKE ? OR supplier.supplierAddress LIKE ?) AND supplier.status =1";
		suppliers = this.findByHQL(hql, "%"+keyword+"%","%"+keyword+"%","%"+keyword+"%","%"+keyword+"%");
		return suppliers;
	}

	
	
	
}
