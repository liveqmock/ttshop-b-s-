package pojo;

import java.util.Date;

import tools.DateTool;

public class Picture {
	
	private Integer id;
	private String companyid;
	private String barcode;
	private String filename;
	private String small;
	private Integer ismain; //1为true 0为false 用于确定是否该 barcode 的主图片
	private String url;
	private Integer size;
	private String updatetime;
	private Integer catalogid; //图片目录
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public Integer getIsmain() {
		return ismain;
	}
	public void setIsmain(Integer ismain) {
		this.ismain = ismain;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Integer getCatalogid() {
		return catalogid;
	}
	public void setCatalogid(Integer catalogid) {
		this.catalogid = catalogid;
	}
	public Picture() {
		super();
	}
	public String getSmall() {
		return small;
	}
	public void setSmall(String small) {
		this.small = small;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Picture(String companyid, String filename, Integer ismain, String url) {
		super();
		this.companyid = companyid;
		this.filename = filename;
		this.ismain = ismain;
		this.url = url;
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
	}

	
	
	
}
