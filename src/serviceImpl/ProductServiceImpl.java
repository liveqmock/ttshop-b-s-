package serviceImpl;

import java.util.Date;
import java.util.List;

import pojo.Product;
import service.ProductService;
import tools.DateTool;

public class ProductServiceImpl extends BaseServiceImpl<Product> implements
		ProductService {

	public Product findByImei(String imei) throws Exception {
		Product product = null;
		String hql = "FROM Product AS p WHERE p.imei = ? AND p.quantity > 0 AND p.status = 1";
		List<Product> list = this.baseDao.findByHQL(hql, imei);
		if(list.size()>0){
			product = list.get(0);
		}
		return product;
	}
	
	public Product findByImeiAndWarehouse(String imei,String warehuose) throws Exception {
		Product product = null;
		String hql = "FROM Product AS p WHERE p.imei = ? AND p.warehouse = ? AND p.quantity > 0 AND p.status = 1";
		List<Product> list = this.baseDao.findByHQL(hql, imei, warehuose);
		if(list.size()>0){
			product = list.get(0);
		}
		return product;
	}
	
	public Product findByImeiAndWarehouseNoLimit(String imei,String warehuose) throws Exception {
		Product product = null;
		String hql = "FROM Product AS p WHERE p.imei = ? AND p.warehouse = ? AND p.status = 1";
		List<Product> list = this.baseDao.findByHQL(hql, imei, warehuose);
		if(list.size()>0){
			product = list.get(0);
		}
		return product;
	}

	public List<Product> findImeis(String barcode,String warehouse) throws Exception {
		String hql = "FROM Product AS p WHERE p.barcode = ? AND p.quantity >= ? AND p.warehouse = ? AND p.status = 1";
		List<Product> list = null;
		list = this.baseDao.findByHQL(hql, barcode,1,warehouse);
		return list;
	}

	public List<Product> findImeis(String barcode) throws Exception {
		String hql = "FROM Product AS p WHERE p.barcode = ? AND p.quantity >= ? AND p.status = 1";
		List<Product> list = null;
		list = this.baseDao.findByHQL(hql, barcode,1);
		return list;
	}
	
	public List<Product> findByKeyword(String keyword) throws Exception {
		String hql = "FROM Product AS p WHERE p.barcode LIKE ? AND p.status = 1";
		List<Product> list = null;
		list = this.baseDao.findByHQL(hql, "%"+keyword+"%");
		return list;
	}

	public void saleOrTransferProduct(List<Product> products) throws Exception {
		for (Product product : products) {
			product.setQuantity(0);
			product.setUpdatetime(DateTool.getInstance().DateToPattern2(new Date()));
		}
		this.baseDao.mergeManyObjects(products);
	}

	public void inboundProduct(List<Product> products) throws Exception {
		this.baseDao.mergeManyObjects(products);
	}

	public void calcelSaleOrTransferProduct(List<Product> products)
			throws Exception {
		for (Product product : products) {
			product.setQuantity(1);
			product.setUpdatetime(DateTool.getInstance().DateToPattern2(new Date()));
		}
		this.baseDao.mergeManyObjects(products);
	}

	public void cancelInboundProduct(List<Product> products) throws Exception {
		this.baseDao.deleteManyObjects(products);
	}

	public List<Object[]> findByDateAndWarehouse(String warehouse,String begindate,
			String finaldate) throws Exception {
		String sql = "select p.*,info.* from Product as p left outer join ProductInfo as info on(p.barcode=info.barcode) where p.warehouse = '"+
						warehouse+"' and p.updatetime >='"+begindate+"' and p.updatetime <='"+finaldate+"' and p.status=1 ";
		List<Object[]> list = this.baseDao.createSqlQuery(sql);
		return list;
	}

	

}
