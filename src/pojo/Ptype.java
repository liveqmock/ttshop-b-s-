package pojo;

import java.util.Date;

import tools.DateTool;

public class Ptype {
	
	private Integer id;
	private String typeName;
	private Integer itype; //0-非库存类型 1-库存类型 2-库存有IMEI
	private String updateTime;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	public Integer getItype() {
		return itype;
	}
	public void setItype(Integer itype) {
		this.itype = itype;
	}
	public Ptype(String typeName,Integer itype) {
		super();
		this.typeName = typeName;
		this.itype = itype;
		this.status = 1;
		this.updateTime = DateTool.getInstance().DateToPattern2(new Date());
	}
	public Ptype() {
		super();
	}
	
	
	
	
	
}
