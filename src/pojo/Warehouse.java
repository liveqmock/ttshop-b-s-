package pojo;

import java.util.Date;

import tools.DateTool;

public class Warehouse {
	
	private Integer id;
	private String wname;
	private String wadmin;
	private String wnickname;
	private String updateTime;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWname() {
		return wname;
	}
	public void setWname(String wname) {
		this.wname = wname;
	}
	public String getWadmin() {
		return wadmin;
	}
	public void setWadmin(String wadmin) {
		this.wadmin = wadmin;
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
	public String getWnickname() {
		return wnickname;
	}
	public void setWnickname(String wnickname) {
		this.wnickname = wnickname;
	}
	public Warehouse(String wname) {
		super();
		this.wname = wname;
		this.status = 1;
		this.updateTime = DateTool.getInstance().DateToPattern2(new Date());
	}
	public Warehouse() {
		super();
	}
	
	
	
	
}
