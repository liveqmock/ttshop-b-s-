package pojo;

import java.util.Date;

import tools.DateTool;

public class CompanyInfo {
	
	private Integer id;
	private String token;
	private String companyId;
	private String companyName;
	private String companyTel;
	private String companyAddress;
	private String companyManager;
	private String companyLogo;
	private String companyFax;
	private String companyEmail;
	private String updateTime;
	private Integer status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyTel() {
		return companyTel;
	}
	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getCompanyManager() {
		return companyManager;
	}
	public void setCompanyManager(String companyManager) {
		this.companyManager = companyManager;
	}
	public String getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCompanyFax() {
		return companyFax;
	}
	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	public CompanyInfo(String companyId, String companyName,String token) {
		super();
		this.token = token;
		this.companyId = companyId;
		this.companyName = companyName;
		this.updateTime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
	}
	public CompanyInfo() {
		super();
	}
	
	
	
	
}
