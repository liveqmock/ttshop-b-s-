package serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pojo.InnoteList;
import service.InnoteListService;
import tools.CheckTool;

public class InnoteListServiceImpl extends BaseServiceImpl<InnoteList> implements
		InnoteListService {
	
	/**
	 * 通过 notenumber 查找入货单明细
	 */
	public List<InnoteList> findByNotenumber(String notenumber) {
		String hql = "FROM InnoteList as l WHERE l.notenumber = ? AND l.status = 1 ORDER BY l.id desc";
		List<InnoteList> innoteLists = null;
		innoteLists = this.findByHQL(hql, notenumber);
		return innoteLists;
	}
	
	/**
	 * 通过 notenumber 查找入货单明细,left join productinfo 表
	 */
	public List<Object[]> findInotelistByNotenumber(String notenumber) {
		List<Object[]> objects = null;
		String sql = "SELECT innotelist.notenumber,innotelist.barcode,innotelist.quantity,innotelist.price,innotelist.imei,innotelist.updatetime,innotelist.status,productinfo.pdesc,innotelist.id FROM innotelist LEFT OUTER JOIN productinfo ON(innotelist.barcode=productinfo.barcode) WHERE " +
				"innotelist.notenumber = '"+notenumber+"' AND innotelist.status = 1";
		objects = this.baseDao.createSqlQuery(sql);
		return objects;
	}
	/**
	 * 入货明细,通过时间段查找
	 */
	public List<Object[]> listInotelistByDate(String begindate, String enddate) {
		List<Object[]> objects = null;
		String sql = "SELECT innotelist.notenumber,innotelist.quantity,innotelist.price,innotelist.imei,innotelist.updatetime,innotelist.status,productinfo.pdesc,innotelist.id,innote.warehouse,innote.supplierid FROM innotelist"+
				" LEFT OUTER JOIN productinfo ON(innotelist.barcode=productinfo.barcode) "+
				" LEFT OUTER JOIN innote ON(innotelist.notenumber=innote.notenumber) WHERE " +
				"innotelist.updatetime>='"+begindate+"' AND innotelist.updatetime<='"+enddate+"' AND innotelist.status = 1 order by innotelist.id desc";
		objects = this.baseDao.createSqlQuery(sql);
		return objects;
	}
	
	/**
	 * 计算所有产品的平均入货价
	 */
	public List<Object[]> avgInnotePrice(String enddate) {
		List<Object[]> objects = null;
		String sql = "SELECT ROUND(SUM(n.amount)/SUM(n.quantity),1),n.barcode,p.pdesc FROM innotelist AS n LEFT OUTER JOIN productinfo as p ON(p.barcode=n.barcode)"+
					 " WHERE n.updatetime<='"+enddate+"' AND n.status=1 GROUP BY n.barcode";
		objects = this.baseDao.createSqlQuery(sql);
		return objects;
	}
	
	/**
	 * 计算某个产品的平均入货价
	 */
	public Double avgInnotePrice(String barcode,String enddate) {
		List<Object[]> objects = null;
		Double avgcost = 0.0;
		String sql = "SELECT ROUND(SUM(n.amount)/SUM(n.quantity),1),n.barcode FROM innotelist AS n "+
					 " WHERE n.updatetime<='"+enddate+"' AND n.barcode ='"+barcode+"' AND n.status=1";
		objects = this.baseDao.createSqlQuery(sql);
		if(objects.size()>0){
			Object c = objects.get(0)[0];
			String co = c.toString();
			if(CheckTool.getInstance().checkNumber(co)){
				avgcost = Double.valueOf(co);
			}
			return avgcost;
		}
		return 0.0;
	}
	
	/**
	 * 计算某个 barcode 产品的入货数量
	 */
	public Integer sumTotalByBarcode(String barcode) {
		Integer quan = 0;
		String sql = "SELECT SUM(n.quantity),n.barcode FROM innotelist AS n WHERE n.barcode = '"+barcode+"' AND n.status=1 GROUP BY n.barcode";
		List<Object[]> objects = null;
		objects = this.baseDao.createSqlQuery(sql);
		if(objects!=null && objects.size()>0){
			String q = objects.get(0)[0].toString();
			quan = Integer.valueOf(q);
		}
		return quan;
	}
	
	/**
	 * 入货产品概览,不带 warehouse 参数
	 */
	public List<Object[]> sumTotalIn(String begin,String end){
		String sql = "SELECT SUM(n.quantity),SUM(n.amount),n.barcode,p.pdesc FROM innotelist AS n LEFT OUTER JOIN productinfo as p ON(p.barcode=n.barcode) WHERE n.updatetime >= '"+begin+"' AND n.updatetime <='"+end+"' AND n.status=1 GROUP BY n.barcode";
		List<Object[]> objects = null;
		objects = this.baseDao.createSqlQuery(sql);
		return objects;
	}
	
	/**
	 * 获取产品成本,输出为 MAP<BARCODE,DOUBLE>
	 */
	public Map<String, Double> sumAllProductCost(String begin, String end) {
		Map<String, Double> allcost = new HashMap<String, Double>();
		//barcode updatetime status cost
		String sql = "SELECT innotelist.barcode as barcode,innotelist.updatetime as updatetime,innotelist.status as status,ROUND(SUM(innotelist.amount)/SUM(innotelist.quantity),2) as cost FROM innotelist WHERE innotelist.updatetime <= '"+
				end+"' AND innotelist.status = 1  GROUP BY innotelist.barcode";
		List<Object[]> objects = this.baseDao.createSqlQuery(sql);
		if(objects!=null && objects.size()>0){
			for (Object[] objects2 : objects) {
				String barcode = objects2[0].toString();
				if(objects2[3]!=null){
					Double cost = Double.valueOf(objects2[3].toString());
					allcost.put(barcode, cost);
				}
			}
		}
		return allcost;
	}
	
	/**
	 * 入货明细,带 warehouse 参数
	 */
	public List<Object[]> listInotelistByDate(String warehouse,
			String begindate, String enddate) {
		List<Object[]> objects = null;
		String sql = "SELECT innotelist.notenumber,innotelist.quantity,innotelist.price,innotelist.imei,innotelist.updatetime,innotelist.status,productinfo.pdesc,innotelist.id,innote.warehouse,innote.supplierid FROM innotelist"+
				" LEFT OUTER JOIN productinfo ON(innotelist.barcode=productinfo.barcode) "+
				" LEFT OUTER JOIN innote ON(innotelist.notenumber=innote.notenumber) WHERE " +
				"innotelist.updatetime>='"+begindate+"' AND innotelist.updatetime<='"+enddate+"' AND innote.warehouse ='"+warehouse+"'  AND innotelist.status = 1 order by innotelist.id desc";
		objects = this.baseDao.createSqlQuery(sql);
		return objects;
	}
	
	/**
	 * 入货产品概览,带 warehouse 参数
	 */
	public List<Object[]> sumTotalIn(String warehouse, String begin, String end) {
		String sql = "SELECT SUM(n.quantity),SUM(n.amount),n.barcode,p.pdesc FROM innotelist AS n " +
				"LEFT OUTER JOIN productinfo as p ON(p.barcode=n.barcode) " +
				"LEFT OUTER JOIN innote as i ON(i.notenumber=n.notenumber) " +
				"WHERE n.updatetime >= '"+begin+"' AND n.updatetime <='"+end+"' AND i.warehouse = '"+warehouse+"' AND n.status=1 GROUP BY n.barcode";
		List<Object[]> objects = null;
		objects = this.baseDao.createSqlQuery(sql);
		return objects;
	}
	
	public List<Object[]> listInotelistByDate(String begindate, String enddate,
			int firstresult, int maxresult) {
		List<Object[]> objects = null;
		String sql = "SELECT innotelist.notenumber,innotelist.quantity,innotelist.price,innotelist.imei,innotelist.updatetime,innotelist.status,productinfo.pdesc,innotelist.id,innote.warehouse,innote.supplierid FROM innotelist"+
				" LEFT OUTER JOIN productinfo ON(innotelist.barcode=productinfo.barcode) "+
				" LEFT OUTER JOIN innote ON(innotelist.notenumber=innote.notenumber) WHERE " +
				"innotelist.updatetime>='"+begindate+"' AND innotelist.updatetime<='"+enddate+"' AND innotelist.status = 1 order by innotelist.id desc";
		sql+=" limit "+firstresult+","+maxresult;
		objects = this.baseDao.createSqlQuery(sql);
		return objects;
	}

	public List<Object[]> listInotelistByDate(String warehouse,
			String begindate, String enddate, int firstresult, int maxresult) {
		List<Object[]> objects = null;
		String sql = "SELECT innotelist.notenumber,innotelist.quantity,innotelist.price,innotelist.imei,innotelist.updatetime,innotelist.status,productinfo.pdesc,innotelist.id,innote.warehouse,innote.supplierid FROM innotelist"+
				" LEFT OUTER JOIN productinfo ON(innotelist.barcode=productinfo.barcode) "+
				" LEFT OUTER JOIN innote ON(innotelist.notenumber=innote.notenumber) WHERE " +
				"innotelist.updatetime>='"+begindate+"' AND innotelist.updatetime<='"+enddate+"' AND innote.warehouse ='"+warehouse+"'  AND innotelist.status = 1 order by innotelist.id desc";
		sql+=" limit "+firstresult+","+maxresult;
		objects = this.baseDao.createSqlQuery(sql);
		return objects;
	}
	

}
