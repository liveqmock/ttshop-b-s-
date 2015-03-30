package pojo;

import java.util.Date;

import tools.DateTool;

public class Customer {
	
	private Integer id;
	private String customerId;
	private String customerName;
	private String customerTel;
	private String customerAddress;
	private Integer credits; // »ý·Ö
	private Double discountrate;
	private String updateTime;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerTel() {
		return customerTel;
	}
	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
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
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public Integer getCredits() {
		return credits;
	}
	public void setCredits(Integer credits) {
		this.credits = credits;
	}
	public Double getDiscountrate() {
		return discountrate;
	}
	public void setDiscountrate(Double discountrate) {
		this.discountrate = discountrate;
	}
	public Customer() {
		super();
	}
	public Customer(String customerId, String customerName) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.updateTime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
	}
	
	
	
	
	
	
}
