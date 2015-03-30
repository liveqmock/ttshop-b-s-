package pojo;

import java.util.Date;

import tools.DateTool;

public class WebOrderList {
	
	private Integer id;
	private String order_no;
	private ProductInfo productInfo;
	private Double price;
	private Integer quantity;
	private Double amount;
	private Double discount;
	private Integer status;
	private String updatetime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
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
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public WebOrderList() {
		super();
	}
	public WebOrderList(String order_no, ProductInfo productInfo, Double price,
			Integer quantity, Double discount) {
		super();
		this.order_no = order_no;
		this.productInfo = productInfo;
		this.price = price;
		this.quantity = quantity;
		this.discount = discount;
		this.amount = (price*quantity) - discount;
		this.status = 1;
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
	}
	
}
