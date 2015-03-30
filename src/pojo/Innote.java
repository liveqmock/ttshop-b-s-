package pojo;

import java.util.Date;
import java.util.List;

import tools.DateTool;

public class Innote {
	
	private Integer id;
	private String notenumber;
	private String warehouse;
	private String inboundType;
	private String operatorid;
	private String supplierid;
	private Integer quantity;
//	private Double price;
	private Double amount;
	private List<InnoteList> innoteLists;
	private String updatetime;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNotenumber() {
		return notenumber;
	}
	public void setNotenumber(String notenumber) {
		this.notenumber = notenumber;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getInboundType() {
		return inboundType;
	}
	public void setInboundType(String inboundType) {
		this.inboundType = inboundType;
	}
	public String getOperatorid() {
		return operatorid;
	}
	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
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
	public Innote() {
		super();
	}
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}
	public List<InnoteList> getInnoteLists() {
		return innoteLists;
	}
	public void setInnoteLists(List<InnoteList> innoteLists) {
		this.innoteLists = innoteLists;
	}
	public void updateInnoteList(List<InnoteList> innoteLists){
		Double am = 0.0;
		Integer qu = 0;
		for (InnoteList innoteList : innoteLists) {
			Double p = 0.0;
			Integer q = 0;
			if(innoteList.getPrice()!=null && innoteList.getQuantity()!=null){
				p = innoteList.getPrice();
				q = innoteList.getQuantity();
			}
			am = am + p*q;
			qu = qu + q;
		}
		this.amount = am;
		this.quantity = qu;
	}
	
	public Innote(String notenumber, String warehouse, String inboundType,String operatorid,String supplierid,List<InnoteList> innoteLists) {
		super();
		this.notenumber = notenumber;
		this.warehouse = warehouse;
		this.supplierid = supplierid;
		this.operatorid = operatorid;
		this.inboundType = inboundType;
		Double am = 0.0;
		Integer qu = 0;
		for (InnoteList innoteList : innoteLists) {
			Double p = 0.0;
			Integer q = 0;
			if(innoteList.getPrice()!=null && innoteList.getQuantity()!=null){
				p = innoteList.getPrice();
				q = innoteList.getQuantity();
			}
			am = am + p*q;
			qu = qu + q;
		}
		this.amount = am;
		this.quantity = qu;
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
	}
	

	
}
