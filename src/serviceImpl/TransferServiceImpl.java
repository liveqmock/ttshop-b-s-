package serviceImpl;

import java.util.List;
import pojo.Transfer;
import service.TransferService;

public class TransferServiceImpl extends BaseServiceImpl<Transfer> implements
		TransferService {

	public List<Transfer> listTransfers(String warehouse, String begindate,
			String enddate) {
		List<Transfer> list = null;
		String hql = "FROM Transfer as tran WHERE tran.warehouse = ? AND tran.updatetime >= ? AND tran.updatetime <= ?  ORDER BY tran.id DESC";
		list = this.baseDao.findByHQL(hql,warehouse,begindate,enddate);
		return list;
	}

	public List<Transfer> listTransfers(String keyword) {
		List<Transfer> list = null;
		String hql = "FROM Transfer as tran WHERE tran.receiverid LIKE ? ORDER BY tran.id DESC";
		list = this.baseDao.findByHQL(hql, "%"+keyword+"%");
		return list;
	}

	public List<Transfer> listTransfers(String begindate, String enddate) {
		List<Transfer> list = null;
		String hql = "FROM Transfer as tran WHERE tran.updatetime >= ? AND tran.updatetime <= ?  ORDER BY tran.id DESC";
		list = this.baseDao.findByHQL(hql,begindate,enddate);
		return list;
	}
	
	
}
