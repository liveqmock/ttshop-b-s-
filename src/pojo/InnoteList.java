package pojo;

import java.util.Date;

import tools.DateTool;

public class InnoteList {
	
	private Integer id;
	private String barcode;
	private Integer quantity;
	private Double price;
	private Double amount;
	private String imei;
	private String notenumber;
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
	public InnoteList() {
		super();
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getNotenumber() {
		return notenumber;
	}
	public void setNotenumber(String notenumber) {
		this.notenumber = notenumber;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void updateAmount() {
		this.amount = this.price * this.quantity;
	}
	public InnoteList(String notenumber,String barcode,Integer quantity,Double price,String imei) {
		super();
		this.notenumber = notenumber;
		this.barcode = barcode;
		this.quantity = quantity;
		this.price = price;
		this.amount = price * quantity;
		this.imei = imei;
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
	}
	
	
	
	
}
