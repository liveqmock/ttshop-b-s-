package pojo;

import java.util.Date;
import java.util.List;

import tools.DateTool;

public class ProductDetial {
	
	private Integer id;
	private String barcode;
	private String detail;
	private List<String> pictures; //urlµÿ÷∑
	private Integer status;
	private String updatetime;
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
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public List<String> getPictures() {
		return pictures;
	}
	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
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
	public ProductDetial(String barcode, String detail, List<String> pictures) {
		super();
		this.barcode = barcode;
		this.detail = detail;
		this.pictures = pictures;
		this.status = 1;
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
	}
	public ProductDetial() {
		super();
	}
	
	
	
}
