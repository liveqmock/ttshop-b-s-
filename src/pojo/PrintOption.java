package pojo;

import java.util.Date;

import tools.DateTool;

public class PrintOption {
	
	private Integer id;
	private String invoicetype; //A4all / POS / A4cut
	private String companyName;
	private String companyAddress;
	
	private String invoiceNo;
	private String invoicedate;
	private String remarks;
	private String otherremarks; //固定的不随发票变动的内容
	private String main;
	
	private Integer customWidth;
	private Integer customHeight;
	
	private Integer invoiceXpoint;
	private Integer invoiceYpoint;
	private Integer companyNameXpoint;
	private Integer companyNameYpoint;
	private Integer companyAddressXpoint;
	private Integer companyAddressYpoint;
	private Integer invoiceNoXpoint;
	private Integer invoiceNoYpoint;
	private Integer invoicedateXpoint;
	private Integer invoicedateYpoint;
	private Integer remarksXpoint;
	private Integer remarksYpoint;
	private Integer otherremarksXpoint;
	private Integer otherremarksYpoint;
	private Integer mainXpoint;
	private Integer mainYpoint;
	
	private Integer status;
	private String updatetime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInvoicetype() {
		return invoicetype;
	}
	public void setInvoicetype(String invoicetype) {
		this.invoicetype = invoicetype;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getInvoicedate() {
		return invoicedate;
	}
	public void setInvoicedate(String invoicedate) {
		this.invoicedate = invoicedate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOtherremarks() {
		return otherremarks;
	}
	public void setOtherremarks(String otherremarks) {
		this.otherremarks = otherremarks;
	}
	public String getMain() {
		return main;
	}
	public void setMain(String main) {
		this.main = main;
	}
	public Integer getInvoiceXpoint() {
		return invoiceXpoint;
	}
	public void setInvoiceXpoint(Integer invoiceXpoint) {
		this.invoiceXpoint = invoiceXpoint;
	}
	public Integer getInvoiceYpoint() {
		return invoiceYpoint;
	}
	public void setInvoiceYpoint(Integer invoiceYpoint) {
		this.invoiceYpoint = invoiceYpoint;
	}
	public Integer getCompanyNameXpoint() {
		return companyNameXpoint;
	}
	public void setCompanyNameXpoint(Integer companyNameXpoint) {
		this.companyNameXpoint = companyNameXpoint;
	}
	public Integer getCompanyNameYpoint() {
		return companyNameYpoint;
	}
	public void setCompanyNameYpoint(Integer companyNameYpoint) {
		this.companyNameYpoint = companyNameYpoint;
	}
	public Integer getCompanyAddressXpoint() {
		return companyAddressXpoint;
	}
	public void setCompanyAddressXpoint(Integer companyAddressXpoint) {
		this.companyAddressXpoint = companyAddressXpoint;
	}
	public Integer getCompanyAddressYpoint() {
		return companyAddressYpoint;
	}
	public void setCompanyAddressYpoint(Integer companyAddressYpoint) {
		this.companyAddressYpoint = companyAddressYpoint;
	}
	public Integer getInvoiceNoXpoint() {
		return invoiceNoXpoint;
	}
	public void setInvoiceNoXpoint(Integer invoiceNoXpoint) {
		this.invoiceNoXpoint = invoiceNoXpoint;
	}
	public Integer getInvoiceNoYpoint() {
		return invoiceNoYpoint;
	}
	public void setInvoiceNoYpoint(Integer invoiceNoYpoint) {
		this.invoiceNoYpoint = invoiceNoYpoint;
	}
	public Integer getInvoicedateXpoint() {
		return invoicedateXpoint;
	}
	public void setInvoicedateXpoint(Integer invoicedateXpoint) {
		this.invoicedateXpoint = invoicedateXpoint;
	}
	public Integer getInvoicedateYpoint() {
		return invoicedateYpoint;
	}
	public void setInvoicedateYpoint(Integer invoicedateYpoint) {
		this.invoicedateYpoint = invoicedateYpoint;
	}
	public Integer getRemarksXpoint() {
		return remarksXpoint;
	}
	public void setRemarksXpoint(Integer remarksXpoint) {
		this.remarksXpoint = remarksXpoint;
	}
	public Integer getRemarksYpoint() {
		return remarksYpoint;
	}
	public void setRemarksYpoint(Integer remarksYpoint) {
		this.remarksYpoint = remarksYpoint;
	}
	public Integer getOtherremarksXpoint() {
		return otherremarksXpoint;
	}
	public void setOtherremarksXpoint(Integer otherremarksXpoint) {
		this.otherremarksXpoint = otherremarksXpoint;
	}
	public Integer getOtherremarksYpoint() {
		return otherremarksYpoint;
	}
	public void setOtherremarksYpoint(Integer otherremarksYpoint) {
		this.otherremarksYpoint = otherremarksYpoint;
	}
	public Integer getMainXpoint() {
		return mainXpoint;
	}
	public void setMainXpoint(Integer mainXpoint) {
		this.mainXpoint = mainXpoint;
	}
	public Integer getMainYpoint() {
		return mainYpoint;
	}
	public void setMainYpoint(Integer mainYpoint) {
		this.mainYpoint = mainYpoint;
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
	public PrintOption() {
		super();
	}
	public Integer getCustomWidth() {
		return customWidth;
	}
	public void setCustomWidth(Integer customWidth) {
		this.customWidth = customWidth;
	}
	public Integer getCustomHeight() {
		return customHeight;
	}
	public void setCustomHeight(Integer customHeight) {
		this.customHeight = customHeight;
	}
	public PrintOption(String invoicetype, String companyName,
			String companyAddress, String invoiceNo, String invoicedate,
			String remarks, String otherremarks, String main,
			Integer invoiceXpoint, Integer invoiceYpoint,
			Integer companyNameXpoint, Integer companyNameYpoint,
			Integer companyAddressXpoint, Integer companyAddressYpoint,
			Integer invoiceNoXpoint, Integer invoiceNoYpoint,
			Integer invoicedateXpoint, Integer invoicedateYpoint,
			Integer remarksXpoint, Integer remarksYpoint,
			Integer otherremarksXpoint, Integer otherremarksYpoint,
			Integer mainXpoint, Integer mainYpoint) {
		super();
		this.invoicetype = invoicetype;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.invoiceNo = invoiceNo;
		this.invoicedate = invoicedate;
		this.remarks = remarks;
		this.otherremarks = otherremarks;
		this.main = main;
		this.invoiceXpoint = invoiceXpoint;
		this.invoiceYpoint = invoiceYpoint;
		this.companyNameXpoint = companyNameXpoint;
		this.companyNameYpoint = companyNameYpoint;
		this.companyAddressXpoint = companyAddressXpoint;
		this.companyAddressYpoint = companyAddressYpoint;
		this.invoiceNoXpoint = invoiceNoXpoint;
		this.invoiceNoYpoint = invoiceNoYpoint;
		this.invoicedateXpoint = invoicedateXpoint;
		this.invoicedateYpoint = invoicedateYpoint;
		this.remarksXpoint = remarksXpoint;
		this.remarksYpoint = remarksYpoint;
		this.otherremarksXpoint = otherremarksXpoint;
		this.otherremarksYpoint = otherremarksYpoint;
		this.mainXpoint = mainXpoint;
		this.mainYpoint = mainYpoint;
		this.status=1;
		this.updatetime = DateTool.getInstance().DateToPattern2(new Date());
	}
	
	
	
	
}
