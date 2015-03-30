package pojo;

import java.util.Date;
import java.util.List;

import tools.DateTool;

public class Transfer {
	
	private Integer id;
	private String transferno;
	private String receiverid;
	private String warehouse;
	private Integer totalquantity;
	private String reason;
	private String updatetime;
	private Integer status;
	private List<TransferList> transferLists;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTransferno() {
		return transferno;
	}
	public void setTransferno(String transferno) {
		this.transferno = transferno;
	}
	public String getReceiverid() {
		return receiverid;
	}
	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}
	public Integer getTotalquantity() {
		return totalquantity;
	}
	public void setTotalquantity(Integer totalquantity) {
		this.totalquantity = totalquantity;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public List<TransferList> getTransferLists() {
		return transferLists;
	}
	public void setTransferLists(List<TransferList> transferLists) {
		this.transferLists = transferLists;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public Transfer(String transferno,String receiverid,String warehouse,List<TransferList> transferLists, String reason) {
		super();
		this.transferno = transferno;
		this.receiverid = receiverid;
		this.warehouse = warehouse;
		Integer total = 0;
		for (TransferList tl : transferLists) {
			total = total+tl.getQuantity();
		}
		this.totalquantity = total;
		this.reason = reason;
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
	}
	public Transfer() {
		super();
	}
	
	
}
