package serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import pojo.InnoteList;
import pojo.ProductInfo;
import pojo.Pstock;
import pojo.ReturnSaleList;
import pojo.SaleRecordList;
import pojo.TransferList;
import service.ProductinfoService;
import service.PstockService;
import tools.DateTool;

public class PstockServiceImpl extends BaseServiceImpl<Pstock> implements
		PstockService {
	@Resource(name="productinfoService")
	private ProductinfoService productinfoService;
	
	public Pstock findbyBarcodeAndWarehouse(String barcode,String warehouse) {
		List<Pstock> pstocks = null;
		Pstock pstock = null;
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Expression.eq("barcode", barcode));
		criterions.add(Expression.eq("warehouse", warehouse));
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.desc("id"));
		pstocks = this.baseDao.createCriteriaQuery(Pstock.class,criterions,null,orders);
		if(!pstocks.isEmpty()){
			pstock = pstocks.get(0);
		}
		return pstock;
	}
	public Pstock findbyBarcode(String barcode) {
		List<Pstock> pstocks = null;
		Pstock pstock = null;
		//String hql = "FROM Pstock as s WHERE s.barcode = ? AND s.status = 1";
		//pstocks = this.baseDao.findByHQL(hql, barcode);
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Expression.eq("barcode", barcode));
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.desc("id"));
		pstocks = this.baseDao.createCriteriaQuery(Pstock.class,criterions,null,orders);
		if(pstocks.size()>0){
			pstock = pstocks.get(0);
		}
		return pstock;
	}
	
	public List<Object[]> listStock(Integer lowestQuantity,String warehouse) {
		String sql = "SELECT s.id,s.barcode,p.pdesc,s.warehouse,s.quantity,s.updatetime,s.status FROM pstock AS s LEFT OUTER JOIN productinfo AS p ON(s.barcode=p.barcode) WHERE "+
					 "s.status = 1 AND s.quantity >= "+lowestQuantity+" AND s.warehouse ='"+warehouse+"'";
		List<Object[]> objects = null;
		objects = this.baseDao.createSqlQuery(sql);
		return objects;
	}
	/**
	 * 库存列表 , 有 warehouse 
	 * 0 s.id,
	 * 1 s.barcode,
	 * 2 desc,
	 * 3 warehouse,
	 * 4 quantity,
	 * 5 updatetime,
	 * 6 status,
	 * 7 itype
	 */
	public List<Object[]> listStock(String warehouse) {
		String sql = "SELECT s.id,s.barcode,p.pdesc,s.warehouse,s.quantity,s.updatetime,s.status,p.itype,p.status as ps FROM pstock AS s LEFT OUTER JOIN productinfo AS p ON(s.barcode=p.barcode) WHERE "+
				"s.status = 1 and p.status = 1 AND s.warehouse ='"+warehouse+"'";
		List<Object[]> objects = null;
		objects = this.baseDao.createSqlQuery(sql);
		return objects;
	}
	/**
	 * 库存表,全部,不带 warehouse 参数
	 */
	public List<Object[]> listStock() {
		String sql = "SELECT s.id,s.barcode,p.pdesc,s.warehouse,sum(s.quantity),s.updatetime,s.status,p.itype,p.status as ps FROM pstock AS s " +
				"LEFT OUTER JOIN productinfo AS p ON(s.barcode=p.barcode) " +
				"WHERE s.status = 1 and p.status = 1 group by s.barcode order by s.barcode desc";
		List<Object[]> objects = null;
		objects = this.baseDao.createSqlQuery(sql);
		return objects;
	}
	
	public List<Object[]> listStockAllWarehouse(Integer lowestQuantity) {
		String sql = "SELECT s.id,s.barcode,p.pdesc,s.warehouse,s.quantity,s.updatetime,s.status FROM pstock AS s LEFT OUTER JOIN productinfo AS p ON(s.barcode=p.barcode) WHERE "+
				"s.status = 1 AND s.quantity >= "+lowestQuantity;
		List<Object[]> objects = null;
		objects = this.baseDao.createSqlQuery(sql);
		return objects;
	}
	public void updateTransfer(List<TransferList> transferLists,
			String warehouse) {
		List<Pstock> pstocks = new ArrayList<Pstock>();
		for (int i = 0; i < transferLists.size(); i++) {
			TransferList transferList = transferLists.get(i);
			String barcode = transferList.getBarcode();
			Integer quantity = transferList.getQuantity();
			Pstock pstock = this.findbyBarcodeAndWarehouse(barcode, warehouse);
			if(pstock==null){
				ProductInfo productInfo = this.productinfoService.findByBaecode(barcode);
				if(productInfo!=null){
					pstock = new Pstock(productInfo, warehouse, -quantity);
					this.baseDao.add(pstock);
					continue;
				}
			}
			pstock.setQuantity(pstock.getQuantity()-quantity);
			pstocks.add(pstock);
		}
		this.baseDao.updateManyObjects(pstocks);
	}
	
	/**
	 * 与 updateTransfer 一起使用的
	 */
	public void recieveTransfer(List<TransferList> transferLists,
			String warehouse) {
		List<Pstock> pstocks = new ArrayList<Pstock>();
		for (int i = 0; i < transferLists.size(); i++) {
			TransferList transferList = transferLists.get(i);
			String barcode = transferList.getBarcode();
			Integer quantity = transferList.getQuantity();
			Pstock pstock = this.findbyBarcodeAndWarehouse(barcode, warehouse);
			if(pstock==null){
				ProductInfo productInfo = this.productinfoService.findByBaecode(barcode);
				if(productInfo!=null){
					pstock = new Pstock(productInfo, warehouse, quantity);
					this.baseDao.add(pstock);
					continue;
				}
			}
			pstock.setQuantity(pstock.getQuantity()+quantity);
			pstocks.add(pstock);
		}
		this.baseDao.updateManyObjects(pstocks);
	}
	
	public void updateSale(List<SaleRecordList> saleRecordLists,String warehouse) {
		List<Pstock> pstocks = new ArrayList<Pstock>();
		for (int i = 0; i < saleRecordLists.size(); i++) {
			SaleRecordList saleRecordList = saleRecordLists.get(i);
			String barcode = saleRecordList.getBarcode();
			Integer quantity = saleRecordList.getQuantity();
			Pstock pstock = this.findbyBarcodeAndWarehouse(barcode, warehouse);
			if(pstock==null){
				ProductInfo productInfo = this.productinfoService.findByBaecode(barcode);
				if(productInfo!=null){
					pstock = new Pstock(productInfo, warehouse, -quantity);
					this.baseDao.add(pstock);
					continue;
				}
			}
			pstock.setQuantity(pstock.getQuantity()-quantity);
			pstocks.add(pstock);
		}
		this.baseDao.updateManyObjects(pstocks);
	}
	
	/**
	 * 修改入货单时更新库存
	 */
	public void updateChangeInbound(Pstock pstock,Integer newquantity) {
		Pstock pstock2 = this.get(Pstock.class, pstock.getId());
		pstock2.setQuantity(newquantity);
		pstock2.setUpdateTime(DateTool.getInstance().DateToPattern2(new Date()));
		this.baseDao.update(pstock2);
	}
	
	public void updateReturn(List<ReturnSaleList> returnSaleLists,
			String warehouse) {
		for (int i = 0; i < returnSaleLists.size(); i++) {
			ReturnSaleList returnSaleList = returnSaleLists.get(i);
			String barcode = returnSaleList.getBarcode();
			Integer quantity = returnSaleList.getQuantity();
			Pstock pstock = this.findbyBarcodeAndWarehouse(barcode, warehouse);
			if(pstock==null){
				ProductInfo productInfo = this.productinfoService.findByBaecode(barcode);
				if(productInfo!=null){
					pstock = new Pstock(productInfo, warehouse, quantity);
					this.baseDao.add(pstock);
					continue;
				}
			}else{
				pstock.setQuantity(pstock.getQuantity()+quantity);
				this.baseDao.update(pstock);
			}
		}
	}
	public void updateInbound(List<InnoteList> innoteLists, String warehouse) {
		for (int i = 0; i < innoteLists.size(); i++) {
			InnoteList innoteList = innoteLists.get(i);
			String barcode = innoteList.getBarcode();
			Integer quantity = innoteList.getQuantity();
			Pstock pstock = this.findbyBarcodeAndWarehouse(barcode, warehouse);
			if(pstock==null){
				ProductInfo productInfo = this.productinfoService.findByBaecode(barcode);
				if(productInfo!=null){
					pstock = new Pstock(productInfo, warehouse, quantity);
					this.baseDao.add(pstock);
					continue;
				}
			}else{
				pstock.setQuantity(pstock.getQuantity()+quantity);
				this.baseDao.update(pstock);
			}
		}
	}
}
