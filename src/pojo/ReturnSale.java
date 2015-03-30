package pojo;

import java.util.Date;
import java.util.List;

import tools.DateTool;

public class ReturnSale {
	
	private Integer id;
	private String returnsaleno;
	private String invoiceno;
	private String warehouse;
	private Integer totalQuantity;
	private Double totalAmount;
	private String operatorid;
	private String customerid;
	private String remarks;
	private String updatetime;
	private Integer status;
	private List<ReturnSaleList> returnSaleLists;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getOperatorid() {
		return operatorid;
	}
	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
	}
	public String getCustomerid() {
		return customerid;
	}
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public List<ReturnSaleList> getReturnSaleLists() {
		return returnSaleLists;
	}
	public void setReturnSaleLists(List<ReturnSaleList> returnSaleLists) {
		this.returnSaleLists = returnSaleLists;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getReturnsaleno() {
		return returnsaleno;
	}
	public void setReturnsaleno(String returnsaleno) {
		this.returnsaleno = returnsaleno;
	}
	public String getInvoiceno() {
		return invoiceno;
	}
	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}
	public void update(List<ReturnSaleList> returnSaleListlists){
		Integer qu = 0;
		Double am = 0.0;
		for (ReturnSaleList returnSaleList : returnSaleListlists) {
			Integer q = 0;
			Double p = 0.0;
			if(returnSaleList.getQuantity()!=null && returnSaleList.getPrice()!=null){
				q = returnSaleList.getQuantity();
				p = returnSaleList.getPrice();
			}
			qu = qu+q;
			am = am+q*p;
		}
		this.totalQuantity = qu;
		this.totalAmount = am;
	}
	public ReturnSale(String returnsaleno,String invoiceno,List<ReturnSaleList> returnSaleLists, String operatorid, String customerid,String warehouse,
			String remarks) {
		super();
		this.returnsaleno = returnsaleno;
		this.invoiceno = invoiceno;
		this.totalQuantity = 0;
		this.totalAmount = 0.0;
		if(returnSaleLists!=null && returnSaleLists.size()>0){
			for (ReturnSaleList returnSaleList : returnSaleLists) {
				this.totalQuantity = totalQuantity+returnSaleList.getQuantity();
				this.totalAmount = totalAmount+returnSaleList.getAmount();
			}
		}
		this.warehouse = warehouse;
		this.operatorid = operatorid;
		this.customerid = customerid;
		this.remarks = remarks;
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
	}
	public ReturnSale() {
		super();
	}
	
	
}
