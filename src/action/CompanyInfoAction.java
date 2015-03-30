package action;

import java.util.Date;
import java.util.List;

import pojo.CompanyInfo;
import tools.DateTool;

@SuppressWarnings("serial")
public class CompanyInfoAction extends BaseAction {
	private CompanyInfo companyInfo;
	private List<CompanyInfo> companyInfos;
	
	
	/**
	 * ADD COMPANYINFO
	 * @return
	 * @throws RuntimeException
	 */
	public String addCompany() throws RuntimeException{
		String companyid = request.getParameter("companyid");
		String token = request.getParameter("companyid");
		String companyname = request.getParameter("companyname");
		String companytel = request.getParameter("companytel");
		String companyaddress = request.getParameter("companyaddress");
		String companymanager = request.getParameter("companymanager");
		
		if(companyid!=null && companyid.trim()!="" && companyname!=null && companyname.trim()!=""){
			companyInfo = new CompanyInfo(companyid, companyname,token);
			if (companytel!=null && companytel.trim()!="") {
				companyInfo.setCompanyTel(companytel);
			}
			if(companyaddress!=null && companyaddress.trim()!=""){
				companyInfo.setCompanyAddress(companyaddress);
			}
			if(companymanager!=null && companymanager.trim()!=""){
				companyInfo.setCompanyManager(companymanager);
			}
			companyInfoService.add(companyInfo);
			this.setMessage(SUCCMESSAGE);
			return SUCCESS;
		}else{
			this.setMessage(ERRORMESSAGE);
			return INPUT;
		}
	}
	
	/**
	 * TO UPDATE PAGE
	 * @return
	 * @throws RuntimeException
	 */
	public String toupdComapny() throws RuntimeException{
		String cid = request.getParameter("companyid");
		if(cid!=null && cid.trim()!=""){
			companyInfo = companyInfoService.findbycid(cid);
			if(companyInfo!=null){
				this.setMessage(SUCCMESSAGE);
				return "TOUPDCOMPANY";
			}else{
				this.setMessage(ERRORMESSAGE);
				return INPUT;
			}
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	/**
	 * UPDATE COMPANYINFO
	 * @return
	 * @throws RuntimeException
	 */
	public String updCompany() throws RuntimeException{
		String cid = request.getParameter("companyid");
		String companyname = request.getParameter("companyname");
		String companytel = request.getParameter("companytel");
		String companyaddress = request.getParameter("companyaddress");
		String companymanager = request.getParameter("companymanager");
		if(cid!=null && cid.trim()!=""){
			companyInfo = this.companyInfoService.findbycid(cid);
			if(companyInfo!=null){
				if(companyname!=null && companyname.trim()!=""){
					companyInfo.setCompanyName(companyname);
				}
				if (companytel!=null && companytel.trim()!="") {
					companyInfo.setCompanyTel(companytel);
				}
				if(companyaddress!=null && companyaddress.trim()!=""){
					companyInfo.setCompanyAddress(companyaddress);
				}
				if(companymanager!=null && companymanager.trim()!=""){
					companyInfo.setCompanyManager(companymanager);
				}
				companyInfo.setUpdateTime(DateTool.getInstance().DateToPattern2(new Date()));
				this.companyInfoService.update(companyInfo);
				this.setMessage(SUCCMESSAGE);
				return SUCCESS;
			}else{
				this.setMessage(ERRORMESSAGE);
				return INPUT;
			}
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	/**
	 * FIND BY KEYWORD
	 * @return
	 * @throws RuntimeException
	 */
	public String listCompany() throws RuntimeException{
		String keyword = request.getParameter("keyword");
		if(keyword!=null && keyword.trim()!=""){
			companyInfos = this.companyInfoService.findbyKeyword(keyword);
		}else{
			companyInfos = this.companyInfoService.findbyKeyword("");
		}
		return "LISTCOMPANY";
	}
	
	

	public CompanyInfo getCompanyInfo() {
		return companyInfo;
	}
	public void setCompanyInfo(CompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}
	public List<CompanyInfo> getCompanyInfos() {
		return companyInfos;
	}
	public void setCompanyInfos(List<CompanyInfo> companyInfos) {
		this.companyInfos = companyInfos;
	}
	
	
}
