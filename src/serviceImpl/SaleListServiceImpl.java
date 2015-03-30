package serviceImpl;

import java.util.ArrayList;
import java.util.List;
import pojo.SaleRecordList;
import service.SaleListService;

public class SaleListServiceImpl extends BaseServiceImpl<SaleRecordList> implements
		SaleListService {

	public List<SaleRecordList> listByInvoiceNo(String invoiceNo) {
		List<SaleRecordList> list = null;
		String hql ="FROM SaleRecordList as s WHERE s.invoiceno = ?";
		list = this.baseDao.findByHQL(hql, invoiceNo);
		return list;
	}
	
	public List<Object[]> countSale(String begindate, String enddate) {
		String sql = "SELECT saleRecordList.remark,saleRecordList.barcode,productinfo.pdesc,SUM(saleRecordList.quantity),productinfo.itype FROM saleRecordList LEFT OUTER JOIN productinfo ON(saleRecordList.barcode=productinfo.barcode) WHERE saleRecordList.updatetime >= '"+begindate+"' AND saleRecordList.updatetime<= '"+enddate+
				"'AND saleRecordList.status = 1 GROUP BY saleRecordList.barcode ORDER BY SUM(saleRecordList.quantity) DESC";
		List<Object[]> list = this.createSqlQuery(sql);
		return list;
	}

	public List<SaleRecordList> findByImei(String imei) {
		List<SaleRecordList> list = null;
		String hql = "FROM SaleRecordList as s WHERE s.remark like ?";
		list = this.baseDao.findByHQL(hql, "%"+imei+"%");
		return list;
	}

	public List<Object[]> backup() {
		List<Object[]> list = null;
		String sql ="SELECT salerecordlist.invoiceno,salerecordlist.barcode,salerecordlist.pdesc,salerecordlist.remark,salerecordlist.quantity,salerecordlist.updatetime,salerecord.customerid,customer.customerName FROM saleRecordList " +
				"LEFT OUTER JOIN salerecord ON(salerecord.invoiceno=salerecordlist.invoiceno)" +
				"LEFT OUTER JOIN customer ON(customer.customerId=salerecord.customerid) WHERE salerecordlist.status = 1";
		list = this.baseDao.createSqlQuery(sql);
		return list;
	}

	public List<Object[]> sumByDateGroupbyType(String begindate, String enddate) {
		String sql = "select sum(s.quantity),sum(s.amount),p.barcode,p.pdesc,t.id,t.typename from salerecordlist as s left outer join productinfo as p on(s.barcode=p.barcode) "+
					 " left outer join ptype as t on(p.ptype=t.typename) where s.updatetime >='"+begindate+"' and s.updatetime <= '"+
					 enddate+"' and s.status=1 group by t.typename";
		List<Object[]> list = this.baseDao.createSqlQuery(sql);
		return list;
	}
	
	
/*	*//**
	 * 	System.out.print(objects[0]+"-"); //invoiceno
		System.out.print(objects[1]+"-"); //barcode
		System.out.print(objects[2]+"-"); //desc
		System.out.print(objects[3]+"-"); //imeis
		System.out.print(objects[4]+"-"); //updatetime
		System.out.print(objects[5]+"-"); //status
		System.out.print(objects[6]+"-"); // warehouse
		System.out.print(objects[7]+"-"); // customer
		System.out.println(objects[8]+"-"); //operator
	 *//*
	public List<Object[]> listRecordAndDetailByInvoice(String invoiceNo,
			String begindate, String enddate) {
		String sql = "select s.invoiceno,s.barcode,s.pdesc,s.remark,s.updatetime,s.status,r.warehouse,r.customerid,r.operatorid from salerecordlist as s left outer join salerecord as r on(s.invoiceno=r.invoiceno) "+
				 " where s.updatetime >='"+begindate+"' and s.updatetime <= '"+
				 enddate+"' and s.invoiceno like '"+invoiceNo+"%' and s.status=1";
		List<Object[]> list = this.baseDao.createSqlQuery(sql);
		return list;
	}*/

	public List<SaleRecordList> listAll() {
		String hql = "from SaleRecordList as s";
		List<SaleRecordList> saleRecordLists = this.findByHQL(hql);
		return saleRecordLists;
	}

	public String findAllImes(String barcode) {
		String hql ="from SaleRecordList as s where s.barcode = ? and s.status = 1";
		List<SaleRecordList> list = this.baseDao.findByHQL(hql, barcode);
		String imeis = "";
		for (int i = 0 ;i<list.size();i++) {
			SaleRecordList saleRecordList = list.get(i);
			String imei = saleRecordList.getRemark();
			if (!imei.trim().equals("")) {
				if(i<list.size()-1){	
					imeis += imei+", ";
				}else{
					imeis += imei;
				}
			}
		}
		return imeis;
	}

	public Integer sumTotalByBarcode(String barcode) {
		Integer quan = 0;
		String sql = "SELECT SUM(s.quantity),s.barcode FROM salerecordlist AS s WHERE s.barcode = '"+barcode+"' AND s.status=1 GROUP BY s.barcode";
		List<Object[]> objects = null;
		objects = this.baseDao.createSqlQuery(sql);
		if(objects!=null && objects.size()>0){
			String q = objects.get(0)[0].toString();
			quan = Integer.valueOf(q);
		}
		return quan;
	}
	
	public List<SaleRecordList> listByWarehouseAndDate(String warehouse,
			String begindate, String enddate) {
		List<SaleRecordList> list = new ArrayList<SaleRecordList>();
		List<Object[]> list1 = null;
		String sql ="select s.id,s.invoiceno,s.invoiceline,s.barcode,s.pdesc,s.remark,s.quantity,s.price,s.amount,s.updatetime,s.status FROM SaleRecordList as s " +
				" left outer join SaleRecord as r on (s.invoiceno = r.invoiceno) " +
				" WHERE r.warehouse = '"+warehouse+"' and s.updatetime >='"+begindate+"' and s.updatetime <='"+enddate+"' order by s.id desc";
		list1 = this.baseDao.createSqlQuery(sql);
		if(list1!=null){
			for (int i = 0; i < list1.size(); i++) {
				Object[] objects = list1.get(i);
				SaleRecordList saleRecordList = new SaleRecordList();
				saleRecordList.setId(Integer.valueOf(objects[0].toString()));
				saleRecordList.setInvoiceno(objects[1].toString());
				saleRecordList.setInvoiceline(Integer.valueOf(objects[2].toString()));
				saleRecordList.setBarcode(objects[3].toString());
				saleRecordList.setPdesc(objects[4].toString());
				saleRecordList.setRemark(objects[5]==null?"":objects[5].toString());
				saleRecordList.setQuantity(Integer.valueOf(objects[6].toString()));
				saleRecordList.setPrice(Double.valueOf(objects[7].toString()));
				saleRecordList.setAmount(Double.valueOf(objects[8].toString()));
				saleRecordList.setUpdatetime(objects[9].toString());
				saleRecordList.setStatus(Integer.valueOf(objects[10].toString()));
				list.add(saleRecordList);
			}
		}
		return list;
	}
	
	/**
	 * 明细,不带 warehouse 参数
	 */
	public List<SaleRecordList> listByWarehouseAndDate(String begindate,
			String enddate) {
		List<SaleRecordList> lists = null;
		String hql = "from SaleRecordList as s where s.updatetime >= ? and s.updatetime <=? order by s.id";
		lists = this.baseDao.findByHQL(hql, begindate,enddate);
		return lists;
	}
	
	/**
	 * 查明细,指定warehouse,带分页
	 */
	public List<SaleRecordList> listByWarehouseAndDate(String warehouse,
			String begindate, String enddate, int firstresult, int maxresult) {
		List<SaleRecordList> list = new ArrayList<SaleRecordList>();
		List<Object[]> list1 = null;
		String sql ="select s.id,s.invoiceno,s.invoiceline,s.barcode,s.pdesc,s.remark,s.quantity,s.price,s.amount,s.updatetime,s.status FROM SaleRecordList as s " +
				" left outer join SaleRecord as r on (s.invoiceno = r.invoiceno) " +
				" WHERE r.warehouse = '"+warehouse+"' and s.updatetime >='"+begindate+"' and s.updatetime <='"+enddate+"' order by s.id desc limit "+firstresult+","+maxresult;
		list1 = this.baseDao.createSqlQuery(sql);
		if(list1!=null){
			for (int i = 0; i < list1.size(); i++) {
				Object[] objects = list1.get(i);
				SaleRecordList saleRecordList = new SaleRecordList();
				saleRecordList.setId(Integer.valueOf(objects[0].toString()));
				saleRecordList.setInvoiceno(objects[1].toString());
				saleRecordList.setInvoiceline(Integer.valueOf(objects[2].toString()));
				saleRecordList.setBarcode(objects[3].toString());
				saleRecordList.setPdesc(objects[4].toString());
				saleRecordList.setRemark(objects[5]==null?"":objects[5].toString());
				saleRecordList.setQuantity(Integer.valueOf(objects[6].toString()));
				saleRecordList.setPrice(Double.valueOf(objects[7].toString()));
				saleRecordList.setAmount(Double.valueOf(objects[8].toString()));
				saleRecordList.setUpdatetime(objects[9].toString());
				saleRecordList.setStatus(Integer.valueOf(objects[10].toString()));
				list.add(saleRecordList);
			}
		}
		return list;
	}

	/**
	 * 查明细,全部 warehouse,带分页
	 */
	public List<SaleRecordList> listByWarehouseAndDate(String begindate,
			String enddate, int firstresult, int maxresult) {
		List<SaleRecordList> lists = null;
		String hql = "from SaleRecordList as s where s.updatetime >= ? and s.updatetime <=? order by s.id";
		lists = this.baseDao.findByHQL(hql,firstresult,maxresult,begindate,enddate);
		return lists;
	}
}
