package pojo;

import java.util.Date;

import tools.DateTool;

public class StockChecking{
	
	private Integer id;
	private Warehouse warehouse;
	private ProductInfo productInfo;
	private Pstock pstock;
	private Integer quantity_before; //盘点前
	private Integer quantity_after; //盘点后
	private String updatetime;
	private Integer status;
	private String  operator;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public Pstock getPstock() {
		return pstock;
	}
	public void setPstock(Pstock pstock) {
		this.pstock = pstock;
	}
	public Integer getQuantity_before() {
		return quantity_before;
	}
	public void setQuantity_before(Integer quantity_before) {
		this.quantity_before = quantity_before;
	}
	public Integer getQuantity_after() {
		return quantity_after;
	}
	public void setQuantity_after(Integer quantity_after) {
		this.quantity_after = quantity_after;
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
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public StockChecking() {
		super();
	}
	public StockChecking(Pstock pstock,ProductInfo productInfo,Integer quantity_after,Warehouse warehouse) {
		super();
		this.productInfo = productInfo;
		this.warehouse = warehouse;
		this.pstock = pstock;
		this.quantity_before = pstock.getQuantity();
		this.quantity_after = quantity_after;
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
	}
	
}
