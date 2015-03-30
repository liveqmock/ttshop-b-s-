package pojo;

import java.util.Date;

import tools.DateTool;

public class PictureCatalog {
	
	private Integer id;
	private String catalog;
	private String updatetime;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
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
	public PictureCatalog(String catalog) {
		super();
		this.catalog = catalog;
		this.status = 1;
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
	}
	public PictureCatalog() {
		super();
	}
	
	
}
