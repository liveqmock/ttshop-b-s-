package service;

import java.util.List;

import pojo.Ptype;

public interface PtypeService extends BaseService<Ptype> {
	
	public abstract Ptype finybyName(String wname);
	public abstract List<Ptype> findByKeyword(String keyword);
	
}
