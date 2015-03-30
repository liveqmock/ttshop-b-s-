package service;

import java.util.List;
import pojo.PictureCatalog;

public interface PictureCatalogService extends BaseService<PictureCatalog> {
	
	public abstract List<PictureCatalog> listAllCatalogs();
	
}
