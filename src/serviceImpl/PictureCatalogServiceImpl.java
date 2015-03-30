package serviceImpl;

import java.util.List;
import pojo.PictureCatalog;
import service.PictureCatalogService;

public class PictureCatalogServiceImpl extends BaseServiceImpl<PictureCatalog> implements
		PictureCatalogService {

	public List<PictureCatalog> listAllCatalogs() {
		List<PictureCatalog> catalogs = null;
		String hql = "from PictureCatalog as c where c.status = 1 order by c.id desc";
		catalogs =  this.baseDao.findByHQL(hql);
		return catalogs;
	}
	
	
}
