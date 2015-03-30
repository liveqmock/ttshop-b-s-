package serviceImpl;

import java.util.List;

import pojo.Myid;
import service.MyidService;

public class MyidServiceImpl extends BaseServiceImpl<Myid> implements
		MyidService {

	public String createIDNumber(String idtype) {
		String sql = "SELECT MAX(idnumber.no),idnumber.type FROM idnumber WHERE idnumber.type='"+idtype+"' GROUP BY idnumber.type";
		String hql = "FROM Myid as m WHERE m.type = ? ";
		String saleNumber = "";
		List<Myid> myids = this.baseDao.findByHQL(hql, idtype);
		if(myids==null || myids.size()==0){
			Myid myid = new Myid(idtype, 10000000);    //系统默认8位单号
			this.baseDao.add(myid);
		}
		List<Object[]> list = this.baseDao.createSqlQuery(sql);
		if(list!=null){
			Object[] object= list.get(0);
			String num = object[0].toString();
			Integer ne = Integer.valueOf(num)+1;
			Myid myid = new Myid(idtype, ne);
			this.baseDao.add(myid);
			saleNumber = idtype+ne;
		}
		return saleNumber;
	}
	
	public void removeIdNumber(String type,String idnumber){
		String hql = "FROM Myid as m WHERE m.type = ? AND m.number = ?";
		Integer number = Integer.valueOf(idnumber.replaceAll(type, ""));
		Myid myid = this.findByHQL(hql,type,number).get(0);
		this.baseDao.delete(myid);
	}
	
}
