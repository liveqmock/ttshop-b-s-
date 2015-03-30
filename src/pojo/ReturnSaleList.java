package pojo;

import java.util.Date;

import tools.DateTool;

public class ReturnSaleList {
	
	private Integer id;
	private String returnsaleno;
	private String barcode;
	private Integer quantity;
	private Double price;
	private Double amount;
	private String remark;
	private String updatetime;
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
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getReturnsaleno() {
		return returnsaleno;
	}
	public void setReturnsaleno(String returnsaleno) {
		this.returnsaleno = returnsaleno;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public ReturnSaleList(String returnsaleno,String barcode, Integer quantity,Double price,
			String remark) {
		super();
		this.returnsaleno = returnsaleno;
		this.barcode = barcode;
		this.quantity = quantity;
		this.price = price;
		this.amount = quantity*price;
		this.remark = remark;
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
	}
	public ReturnSaleList() {
		super();
	}
	
	
	
}
