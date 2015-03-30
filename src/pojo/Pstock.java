package pojo;

import java.util.Date;

import tools.DateTool;

public class Pstock {
	
	private Integer id;
	private String barcode;
	private Integer quantity;
	private String warehouse; //warehouse-name not nickname
	private String updateTime;
	private Integer status;
	private ProductInfo productInfo;
	
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
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public Pstock(ProductInfo productInfo,String warehouse,Integer quantity) {
		super();
		this.barcode = productInfo.getBarcode();
		this.productInfo = productInfo;
		this.quantity = quantity;
		this.warehouse = warehouse;
		this.status = 1;
		this.updateTime = DateTool.getInstance().DateToPattern2(new Date());
	}
	public Pstock() {
		super();
	}
	
	
	
	
}
