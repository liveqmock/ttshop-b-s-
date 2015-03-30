package serviceImpl;

import java.util.ArrayList;
import java.util.List;

import pojo.Picture;
import service.PictureService;

public class PictureServiceImpl extends BaseServiceImpl<Picture> implements
		PictureService {

	public List<Picture> listAllPicture(Integer firstresult,Integer maxresult) {
		String hql = "from Picture as p order by p.id desc";
		List<Picture> pictures = null;
		pictures = this.baseDao.findByHQL(hql);
		return pictures;
	}
	
	public List<Picture> listAllPicture(Integer catalogid,Integer firstresult,Integer maxresult) {
//		String hql = "select p.id,p.barcode,p.filename,p.companyid,p.ismain,p.url,p.catalogid p.status " +
//				"from Picture as p where p.catalogid=? and p.status = 1 order by p.id desc ";
		String hql = "from Picture as p where p.catalogid=? and p.status = 1 order by p.id desc";
		List<Picture> pictures = null;
		pictures = this.baseDao.findByHQL(hql, firstresult, maxresult, catalogid);
		return pictures;
	}

	public Integer getAllrows() {
		String hql = "select count(p.id),count(p.catalogid) from Picture as p";
		List<Object[]> list = this.baseDao.createHqlQuery(hql);
		Integer allrows = Integer.valueOf(list.get(0)[1].toString());
		return allrows;
	}

	public Integer getAllrows(Integer catalogid) {
		String hql = "select count(p.id),count(p.catalogid) from Picture as p where p.catalogid = "+catalogid;
		List<Object[]> list = this.baseDao.createHqlQuery(hql);
		Integer allrows = Integer.valueOf(list.get(0)[1].toString());
		return allrows;
	}

	public List<Picture> listAllPicture(String barcode, Integer firstresult,
			Integer maxresult) {
		String hql = "from Picture as p where p.barcode = ? and p.status = 1";
		List<Picture> pictures = new ArrayList<Picture>();
		pictures = this.baseDao.findByHQL(hql, firstresult, maxresult, barcode);
		return pictures;
	}

	public List<Picture> listAllPicture() {
		List<Picture> pictures = new ArrayList<Picture>();
		String hql = "FROM Picture as p where p.status = 1";
		pictures = this.baseDao.findByHQL(hql);
		return pictures;
	}


	
}
