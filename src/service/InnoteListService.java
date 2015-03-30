package service;

import java.util.List;
import java.util.Map;

import pojo.InnoteList;

public interface InnoteListService extends BaseService<InnoteList> {
	
	public abstract List<InnoteList> findByNotenumber(String notenumber);
	public abstract List<Object[]> findInotelistByNotenumber(String notenumber);
	public abstract List<Object[]> listInotelistByDate(String begindate,String enddate);
	public abstract List<Object[]> listInotelistByDate(String begindate,String enddate,int firstresult,int maxresult);
	public abstract List<Object[]> listInotelistByDate(String warehouse,String begindate,String enddate);
	public abstract List<Object[]> listInotelistByDate(String warehouse,String begindate,String enddate,int firstresult,int maxresult);
	public abstract List<Object[]> avgInnotePrice(String enddate);
	public abstract Double avgInnotePrice(String barcode,String enddate);
	public abstract Integer sumTotalByBarcode(String barcode);
	public abstract List<Object[]> sumTotalIn(String begin,String end);
	public abstract List<Object[]> sumTotalIn(String warehouse,String begin,String end);
	public abstract Map<String, Double> sumAllProductCost(String begin,String end);
}
