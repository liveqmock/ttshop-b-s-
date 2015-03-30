package action;

import java.io.File;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import pojo.PrintOption;
import pojo.User;
import service.CompanyInfoService;
import service.CustomerService;
import service.EmployeeService;
import service.InnoteListService;
import service.InnoteService;
import service.MyidService;
import service.PictureCatalogService;
import service.PictureService;
import service.PrintOptionService;
import service.ProductService;
import service.ProductinfoService;
import service.PstockService;
import service.PtypeService;
import service.ReturnSaleListService;
import service.ReturnSaleService;
import service.SaleListService;
import service.SaleService;
import service.StockCheckingService;
import service.SupplierService;
import service.TransferListService;
import service.TransferService;
import service.UserService;
import service.WarehouseService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BaseAction extends ActionSupport {
	
	@Resource(name="employeeService")
	EmployeeService employeeService;
	@Resource(name="userService")
	UserService userService;
	@Resource(name="companyInfoService")
	CompanyInfoService companyInfoService;
	@Resource(name="customerService")
	CustomerService customerService;
	@Resource(name="supplierService")
	SupplierService supplierService;
	@Resource(name="warehouseService")
	WarehouseService warehouseService;
	@Resource(name="ptypeService")
	PtypeService ptypeService;
	@Resource(name="productinfoService")
	ProductinfoService productinfoService;
	@Resource(name="productService")
	ProductService productService;
	@Resource(name="pstockService")
	PstockService pstockService;
	@Resource(name="innoteService")
	InnoteService innoteService;
	@Resource(name="innoteListService")
	InnoteListService innoteListService;
	@Resource(name="saleService")
	SaleService saleService;
	@Resource(name="saleListService")
	SaleListService saleListService;
	@Resource(name="myidService")
	MyidService myidService;
	@Resource(name="transferService")
	TransferService transferService;
	@Resource(name="transferListService")
	TransferListService transferListService;
	@Resource(name="printOptionService")
	PrintOptionService printOptionService;
	@Resource(name="returnSaleService")
	ReturnSaleService returnSaleService;
	@Resource(name="returnSaleListService")
	ReturnSaleListService returnSaleListService;
	@Resource(name="pictureService")
	PictureService pictureService;
	@Resource(name="pictureCatalogService")
	PictureCatalogService pictureCatalogService;
	@Resource(name="stockCheckingService")
	StockCheckingService stockCheckingService;
	
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response  = ServletActionContext.getResponse();
	
	public static String INPUT = "INPUT";
	public static String ERROR = "ERROR";
	public static String SUCCESS = "SUCCESS";
	public static int totalVisted = 0;
	
	public static String NECESSARY = "*为必填项目";
	public static String ERRORMESSAGE = "操作错误,请联系管理员";
	public static String SUCCMESSAGE = "操作成功";
	public static String FILESEPARATOR = File.separator;
	
	public static final String ACCESSKEYID = "vLLrdpKJPCLUzd19";
	public static final String ACCESSKEYSECRET  = "BLb9Ju3tBgbX7fYNCNEDWbSQ7vGEN3";
	public static final String ENDPOINT ="http://oss-cn-shenzhen.aliyuncs.com"; //深圳点
	public static String BUCKETNAME = "ttshop";
	
	public String returnurl = request.getContextPath()+FILESEPARATOR+"homepage.jsp";
	public PrintOption option;
	public String msg;
	public String keyword;
	
	public File file;
	
	public void setMessage(String message){
		request.setAttribute("me", message);
		this.setMsg(message);
	}
	
	public boolean checkLogin(User user){
		if(user!=null){
			return true;
		}
		return false;
	}
	
	public EmployeeService getEmployeeService() {
		return employeeService;
	}
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public CompanyInfoService getCompanyInfoService() {
		return companyInfoService;
	}
	public void setCompanyInfoService(CompanyInfoService companyInfoService) {
		this.companyInfoService = companyInfoService;
	}
	public CustomerService getCustomerService() {
		return customerService;
	}
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	public SupplierService getSupplierService() {
		return supplierService;
	}
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}
	public WarehouseService getWarehouseService() {
		return warehouseService;
	}
	public void setWarehouseService(WarehouseService warehouseService) {
		this.warehouseService = warehouseService;
	}
	public PtypeService getPtypeService() {
		return ptypeService;
	}
	public void setPtypeService(PtypeService ptypeService) {
		this.ptypeService = ptypeService;
	}
	public ProductinfoService getProductinfoService() {
		return productinfoService;
	}
	public void setProductinfoService(ProductinfoService productinfoService) {
		this.productinfoService = productinfoService;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public PstockService getPstockService() {
		return pstockService;
	}
	public void setPstockService(PstockService pstockService) {
		this.pstockService = pstockService;
	}
	public InnoteService getInnoteService() {
		return innoteService;
	}
	public void setInnoteService(InnoteService innoteService) {
		this.innoteService = innoteService;
	}
	public ProductService getProductService() {
		return productService;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	public InnoteListService getInnoteListService() {
		return innoteListService;
	}
	public void setInnoteListService(InnoteListService innoteListService) {
		this.innoteListService = innoteListService;
	}
	public SaleService getSaleService() {
		return saleService;
	}
	public void setSaleService(SaleService saleService) {
		this.saleService = saleService;
	}
	public SaleListService getSaleListService() {
		return saleListService;
	}
	public void setSaleListService(SaleListService saleListService) {
		this.saleListService = saleListService;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public TransferService getTransferService() {
		return transferService;
	}
	public void setTransferService(TransferService transferService) {
		this.transferService = transferService;
	}
	public TransferListService getTransferListService() {
		return transferListService;
	}
	public void setTransferListService(TransferListService transferListService) {
		this.transferListService = transferListService;
	}
	public MyidService getMyidService() {
		return myidService;
	}
	public void setMyidService(MyidService myidService) {
		this.myidService = myidService;
	}

	public PrintOptionService getPrintOptionService() {
		return printOptionService;
	}

	public void setPrintOptionService(PrintOptionService printOptionService) {
		this.printOptionService = printOptionService;
	}

	public PrintOption getOption() {
		return option;
	}

	public void setOption(PrintOption option) {
		this.option = option;
	}
	public ReturnSaleService getReturnSaleService() {
		return returnSaleService;
	}
	public void setReturnSaleService(ReturnSaleService returnSaleService) {
		this.returnSaleService = returnSaleService;
	}
	public ReturnSaleListService getReturnSaleListService() {
		return returnSaleListService;
	}
	public void setReturnSaleListService(ReturnSaleListService returnSaleListService) {
		this.returnSaleListService = returnSaleListService;
	}
	public String getReturnurl() {
		return returnurl;
	}
	public void setReturnurl(String returnurl) {
		this.returnurl = returnurl;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	

	
	
	
	
	
	
	
}
