package serviceImpl;

import java.util.List;

import pojo.Warehouse;
import service.WarehouseService;

public class WarehouseServiceImpl extends BaseServiceImpl<Warehouse> implements
		WarehouseService {

	public Warehouse finybyName(String wname) {
		Warehouse warehouse = null;
		String hql = "FROM Warehouse as w WHERE w.wname = ? AND w.status = 1";
		List<Warehouse> warehouses = this.findByHQL(hql, wname);
		if(warehouses.size()>0){
			warehouse = warehouses.get(0);
		}
		return warehouse;
	}

	public List<Warehouse> findByKeyword(String keyword) {
		List<Warehouse> warehouses = null;
		String hql = "FROM Warehouse as w WHERE (w.wname LIKE ? OR w.wadmin LIKE ?) AND w.status = 1";
		warehouses = this.findByHQL(hql, "%"+keyword+"%","%"+keyword+"%");
		return warehouses;
	}
	
	
	
	
}
