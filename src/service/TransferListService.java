package service;

import java.util.List;

import pojo.TransferList;


public interface TransferListService extends BaseService<TransferList> {
	
	public abstract List<TransferList> findByTransferNo(String transferno);
	
}
