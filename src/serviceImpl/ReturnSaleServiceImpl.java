package serviceImpl;

import java.util.List;

import pojo.ReturnSale;
import service.ReturnSaleService;

public class ReturnSaleServiceImpl extends BaseServiceImpl<ReturnSale> implements
		ReturnSaleService {

	public List<ReturnSale> listReturnSales(String bdate, String enddate){
		List<ReturnSale> returnSales = null;
		String hql = "FROM ReturnSale AS s WHERE s.updatetime >= ? AND s.updatetime <= ? ORDER BY s.id DESC";
		returnSales = this.findByHQL(hql,bdate,enddate);
		return returnSales;
	}

	public ReturnSale findByReturnsale(String returnno){
		ReturnSale returnSale =null;
		String hql = "FROM ReturnSale AS s WHERE s.returnsaleno = ? and s.status = 1";
		List<ReturnSale> list = this.findByHQL(hql, returnno);
		if(!list.isEmpty()){
			returnSale = list.get(0);
		}
		return returnSale;
	}

	public List<Object[]> sumTotalReturnSale(String begindate, String enddate){
		List<Object[]> objects = null;
		String sql = "select sum(r.totalquantity),sum(r.totalamount) from returnsale as r where r.updatetime >='"+begindate+"' and r.updatetime <='"+enddate+"' and r.status = 1 group by r.status";
		objects = this.createSqlQuery(sql);
		return objects;
	}

	public ReturnSale findByInvoiceno(String invoiceno){
		ReturnSale returnSale =null;
		String hql = "FROM ReturnSale AS s WHERE s.invoiceno = ? and s.status = 1";
		List<ReturnSale> list = this.findByHQL(hql, invoiceno);
		if(list!=null && list.size()>0){
			returnSale = list.get(0);
		}
		return returnSale;
	}

	public List<ReturnSale> listReturnSales(String warehouse, String bdate,
			String enddate) {
		List<ReturnSale> returnSales = null;
		String hql = "FROM ReturnSale AS s WHERE s.warehouse = ? and s.updatetime >= ? AND s.updatetime <= ? ORDER BY s.id DESC";
		returnSales = this.findByHQL(hql,warehouse,bdate,enddate);
		return returnSales;
	}

	

	
}
