package pojo;

import java.util.Date;
import java.util.List;

import tools.DateTool;

public class SaleRecord {
	
	private Integer id;
	private String invoiceno;
	private String customerid;
	private String warehouse;
	private String operatorid;
	private Integer totalquantity;
	private Double totalamount;
	private String currency;   //HKD--港币   MOP--澳门币  RMB--人民币
	private Double discount;
	private Double discountrate;
	private Double freight;
	private Double othercharge;
	private Double pay;
	private Double cashchange;
	private String paidtime;
	private String remark;
	private Integer paidtype;  //0--现金  1--刷卡   2--签单 3--支票  4--其它
	private Integer paidstatus;  //0--未处理  1--已付  2--未付
	private String updatetime;
	private String createtime;
	private Integer status;
	private List<SaleRecordList> recordLists;
	private Customer customer;
	private User user;
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
	public String getCustomerid() {
		return customerid;
	}
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	public String getOperatorid() {
		return operatorid;
	}
	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
	}
	public Integer getTotalquantity() {
		return totalquantity;
	}
	public void setTotalquantity(Integer totalquantity) {
		this.totalquantity = totalquantity;
	}
	public Double getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
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
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getFreight() {
		return freight;
	}
	public void setFreight(Double freight) {
		this.freight = freight;
	}
	public Double getOthercharge() {
		return othercharge;
	}
	public void setOthercharge(Double othercharge) {
		this.othercharge = othercharge;
	}
	public String getPaidtime() {
		return paidtime;
	}
	public void setPaidtime(String paidtime) {
		this.paidtime = paidtime;
	}
	public Integer getPaidtype() {
		return paidtype;
	}
	public void setPaidtype(Integer paidtype) {
		this.paidtype = paidtype;
	}
	public Integer getPaidstatus() {
		return paidstatus;
	}
	public void setPaidstatus(Integer paidstatus) {
		this.paidstatus = paidstatus;
	}
	public List<SaleRecordList> getRecordLists() {
		return recordLists;
	}
	public void setRecordLists(List<SaleRecordList> recordLists) {
		this.recordLists = recordLists;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public Double getPay() {
		return pay;
	}
	public void setPay(Double pay) {
		this.pay = pay;
	}
	public Double getCashchange() {
		return cashchange;
	}
	public void setCashchange(Double cashchange) {
		this.cashchange = cashchange;
	}
	public Double getDiscountrate() {
		return discountrate;
	}
	public void setDiscountrate(Double discountrate) {
		this.discountrate = discountrate;
	}
	public SaleRecord(String invoiceno,String warehouse, String customerid, String operatorid,
			List<SaleRecordList> recordLists,String currency,Integer paidtype,Double discount,Double discountrate) {
		super();
		this.invoiceno = invoiceno;
		this.customerid = customerid;
		this.warehouse = warehouse;
		this.operatorid = operatorid;
		this.discount = discount;
		this.discountrate = discountrate; //接收的是dicountrate%，所以除100
		this.paidtype = paidtype;
		Integer totalquantity = 0;
		double totalamount = 0.0;
		for (SaleRecordList recordList : recordLists) {
			totalquantity = totalquantity + recordList.getQuantity();
			totalamount = totalamount + recordList.getAmount();
		}
		this.totalquantity = totalquantity;
		this.totalamount = (totalamount-discount)*discountrate/100;
		this.currency = currency;
		this.createtime = DateTool.getInstance().DateToPattern2(new Date());
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
	}
	public SaleRecord() {
		super();
	}
	
	
	
	
}
