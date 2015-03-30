package serviceImpl;

import java.util.List;

import pojo.TransferList;
import service.TransferListService;

public class TransferListServiceImpl extends BaseServiceImpl<TransferList> implements
		TransferListService {

	public List<TransferList> findByTransferNo(String transferno) {
		List<TransferList> list = null;
		String hql = "FROM TransferList as t WHERE t.transferno = ?";
		list = this.baseDao.findByHQL(hql, transferno);
		return list;
	}
	
	
}
