package serviceImpl;

import java.util.List;
import pojo.Innote;
import service.InnoteService;

public class InnoteServiceImpl extends BaseServiceImpl<Innote> implements
		InnoteService {

	public List<Innote> listByDate(String warehosue,String begindate,String enddate) {
		List<Innote> innotes = null;
		String hql = "FROM Innote as n WHERE n.warehouse = ? n.updatetime >= ? AND n.updatetime <= ? ORDER BY n.id DESC";
		innotes = this.baseDao.findByHQL(hql, warehosue,begindate,enddate);
		return innotes;
	}

	public Innote findByNotenumber(String notenumber) {
		Innote innote = null;
		String hql = "FROM Innote as i WHERE i.notenumber = ?";
		List<Innote> innotes = null;
		innotes = this.baseDao.findByHQL(hql, notenumber);
		if (innotes!=null && innotes.size()>0) {
			innote = innotes.get(0);
		}
		return innote;
	}

	public List<Innote> findBykeyword(String keyword){
		List<Innote> innotes = null;
		String hql = "FROM Innote as n WHERE (n.notenumber LIKE ? OR n.warehouse LIKE ? OR n.operatorid LIKE ? OR n.supplierid LIKE ?) AND n.status = 1 ORDER BY n.id DESC";
		innotes = this.baseDao.findByHQL(hql, "%"+keyword+"%","%"+keyword+"%","%"+keyword+"%","%"+keyword+"%");
		return innotes;
	}

	public List<Object[]> sumInnote(String begindate,String enddate) throws Exception {
		List<Object[]> list = null;
		String sql = "select sum(n.quantity),sum(n.amount),n.warehouse from innote as n where n.updatetime >='"+begindate+
				"' and n.updatetime<='"+enddate+"' and n.status=1 group by n.warehouse";
		list = this.baseDao.createSqlQuery(sql);
		return list;
	}
	
	public List<Object[]> sumInnoteByProduct(String begindate,String enddate) throws Exception {
		List<Object[]> list = null;
		String sql = "select sum(n.quantity),n.price,p.barcode,p.pdesc,n.notenumber from innotelist as n left outer join productinfo as p on(p.barcode=n.barcode) where n.updatetime >='"+begindate+
				"' and n.updatetime<='"+enddate+"' and n.status=1 group by p.barcode,n.price";
		list = this.baseDao.createSqlQuery(sql);
		return list;
	}

	public List<Innote> listByDate(String warehosue,String begindate, String enddate,
			int firstResult, int maxResult) {
		List<Innote> list = null;
		String hql = "FROM Innote as n WHERE n.warehouse = ? and n.updatetime >=? and n.updatetime <= ? ORDER BY n.id DESC";
		list = this.baseDao.findByHQL(hql, firstResult, maxResult,warehosue, begindate,enddate);
		return list;
	}

	public Integer findallrow(String warehosue,String begindate,
			String enddate) {
		String hql = "select count(i.id) from Innote as i where i.warehouse ='"+warehosue+"' and i.updatetime >='"+begindate+"' and i.updatetime <='"+enddate+"'";
		Integer allrows = 0;
		allrows = (Integer) this.baseDao.getUniqueResult(hql);
		return allrows;
	}

	public List<Innote> listByDate(String begindate, String enddate,
			int firstResult, int maxResult) {
		List<Innote> list = null;
		String hql = "FROM Innote as n WHERE n.updatetime >=? and n.updatetime <= ? ORDER BY n.id DESC";
		list = this.baseDao.findByHQL(hql, firstResult, maxResult, begindate,enddate);
		return list;
	}

	public Integer findallrow(String begindate, String enddate) {
		String hql = "select count(i.id) from Innote as i where i.updatetime >='"+begindate+"' and i.updatetime <='"+enddate+"'";
		Integer allrows = 0;
		allrows = (Integer) this.baseDao.getUniqueResult(hql);
		return allrows;
	}
	
}
