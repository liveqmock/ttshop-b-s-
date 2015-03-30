package pojo;

import java.util.Date;

import tools.DateTool;

public class TransferList {
	
	private Integer id;
	private String transferno;
	private String barcode;
	private Integer quantity;
	private String description;
	private String imei;
	private String updatetime;
	private Integer status;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
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
	public TransferList(String transferno, String barcode,
			Integer quantity, String description, String imei) {
		super();
		this.transferno = transferno;
		this.barcode = barcode;
		this.quantity = quantity;
		this.description = description;
		this.imei = imei;
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
	}
	public TransferList() {
		super();
	}
	
	
	
}
