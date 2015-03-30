package service;

import java.util.List;

import pojo.Picture;

public interface PictureService extends BaseService<Picture> {
	
	public abstract List<Picture> listAllPicture(Integer firstresult,Integer maxresult);
	public abstract List<Picture> listAllPicture(Integer catalogid,Integer firstresult,Integer maxresult);
	public abstract List<Picture> listAllPicture(String barcode,Integer firstresult,Integer maxresult);
	public abstract List<Picture> listAllPicture();
	public abstract Integer getAllrows();
	public abstract Integer getAllrows(Integer catalogid);
	
}
