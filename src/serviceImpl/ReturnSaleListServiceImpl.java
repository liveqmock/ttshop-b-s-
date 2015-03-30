package serviceImpl;

import java.util.List;

import pojo.ReturnSaleList;
import service.ReturnSaleListService;

public class ReturnSaleListServiceImpl extends BaseServiceImpl<ReturnSaleList> implements
		ReturnSaleListService {
	
	public List<Object[]> findByReturnNo(String returnno) {
		List<Object[]> objects = null;
		String sql = "select r.id,r.returnsaleno,r.barcode,p.pdesc,r.quantity,r.price,r.amount,r.status from returnsalelist as r left join productinfo as p on(r.barcode=p.barcode) " +
				"where r.returnsaleno='"+returnno+"' and r.status = 1";
		objects = this.createSqlQuery(sql);
		return objects;
	}

	public List<ReturnSaleList> listByReturnNo(String returnno) {
		List<ReturnSaleList> returnSaleLists = null;
		String hql = "FROM ReturnSaleList as r where r.returnsaleno = ? AND r.status = 1";
		returnSaleLists = this.findByHQL(hql, returnno);
		return returnSaleLists;
	}

	public List<Object[]> sumTotalreturnsalelist(String begindate, String enddate) {
		List<Object[]> objects = null;
		String sql = "select sum(r.quantity),sum(r.amount),r.barcode from returnsalelist as r " +
				"where r.updatetime>='"+begindate+"' and r.updatetime <='"+enddate+"' and r.status = 1 group by r.barcode";
		objects = this.createSqlQuery(sql);
		return objects;
	}

	public List<ReturnSaleList> findByreturnnoandbarcode(String returnno, String barcode) {
		List<ReturnSaleList> returnSaleLists = null;
		String hql = "FROM ReturnSaleList as r where r.returnsaleno = ? and r.barcode = ? AND r.status = 1";
		returnSaleLists = this.findByHQL(hql,returnno,barcode);
		return returnSaleLists;
	}

	public Integer findOldQuantityByInvoiceAndbarcode(String invoiceno,
			String barcode) {
		Integer quantity = null;
		List<Object[]> objects = null;
		String sql= "select sum(r.quantity),r.barcode from returnsalelist as r left outer join returnsale as rs on(rs.returnsaleno = r.returnsaleno) " +
				"where rs.invoiceno ='"+invoiceno+"' and r.barcode = '"+barcode+"' and r.status = 1 group by r.barcode";
		objects = this.baseDao.createSqlQuery(sql);
		System.out.println(objects);
		if(objects!=null && objects.size()>0){
			if(objects.get(0)[0]!=null){
				quantity = Integer.valueOf(objects.get(0)[0].toString());
			}
		}
		return quantity;
	}

	

	
}
