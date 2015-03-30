package serviceImpl;

import java.util.List;

import pojo.CompanyInfo;
import service.CompanyInfoService;

public class CompanyInfoServiceImpl extends BaseServiceImpl<CompanyInfo> implements
		CompanyInfoService {

	public CompanyInfo findbycid(String companyid) {
		CompanyInfo companyInfo = null;
		String hql = "FROM CompanyInfo as c WHERE c.companyId = ? and c.status = 1 LIMIT 1";
		List<CompanyInfo> companyInfos = this.findByHQL(hql, companyid);
		if(companyInfos.size()>0){
			companyInfo = companyInfos.get(0);
		}
		return companyInfo;
	}

	public List<CompanyInfo> findbyKeyword(String keyword) {
		List<CompanyInfo> companyInfos = null;
		String hql = "FROM CompanyInfo as c WHERE (c.companyId LIKE ? OR c.companyName LIKE ? OR c.companyTel LIKE ? OR c.companyAddress LIKE ?) AND c.status = 1";
		companyInfos = this.findByHQL(hql, "%"+keyword+"%","%"+keyword+"%","%"+keyword+"%","%"+keyword+"%");
		return companyInfos;
	}
	
	
}
