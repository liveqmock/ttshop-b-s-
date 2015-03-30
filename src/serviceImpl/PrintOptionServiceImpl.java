package serviceImpl;

import java.util.List;

import pojo.PrintOption;
import service.PrintOptionService;

public class PrintOptionServiceImpl extends BaseServiceImpl<PrintOption> implements
		PrintOptionService {

	public List<PrintOption> findOptionList(String keywords) throws Exception {
		List<PrintOption> options = null;
		String hql = "FROM PrintOption AS p WHERE p.companyName LIKE ? AND p.status = 1";
		options = this.baseDao.findByHQL(hql,"%"+keywords+"%");
		return options;
	}

	public PrintOption findOption() throws Exception {
		System.out.println("okok");
		PrintOption printOption;
		String hql = "FROM PrintOption";
		List<PrintOption> options = this.baseDao.findByHQL(hql);
		System.out.println(options);
		if(options!=null && options.size()>0){
			printOption = options.get(0);
			return printOption;
		}
		return null;
	}

	
	
}
