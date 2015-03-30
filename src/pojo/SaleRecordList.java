package pojo;

import java.util.Date;

import tools.DateTool;

public class SaleRecordList {
	
	private Integer id;
	private String invoiceno;
	private Integer invoiceline;
	private String barcode;
	private String pdesc;
	private String remark;
	private Integer quantity;
	private Double price;
	private Double amount;
	private String updatetime;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInvoiceno() {
		return invoiceno;
	}
	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public SaleRecordList() {
		super();
	}
	public String getPdesc() {
		return pdesc;
	}
	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}
	public Integer getInvoiceline() {
		return invoiceline;
	}
	public void setInvoiceline(Integer invoiceline) {
		this.invoiceline = invoiceline;
	}
	public SaleRecordList(String invoiceno, Integer invoiceline, String barcode,String pdesc,
			Integer quantity, Double price) {
		super();
		this.invoiceno = invoiceno;
		this.invoiceline = invoiceline;
		this.barcode = barcode;
		this.pdesc = pdesc;
		this.quantity = quantity;
		this.price = price;
		this.amount = quantity*price;
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
	}
	
	
	
	
}
