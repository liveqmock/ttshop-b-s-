package service;

import pojo.Myid;

public interface MyidService extends BaseService<Myid> {
	
	public abstract String createIDNumber(String idtype);
	public abstract void removeIdNumber(String type,String idnumber);
}
