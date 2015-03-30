package service;

import java.util.List;

import pojo.Innote;

public interface InnoteService extends BaseService<Innote> {
	
	public abstract List<Innote> listByDate(String warehosue,String begindate,String enddate);
	public abstract List<Innote> listByDate(String warehosue,String begindate,String enddate,int firstResult,int maxResult);
	public abstract List<Innote> listByDate(String begindate,String enddate,int firstResult,int maxResult);//find all not groupby warehouse
	public abstract List<Innote> findBykeyword(String keyword);
	public abstract Innote findByNotenumber(String notenumber);
	public abstract Integer findallrow(String warehosue,String begindate,String enddate);
	public abstract Integer findallrow(String begindate,String enddate);//find all not groupby warehouse
	public abstract List<Object[]> sumInnote(String begindate,String enddate) throws Exception;
	public abstract List<Object[]> sumInnoteByProduct(String begindate,String enddate) throws Exception;
}
