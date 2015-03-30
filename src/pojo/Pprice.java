package pojo;

import java.util.Date;

import tools.DateTool;

public class Pprice {
	
	private Integer id;
	private String barcode;
	private Double price;
	private Double lprice;
	private String updateTime;
	private Integer status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
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
	public Double getLprice() {
		return lprice;
	}
	public void setLprice(Double lprice) {
		this.lprice = lprice;
	}
	public Pprice(String barcode, Double price ) {
		super();
		this.barcode = barcode;
		this.price = price;
		this.status = 1;
		this.updateTime = DateTool.getInstance().DateToPattern2(new Date());
	}
	public Pprice() {
		super();
	}
	
	
	
	
}
