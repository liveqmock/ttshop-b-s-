package serviceImpl;

import java.util.List;

import pojo.Ptype;
import service.PtypeService;

public class PtypeServiceImpl extends BaseServiceImpl<Ptype> implements
		PtypeService {

	public List<Ptype> findByKeyword(String keyword) {
		List<Ptype> ptypes = null;
		String hql = "FROM Ptype as p WHERE p.typeName LIKE ? AND p.status = 1";
		ptypes = this.findByHQL(hql, "%"+keyword+"%");
		return ptypes;
	}

	public Ptype finybyName(String wname) {
		Ptype  ptype = null;
		String hql ="FROM Ptype as p WHERE p.typeName = ? AND p.status = 1";
		List<Ptype> ptypes = this.findByHQL(hql, wname);
		if(ptypes.size()>0 && ptypes.size()==1){
			ptype = ptypes.get(0);
		}
		return ptype;
	}
	
	
}
