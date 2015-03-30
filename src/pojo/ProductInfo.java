package pojo;

import java.util.Date;
import tools.DateTool;

public class ProductInfo {
	
	private Integer id;
	private String ptype;
	private Integer itype; //0-非库存类型 1-库存类型 2-库存有IMEI
	private String barcode;
	private String pdesc;
	private String introduction;
	private String pbrand;
	private String pcolor;
	private Integer picture; //保存时候只保存id
	private Double sprice;
	private Double mprice;
	private Integer saleornot; //商品上线下架
	private String updateTime;
	private Integer status;
	private Picture image;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPtype() {
		return ptype;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	public String getBarcode() {
		return barcode.toUpperCase();
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode.toUpperCase();
	}
	public String getPdesc() {
		return pdesc;
	}
	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}
	public String getPbrand() {
		return pbrand;
	}
	public void setPbrand(String pbrand) {
		this.pbrand = pbrand;
	}
	public String getPcolor() {
		return pcolor;
	}
	public void setPcolor(String pcolor) {
		this.pcolor = pcolor;
	}
	public Integer getPicture() {
		return picture;
	}
	public void setPicture(Integer picture) {
		this.picture = picture;
	}
	public String getUpdateTime() {
		return updateTime.substring(0, 16);
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Double getSprice() {
		return sprice;
	}
	public void setSprice(Double sprice) {
		this.sprice = sprice;
	}
	public Double getMprice() {
		return mprice;
	}
	public void setMprice(Double mprice) {
		this.mprice = mprice;
	}
	public Integer getItype() {
		return itype;
	}
	public void setItype(Integer itype) {
		this.itype = itype;
	}
	public Picture getImage() {
		return image;
	}
	public void setImage(Picture image) {
		this.image = image;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Integer getSaleornot() {
		return saleornot;
	}
	public void setSaleornot(Integer saleornot) {
		this.saleornot = saleornot;
	}
	public ProductInfo() {
		super();
	}
	public ProductInfo(String ptype,Integer itype, String barcode, String pdesc) {
		super();
		this.itype = itype;
		this.ptype = ptype;
		this.barcode = barcode.toUpperCase();
		this.pdesc = pdesc;
		this.updateTime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
		this.saleornot = 0;
	}
	public ProductInfo(Ptype ptype, String barcode, String pdesc) {
		super();
		this.itype = ptype.getItype();
		this.ptype = ptype.getTypeName();
		this.barcode = barcode.toUpperCase();
		this.pdesc = pdesc;
		this.updateTime = DateTool.getInstance().DateToPattern2(new Date());
		this.status = 1;
		this.saleornot = 0;
	}
	
	
}
