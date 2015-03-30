package service;

import java.util.List;

import pojo.Transfer;

public interface TransferService extends BaseService<Transfer> {
	
	public List<Transfer> listTransfers(String keyword);
	public List<Transfer> listTransfers(String warehouse,String begindate,String enddate);
	public List<Transfer> listTransfers(String begindate,String enddate);
	
	
}
