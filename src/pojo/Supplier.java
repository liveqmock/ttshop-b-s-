package pojo;

import java.util.Date;

import tools.DateTool;


public class Supplier {
	
	private Integer id;
	private String supplierId;
	private String supplierName;
	private String supplierTel;
	private String supplierAddress;
	private String updateTime;
	private Integer status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierTel() {
		return supplierTel;
	}
	public void setSupplierTel(String supplierTel) {
		this.supplierTel = supplierTel;
	}
	public String getSupplierAddress() {
		return supplierAddress;
	}
	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
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
	public Supplier() {
		super();
	}
	public Supplier(String supplierId, String supplierName) {
		super();
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.updateTime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
	}
	
	
	
	
	
	
	
}
