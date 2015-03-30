package service;

import java.util.List;

import pojo.CompanyInfo;

public interface CompanyInfoService extends BaseService<CompanyInfo> {
	
	
	public abstract CompanyInfo findbycid(String companyid);
	public abstract List<CompanyInfo> findbyKeyword(String keyword);
	
}
