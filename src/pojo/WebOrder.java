package pojo;

import java.util.Date;
import java.util.List;

import tools.DateTool;

public class WebOrder {
	
	private Integer id;
	private String order_no;
	private Customer customer;
	private Double amount;
	private Double discount;
	private Integer paid_status;
	private String updatetime;
	private Integer status;
	private List<WebOrderList> lists;
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
	public Integer getPaid_status() {
		return paid_status;
	}
	public void setPaid_status(Integer paid_status) {
		this.paid_status = paid_status;
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
	public List<WebOrderList> getLists() {
		return lists;
	}
	public void setLists(List<WebOrderList> lists) {
		this.lists = lists;
	}
	public WebOrder() {
		super();
	}
	public WebOrder(String order_no,Customer customer,List<WebOrderList> orderLists, Double discount,
			Integer paid_status) {
		super();
		this.order_no  = order_no;
		this.discount = discount;
		this.customer = customer;
		if(!orderLists.isEmpty()){
			Double tem = 0.0;
			for (WebOrderList webOrderList : orderLists) {
				tem+=webOrderList.getAmount(); 
			}
			this.amount = tem - discount;
		}
		this.paid_status = paid_status;
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
	}
	
	
	
}
