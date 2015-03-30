package action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import pojo.Customer;
import pojo.Page;
import pojo.Product;
import pojo.ProductInfo;
import pojo.SaleRecord;
import pojo.SaleRecordList;
import pojo.Warehouse;
import tools.CheckTool;
import tools.DateTool;
import tools.ExcelTool;
import tools.JsonTool;
import tools.SortTool;

@SuppressWarnings("serial")
public class SaleAction extends BaseAction {
	private SaleRecord saleRecord;
	private List<SaleRecord> saleRecords;
	private SaleRecordList recordList;
	private List<SaleRecordList> recordLists;
	private List<Object[]> objects;
	private List<Object[]> typestatement;
	private String today;
	private String outstr;
	private Integer totalCount;
	private Integer[] lines;
	private String[] imeis;
	private Integer[] quans;
	private String[] barcodes;
	private Double[] prices;
	private String[] descs;
	private Double[] amounts;
	private Integer paidtype; //0,1,2,3,4,5
	private String currency; //hkd mop rmb
	private String customer;
	private String remark;
	private String date;
	private InputStream inputStream;
	private Page page;
	private String begindate;
	private String enddate;
	private Double discount;
	private Double totalamount;
	private Integer totalquantity;
	private Warehouse warehouse2;
	private String warehouse;
	private Map<String, List<SaleRecordList>> outputList;
	private List<Warehouse> warehouses;
	private String keyword;
	
	/**
	 * 新通讯用,跳转至开单页面
	 * @return
	 * @throws Exception
	 */
	public String toCreateOutDetial() throws Exception{
		String flag = request.getParameter("flag");
		List<Customer> customers = this.customerService.findByKeyword("");
		warehouses = this.warehouseService.findByKeyword("");
		List<String[]> list = new ArrayList<String[]>();
		for (Customer customer : customers) {
			String[] strs = {customer.getCustomerName(),customer.getCustomerTel()+"|"+customer.getCustomerName(),customer.getCustomerAddress()};
			list.add(strs);
		}
		outstr = "var customers = "+ JsonTool.getInstance().formList(list,"value","label","desc")+";";
		if(flag.equalsIgnoreCase("excel")){
			return "EXCEL";
		}
		if(flag.equalsIgnoreCase("none")){
			List<ProductInfo> infos = this.productinfoService.findByKeyword("");
			List<String[]> list1 = new ArrayList<String[]>();
			for (ProductInfo productInfo : infos) {
				String[] args = {productInfo.getBarcode(),productInfo.getPdesc()};
				list1.add(args);
			}
			date = DateTool.getInstance().DateToPattern1(new Date());
			outstr = outstr + "var infos = "+ JsonTool.getInstance().formList(list1,"value","label")+";";
			return "NONE";
		}
		this.setMessage(ERRORMESSAGE);
		return ERROR;
	}
	
	/**
	 * 新通讯用,已作废
	 * @return
	 * @throws Exception
	 *//*
	public String createOutDetailfromExcel() throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		if(user==null || user.getUserid().equals("")){
			this.setMessage("请重新登陆！");
			return LOGIN;
		}
		if(file==null || !file.exists()){
			this.setMessage("请选择出货Excel表格！");
			return INPUT;
		}
		if(customer.trim().equalsIgnoreCase("")){
			this.setMessage("请输入客户名称！");
			return INPUT;
		}
		Workbook workbook = Workbook.getWorkbook(file);
		Sheet sheet = workbook.getSheet(0);
		List<SaleRecordList> temrecordLists = new ArrayList<SaleRecordList>();
		recordLists = new ArrayList<SaleRecordList>();
		List<String> sortlist = new ArrayList<String>();
		String date = DateTool.getInstance().DateToPattern1(new Date());
		String sa = this.myidService.createIDNumber("SA-");
		Double temprice = 0.0;
		for (int i = 0; i < sheet.getRows(); i++) {
			String b = sheet.getCell(0, i).getContents();
			String im = sheet.getCell(1, i).getContents();
			if(CheckTool.getInstance().checkNull(b,im)){
				SaleRecordList temrecord = new SaleRecordList(sa, 1, b, b, 1, temprice);
				temrecord.setRemark(im);
				temrecordLists.add(temrecord);
			}
		}
		
		int n = temrecordLists.size();
		String[] strss = new String[n];
		for (int i=0;i<temrecordLists.size();i++) {
			strss[i] = temrecordLists.get(i).getBarcode();
		}
		sortlist = SortTool.getInstance().sortString(strss);
		//输出
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.reset();
		WritableWorkbook workbook2 = Workbook.createWorkbook(outputStream);
		WritableSheet detial = workbook2.createSheet("DETIAL", 0);
		WritableSheet invoice = workbook2.createSheet("INVOICE", 1);
		*//** 
         * 定义单元格样式 
         *//*  
		WritableCellFormat format = new WritableCellFormat();
		format.setAlignment(jxl.format.Alignment.CENTRE);
		format.setBorder(Border.ALL,BorderLineStyle.THIN, Colour.BLACK);
		
		//init sheet
		ExcelTool excelTool = new ExcelTool();
		excelTool.initDetailAndInvoiceSheet(detial, invoice, sa, customer, date);
		
		int row = 3;
		int column = 0;
		int row2 = 4;
		int line = 1;
		for(int i=0;i<sortlist.size();i++){
			String barcode = sortlist.get(i);
			List<String> lineimeilist = new ArrayList<String>();
			ProductInfo info = productinfoService.findByBaecode(barcode);
			if(info==null){
				info = new ProductInfo(2, "Mobile", barcode, barcode);
				info.setSprice(temprice);
			}
			String pdesc = info.getPdesc();
			Label label3 = new Label(column, row, pdesc, format);
			detial.addCell(label3);
			row++;
			int q = 0;
			for (SaleRecordList temrecordList:temrecordLists) {
				if(temrecordList.getBarcode().equalsIgnoreCase(barcode)){
					String imei = temrecordList.getRemark();
					Label label4 = new Label(column, row, imei, format);
					detial.addCell(label4);
					row++;
					q++;
					if(info.getItype().equals(2) && !CheckTool.getInstance().checkNull(imei)){
						this.setMessage(barcode+"欠IMEI");
						return INPUT;
					}
					lineimeilist.add(imei);
				}
			}
			SaleRecordList recordList = new SaleRecordList(sa, line, barcode, pdesc, q, info.getSprice());
			recordList.setRemark(SortTool.getInstance().listToString(lineimeilist));
			recordLists.add(recordList);
			label3.setString(pdesc+"  X"+q);
			excelTool.setInvoiceLineAmount(invoice,row2,info,q); //set invoice line
			row2++;
			line++;
		}
		//add to database
		{
			saleRecord = new SaleRecord(sa,warehouse,customer, user.getUsername(), recordLists, currency, paidtype, 0.0,1.0);
			saleRecord.setPaidstatus(0); // 设置未付
			this.saleListService.addManyObjects(recordLists);
			this.saleService.add(saleRecord);
			Label label0 = (Label) detial.getCell(0, 0);
			label0.setString(sa+" 共  "+saleRecord.getTotalquantity());
		}
		excelTool.setInvoiceFormula(invoice);
		excelTool.endformat(detial, invoice); //set invoice format
		workbook2.write();
		workbook2.close();
		response.reset();
		response.setContentType("application/vn.ms-excel");
		response.setCharacterEncoding("utf-8");
		try {
			this.inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			outputStream.close();
		}
		return SUCCESS;
	}*/
	
	/**
	 * WEB页面出货
	 * @return
	 * @throws Exception
	 *//*
	public String createOutDetail() throws Exception{
		User user = (User) request.getSession().getAttribute("user");
		if(user==null){
			this.setMessage("请重新登陆！");
			return LOGIN;
		}
		if(customer==null || customer.trim().equals("")){
			this.setMessage("请输入客户名称！");
			return ERROR;
		}
		if (warehouse2==null || warehouse2.getId()==null) {
			this.setMessage("请选择出货仓库");
			return ERROR;
		}
		warehouse2 = this.warehouseService.get(Warehouse.class, warehouse2.getId());
		warehouse = warehouse2.getWname();
		System.out.println(warehouse);
		List<SaleRecordList> temrecordLists = new ArrayList<SaleRecordList>();
		recordLists = new ArrayList<SaleRecordList>();
		List<String> sortlist = new ArrayList<String>();
		if(date==null || date.trim().equals("")){
			date = DateTool.getInstance().DateToPattern1(new Date());
		}
		Double temprice = 0.0;
		for (int i = 0; i < barcodes.length; i++){
			String b = barcodes[i].trim();
			String im = imeis[i].trim();
			if(!b.trim().equals("")){
				SaleRecordList temrecord = new SaleRecordList("temp", 1, b, b, 1, temprice);
				temrecord.setRemark(im);
				temrecordLists.add(temrecord);
			}
		}
		int n = temrecordLists.size();
		if(n==0){
			return INPUT;
		}
		String[] strss = new String[n];
		for (int i=0;i<temrecordLists.size();i++){
			strss[i] = temrecordLists.get(i).getBarcode();
		}
		sortlist = SortTool.getInstance().sortString(strss);
		String sa = "TEMP-"+DateTool.getInstance().DateToPattern3(new Date());
		int line = 1; //项目行
		for(int i=0;i<sortlist.size();i++){
			String barcode = sortlist.get(i);
			List<String> lineImeis = new ArrayList<String>();
			ProductInfo info = productinfoService.findByBaecode(barcode);
			if(info==null){
				info = new ProductInfo(2, "Mobile", barcode, barcode);
				info.setSprice(temprice);
			}
			for (SaleRecordList srl:temrecordLists){
				if(srl.getBarcode().equalsIgnoreCase(barcode)){
					String imei = srl.getRemark();
					if(info.getItype().equals(2) && !CheckTool.getInstance().checkNull(imei)){
						this.setMessage(barcode+"欠IMEI");
						return INPUT;
					}
					lineImeis.add(imei);
				}
			}
			SaleRecordList recordList = new SaleRecordList(sa, line, info.getBarcode(), info.getPdesc(), lineImeis.size(), info.getSprice());
			recordList.setRemark(SortTool.getInstance().listToString(lineImeis));
			recordLists.add(recordList);
			line++;
		}
		saleRecord = new SaleRecord(sa,warehouse, customer,user.getUsername(), recordLists, currency, paidtype, 0.0,1.0);
		saleRecord.setPaidstatus(0);
		try {
			request.setAttribute("recordLists", recordLists);
			request.setAttribute("saleRecord", saleRecord);
			return SUCCESS;
		} catch (Exception e) {
			this.setMessage(e.getMessage());
			return ERROR;
		}
	}*/
	
	/**
	 * 已作废
	 * @return
	 * @throws Exception
	 *//*
	public String addSale() throws Exception{
		User user = (User) request.getSession().getAttribute("user");
		//currency = "HKD";
		//paidtype = 0; //--现金
		if(user==null || user.getUserid().equals("")){
			this.setMessage("请重新登录");
			return LOGIN;
		}
		recordLists = new ArrayList<SaleRecordList>();
		List<Product> products = new ArrayList<Product>();
		String invoiceno = this.myidService.createIDNumber("SA-");
		System.out.println();
		for (int i = 0; i < barcodes.length; i++) {
			String b = barcodes[i];
			ProductInfo info = this.productinfoService.findByBaecode(b);
			if (info==null) {
				return ERROR;
			}
			SaleRecordList recordList = new SaleRecordList(invoiceno, lines[i], info.getBarcode(), descs[i], quans[i], prices[i]);
			if(info.getItype().equals(2)){
				String temp = imeis[i];
				List<String> ims = SortTool.getInstance().stringToList(temp);
				for (String string : ims) {
					System.out.println(string + warehouse);
					Product product  = this.productService.findByImeiAndWarehouse(string, warehouse);
					if (product==null) {
						return ERROR;
					}
					products.add(product);
				}
			}
			recordList.setRemark(imeis[i]);
			recordLists.add(recordList);
		}
		saleRecord = new SaleRecord(invoiceno,warehouse, customer, user.getUserid(), recordLists, currency, paidtype, discount,1.0);
		saleRecord.setPaidstatus(0); //0-未处理 
		saleRecord.setCreatetime(date);
		saleRecord.setRemark(remark);
		try {
			
			this.productService.saleOrTransferProduct(products);
			this.pstockService.updateSale(recordLists, warehouse);
			this.saleListService.addManyObjects(recordLists);
			this.saleService.add(saleRecord);
			return SUCCESS;
		} catch (Exception e) {
			this.setMessage(e.getMessage());
			return ERROR;
		}
	}*/
	
	
	/**
	 * 没有用到
	 * @return
	 * @throws Exception
	 */
	public String toupdate() throws Exception{
		String invoiceno = request.getParameter("keyword");
		saleRecord = this.saleService.findByInvoiceNo(invoiceno);
		recordLists = this.saleListService.listByInvoiceNo(invoiceno);
		if(saleRecord!=null && recordLists.size()>0){
			return SUCCESS;
		}
		return INPUT;
	}
	
	/**
	 * 新通讯用
	 * @return
	 * @throws Exception
	 *//*
	public String recreateOutDetail() throws Exception{
		String keyword = request.getParameter("keyword");
		List<SaleRecordList> temrecordLists = this.saleListService.listByInvoiceNo(keyword);
		saleRecord = this.saleService.findByInvoiceNo(keyword);
		if(saleRecord!=null && temrecordLists.size()>0){
			String sa = saleRecord.getInvoiceno();
			String customer = saleRecord.getCustomerid();
			String date = saleRecord.getCreatetime().substring(0, 10);
			
			//输出
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			outputStream.reset();
			WritableWorkbook workbook2 = Workbook.createWorkbook(outputStream);
			WritableSheet detial = workbook2.createSheet("DETIAL", 0);
			WritableSheet invoice = workbook2.createSheet("INVOICE", 1);
			
			*//** 
	         * 定义单元格样式 
	         *//*  
			WritableCellFormat format = new WritableCellFormat();
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setBorder(Border.ALL,BorderLineStyle.THIN, Colour.BLACK);
			
			//init sheet
			ExcelTool excelTool = new ExcelTool();
			excelTool.initDetailAndInvoiceSheet(detial, invoice, sa, customer, date);
			
			int row = 3;
			int column = 0;
			int row2 = 4;
			for(int i=0;i<temrecordLists.size();i++){
				SaleRecordList saleRecordList = temrecordLists.get(i);
				String barcode = saleRecordList.getBarcode();
				ProductInfo info = productinfoService.findByBaecode(barcode);
				List<String> imeis = SortTool.getInstance().stringToList(saleRecordList.getRemark());
				if(info==null){
					info = new ProductInfo(2, "Mobile", barcode, barcode);
				}
				info.setSprice(saleRecordList.getPrice());
				String pdesc = info.getPdesc();
				Label label3 = new Label(column, row, pdesc, format);
				detial.addCell(label3);
				row++;
				int q = saleRecordList.getQuantity();
				for (String imei:imeis) {
						Label label4 = new Label(column, row, imei, format);
						detial.addCell(label4);
						row++;
				}
				label3.setString(pdesc+"  X"+q);
				excelTool.setInvoiceLineAmount(invoice,row2,info,q); //set invoice line
				row2++;
			}
			Label label0 = (Label) detial.getCell(0, 0);
			label0.setString(sa+" 共  "+saleRecord.getTotalquantity());
			
			excelTool.setInvoiceFormula(invoice);
			excelTool.endformat(detial, invoice);//set invoice format
			workbook2.write();
			workbook2.close();
			response.reset();
			response.setContentType("application/vn.ms-excel");
			response.setCharacterEncoding("utf-8");
			try {
				this.inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				outputStream.close();
			}
			return SUCCESS;
		}
		this.setMessage("没有此单号");
		return INPUT;
	}*/
	
	
	/**
	 * 一张销售单的明细项
	 * @return
	 * @throws RuntimeException
	 */
	public String findlistByInvoice() throws RuntimeException{
		String keyword = request.getParameter("keyword");
		saleRecord = this.saleService.findByInvoiceNo(keyword);
		recordLists = this.saleListService.listByInvoiceNo(keyword);
		if(saleRecord!=null && !recordLists.isEmpty()){
			return SUCCESS;
		}
		this.setMessage("没有此单号");
		return INPUT;
	}
	
	
	/**
	 * 销售记录
	 * @return
	 * @throws Exception
	 */
	public String findRecordByDate() throws Exception{
		warehouses = this.warehouseService.findByKeyword("");
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"salefunction.jsp");
		if(keyword==null){
			keyword = "";
		}
		if(warehouse==null){
			warehouse = "all";
		}
		if(begindate==null || begindate.trim().equals("")){
			begindate = DateTool.getInstance().DateToPattern1(new Date());
		}
		if(enddate==null || enddate.trim().equals("")){
			enddate = DateTool.getInstance().DateToPattern1(new Date());
		}
		if(warehouse.equals("all")){
			Integer allrows = this.saleService.findBykeywordallrow(keyword,begindate+" 00:00:00", enddate+" 23:59:59");
			if(page==null){
				page = new Page(1, 200, allrows);
			}else{
				page.setMaxResult(200);
				page.setAllRows(allrows);
			}
			saleRecords=this.saleService.findBykeyword(keyword, begindate+" 00:00:00", enddate+" 23:59:59",page.getFirstResult(),page.getMaxResult());
		}else{
			Integer allrows = this.saleService.findBykeywordallrow(keyword,warehouse,begindate+" 00:00:00", enddate+" 23:59:59");
			if(page==null){
				page = new Page(1, 200, allrows);
			}else{
				page.setMaxResult(200);
				page.setAllRows(allrows);
			}
			saleRecords=this.saleService.findBykeyword(keyword,warehouse,begindate+" 00:00:00", enddate+" 23:59:59",page.getFirstResult(),page.getMaxResult());
		}
		return SUCCESS;
	}
	
	
	/**
	 * 作废一张销售单
	 * @return
	 * @throws Exception
	 */
	public String cancelByInvoice() throws Exception{
		String keyword = request.getParameter("keyword");
		recordLists = this.saleListService.listByInvoiceNo(keyword);
		saleRecord = this.saleService.findByInvoiceNo(keyword);
		List<Product> products = new ArrayList<Product>();
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"sale"+FILESEPARATOR+"findRecordByDate.action?keyword=s-");
		if(saleRecord!=null){
			saleRecord.setStatus(0);
			if(recordLists!=null && recordLists.size()>0){
				for (SaleRecordList list : recordLists) {
					list.setStatus(0);
					Integer quantity = -list.getQuantity();
					list.setQuantity(quantity);
					ProductInfo info = this.productinfoService.findByBaecode(list.getBarcode());
					if(info.getItype().equals(2)){
						String imeis = list.getRemark();
						List<String> ims = SortTool.getInstance().stringToList(imeis);
						for (String string : ims) {
							Product product = this.productService.findByImeiAndWarehouseNoLimit(string, saleRecord.getWarehouse());
							if(product==null){
								this.setMessage("没有此产品 "+string);
								return ERROR;
							}
							products.add(product);
						}
					}
				}
			Customer customer = this.customerService.findById(saleRecord.getCustomerid());
			if(customer!=null){
				int creditcut = saleRecord.getTotalamount().intValue();
				customer.setCredits(customer.getCredits()-creditcut);
				this.customerService.update(customer);
			}
			this.pstockService.updateSale(recordLists, saleRecord.getWarehouse()); //销售扣库存
			this.productService.calcelSaleOrTransferProduct(products); //有imei的产品作库存处理
			this.saleService.update(saleRecord);
			this.saleListService.updateManyObjects(recordLists);
			this.setMessage("操作成功");
			return SUCCESS;
			}
		}
		this.setMessage("没有找到此单号！");
		return INPUT;
	}
	
	/**
	 * 彻底删除,,从数据库移除,与作废不一样
	 * @return
	 * @throws Exception
	 */
	public String deleteByInvoice() throws Exception{
		String keyword = request.getParameter("keyword");
		recordLists = this.saleListService.listByInvoiceNo(keyword);
		saleRecord = this.saleService.findByInvoiceNo(keyword);
		if(saleRecord!=null){
			if(recordLists!=null && recordLists.size()>0){
				saleService.delete(saleRecord);
				saleListService.deleteManyObjects(recordLists);
				this.setMessage("successfully delete ："+saleRecord.getInvoiceno());
				return SUCCESS;
			}
		}
		this.setMessage("没有找到此单号！");
		return INPUT;
	}
	
	/**
	 * 生成原始导入文件
	 * @return
	 * @throws Exception
	 */
	public String createOriginalExcel() throws Exception{
		String keyword = request.getParameter("keyword");
		recordLists = this.saleListService.listByInvoiceNo(keyword);
		if(recordLists.size()>0){
			ByteOutputStream outputStream = new ByteOutputStream();
			WritableWorkbook workbook  = Workbook.createWorkbook(outputStream);
			WritableSheet sheet = workbook.createSheet("sheet1", 0);
			int row = 0;
			for (int i = 0 ;i<recordLists.size();i++) {
				SaleRecordList recordList = recordLists.get(i);
				String barcode = recordList.getBarcode();
				List<String> imeis = SortTool.getInstance().stringToList(recordList.getRemark());
				for (String imei : imeis) {
					Label ba = new Label(0, row, barcode);
					Label im = new Label(1, row, imei);
					sheet.addCell(ba);
					sheet.addCell(im);
					row++;
				}
			}
			for (int i = 0; i < sheet.getColumns(); i++) {
				sheet.setColumnView(i, 30);
			}
			for (int i = 0; i < sheet.getRows(); i++) {
				sheet.setRowView(i, 300);
			}
			workbook.write();
			workbook.close();
			response.reset();
			response.setContentType("application/vn.ms-excel");
			response.setCharacterEncoding("utf-8");
			this.inputStream = new ByteArrayInputStream(outputStream.getBytes());
			return SUCCESS;
		}
		this.setMessage("找不到相关资料！");
		return INPUT;
	}
	
	/**
	 * 出货统计
	 * @return
	 * @throws Exception
	 */
	public String listCount() throws Exception{
		String begindate = request.getParameter("begindate");
		String enddate = request.getParameter("enddate");
		if(begindate==null || begindate.trim()==""){
			begindate = DateTool.getInstance().DateToPattern1(new Date());
		}
		if(enddate==null || enddate.trim()==""){
			enddate  = DateTool.getInstance().DateToPattern1(new Date());
		}
		request.setAttribute("begindate", begindate);
		request.setAttribute("enddate", enddate);
		begindate = begindate +" 00:00:00";
		enddate = enddate +" 23:59:59";
		objects = this.saleListService.countSale(begindate, enddate);
		totalCount = 0;
		for (Object[] object : objects) {
		 String qua = object[3].toString();
		 String barcode = object[1].toString();
		 String itype = object[4].toString();
		 if(itype.equals("2")){
			 String imeis = this.saleListService.findAllImes(barcode);
			 object[0] = imeis;
		 }
		 Integer qu = Integer.valueOf(qua);
		 totalCount = totalCount+qu;
		}
		return SUCCESS;
	}
	
	/**
	 * 查找出货Imei信息
	 * @return
	 * @throws Exception
	 */
	public String findByImei() throws Exception{
		String imei = request.getParameter("imei");
		recordLists = this.saleListService.findByImei(imei);
		return SUCCESS;
	}
	
	/**
	 * 出货数据备份
	 * @return
	 * @throws Exception
	 */
	public String backupSalerecord() throws Exception{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		os.reset();
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		WritableSheet sheet = workbook.createSheet("salerecordlist", 0);
		WritableSheet sheet2 = workbook.createSheet("salerecord", 1);
		List<SaleRecordList> saleRecordLists = this.saleListService.listAll();
		List<SaleRecord> saleRecords = this.saleService.listAll();
		int r = 0;
		{
			Label label1 = new Label(0, r,"invoiceno"); //invoiceno
			Label label2 = new Label(1, r,"line"); //customerid
			Label label3 = new Label(2, r,"barcode"); //barcode
			Label label4 = new Label(3, r,"pdesc"); //pdesc
			Label label5 = new Label(4, r,"remarks"); //imeis
			Label label6 = new Label(5, r,"quantity"); 
			Label label7 = new Label(6, r,"price"); 
			Label label8 = new Label(7, r,"amount"); 
			Label label9 = new Label(8, r,"updatetime"); 
			Label label10 = new Label(9, r,"status");
			
			sheet.addCell(label1);
			sheet.addCell(label2);
			sheet.addCell(label3);
			sheet.addCell(label4);
			sheet.addCell(label5);
			sheet.addCell(label6);
			sheet.addCell(label7);
			sheet.addCell(label8);
			sheet.addCell(label9);
			sheet.addCell(label10);
		}
		r++;
		for (SaleRecordList sl:saleRecordLists) {
			Label label1 = new Label(0, r,sl.getInvoiceno()); 
			Number label2 = new Number(1, r,sl.getInvoiceline());
			Label label3 = new Label(2, r,sl.getBarcode()); 
			Label label4 = new Label(3, r,sl.getPdesc());
			Label label5 = new Label(4, r,sl.getRemark()); 
			Number label6 = new Number(5, r,sl.getQuantity()); 
			Number label7 = new Number(6, r,sl.getPrice()); 
			Number label8 = new Number(7, r,sl.getAmount()); 
			Label label9 = new Label(8, r,sl.getUpdatetime()); 
			Number label10 = new Number(9, r,sl.getStatus());
			sheet.addCell(label1);
			sheet.addCell(label2);
			sheet.addCell(label3);
			sheet.addCell(label4);
			sheet.addCell(label5);
			sheet.addCell(label6);
			sheet.addCell(label7);
			sheet.addCell(label8);
			sheet.addCell(label9);
			sheet.addCell(label10);
			r++;
		}
		int r2 = 0;
		{
			Label label01 = new Label(0, r2, "invoiceno");
			Label label02 = new Label(1, r2, "warehouse");
			Label label03 = new Label(2, r2, "customerid");
			Label label04 = new Label(3, r2, "totalquantity");
			Label label05 = new Label(4, r2, "totalamount");
			Label label06 = new Label(5, r2, "discount");
			Label label07 = new Label(6, r2, "freight");
			Label label08 = new Label(7, r2, "othercharge");
			Label label09 = new Label(8, r2, "operatorid");
			Label label10 = new Label(9, r2, "currency");
			Label label11 = new Label(10, r2, "paidtype");
			Label label12 = new Label(11, r2, "paidstatus");
			Label label13 = new Label(12, r2, "remark");
			Label label14 = new Label(13, r2, "paidtime");
			Label label15 = new Label(14, r2, "createtime");
			Label label16 = new Label(15, r2, "updatetime");
			Label label17 = new Label(16, r2, "status");
			Label label18 = new Label(17, r2,"discountrate(%)");
			sheet2.addCell(label01);
			sheet2.addCell(label02);
			sheet2.addCell(label03);
			sheet2.addCell(label04);
			sheet2.addCell(label05);
			sheet2.addCell(label06);
			sheet2.addCell(label07);
			sheet2.addCell(label08);
			sheet2.addCell(label09);
			sheet2.addCell(label10);
			sheet2.addCell(label11);
			sheet2.addCell(label12);
			sheet2.addCell(label13);
			sheet2.addCell(label14);
			sheet2.addCell(label15);
			sheet2.addCell(label16);
			sheet2.addCell(label17);
			sheet2.addCell(label18);
		}
		r2++;
		for (SaleRecord sr : saleRecords) {
			Label label01 = new Label(0, r2, sr.getInvoiceno());
			Label label02 = new Label(1, r2, sr.getWarehouse());
			Label label03 = new Label(2, r2, sr.getCustomerid());
			Number label04 = new Number(3, r2, sr.getTotalquantity());
			Number label05 = new Number(4, r2, sr.getTotalamount());
			Number label06 = new Number(5, r2, sr.getDiscount());
			if(sr.getFreight()!=null){
				Number label07 = new Number(6, r2, sr.getFreight());
				sheet2.addCell(label07);
			}
			if(sr.getFreight()!=null){
				Number label08 = new Number(7, r2, sr.getOthercharge());
				sheet2.addCell(label08);
			}
			Label label09 = new Label(8, r2, sr.getOperatorid());
			Label label10 = new Label(9, r2, sr.getCurrency());
			Number label11 = new Number(10, r2, sr.getPaidtype());
			Number label12 = new Number(11, r2, sr.getPaidstatus());
			Label label13 = new Label(12, r2, sr.getRemark());
			Label label14 = new Label(13, r2, sr.getPaidtime());
			Label label15 = new Label(14, r2, sr.getCreatetime());
			Label label16 = new Label(15, r2, sr.getUpdatetime());
			if(sr.getDiscount()!=null){
				Number label17 = new Number(16, r2, sr.getStatus());
				sheet2.addCell(label17);
			}
			if(sr.getDiscountrate()!=null){
				Number label18 = new Number(17, r2, sr.getDiscountrate());
				sheet2.addCell(label18);
			}
			sheet2.addCell(label01);
			sheet2.addCell(label02);
			sheet2.addCell(label03);
			sheet2.addCell(label04);
			sheet2.addCell(label05);
			sheet2.addCell(label06);
			sheet2.addCell(label09);
			sheet2.addCell(label10);
			sheet2.addCell(label11);
			sheet2.addCell(label12);
			sheet2.addCell(label13);
			sheet2.addCell(label14);
			sheet2.addCell(label15);
			sheet2.addCell(label16);
			r2++;
		}
		
		ExcelTool excelTool = new ExcelTool();
		excelTool.backupexcel(sheet);
		excelTool.backupexcel(sheet2);
		workbook.write();
		workbook.close();
		this.inputStream = new ByteArrayInputStream(os.toByteArray());
		os.close();
		return SUCCESS;
	}
	
	/**
	 * 备份恢复
	 * @return
	 * @throws Exception
	 */
	public String recover() throws Exception{
		if(file!=null || file.exists()){
			CheckTool checkTool = CheckTool.getInstance();
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheet1 = workbook.getSheet(0);
			Sheet sheet2 = workbook.getSheet(1);
			List<SaleRecordList> newsalRecordLists = new ArrayList<SaleRecordList>();
			List<SaleRecord> newSaleRecords = new ArrayList<SaleRecord>(); 
			for (int i = 0; i < sheet1.getRows(); i++) {
					String invoiceno = sheet1.getCell(0, i).getContents();
					String invoiceline = sheet1.getCell(1, i).getContents();
					String barcode = sheet1.getCell(2, i).getContents();
					String pdesc = sheet1.getCell(3, i).getContents();
					String remarks = sheet1.getCell(4, i).getContents();
					String quantity = sheet1.getCell(5, i).getContents();
					String price = sheet1.getCell(6, i).getContents();
					String amount = sheet1.getCell(7, i).getContents();
					String updatetime = sheet1.getCell(8, i).getContents();
					String status =sheet1.getCell(9, i).getContents();
					
					if(checkTool.checkNumbers(invoiceline,quantity,price,amount,status)){
						Integer line = Integer.valueOf(invoiceline);
						Integer q = Integer.valueOf(quantity);
						Double p  = Double.valueOf(price);
						Double a = Double.valueOf(amount);
						Integer s = Integer.valueOf(status);
						//create a new salelist
						SaleRecordList saleRecordList = new SaleRecordList(invoiceno, line, barcode, pdesc, q, p);
						saleRecordList.setRemark(remarks);
						saleRecordList.setAmount(a);
						saleRecordList.setUpdatetime(updatetime);
						saleRecordList.setStatus(s);
						newsalRecordLists.add(saleRecordList);//add to list
					}
			}
			for (int i = 0; i < sheet2.getRows(); i++) {
				String invoiceno = sheet2.getCell(0, i).getContents();
				String warehouse = sheet2.getCell(1, i).getContents();
				String customerid = sheet2.getCell(2, i).getContents();
				String totalquantity = sheet2.getCell(3, i).getContents();
				String totalamount = sheet2.getCell(4, i).getContents();
				String discount = sheet2.getCell(5, i).getContents();
				String freight = sheet2.getCell(6, i).getContents();
				String othercharge = sheet2.getCell(7, i).getContents();
				String operatorid = sheet2.getCell(8, i).getContents();
				String currency = sheet2.getCell(9, i).getContents();
				String paidtype = sheet2.getCell(10, i).getContents();
				String paidstatus = sheet2.getCell(11, i).getContents();
				String remark = sheet2.getCell(12, i).getContents();
				//String paidtime = sheet2.getCell(13, i).getContents();
				String createtime = sheet2.getCell(14, i).getContents();
				String updatetime = sheet2.getCell(15, i).getContents();
				String status = sheet2.getCell(16, i).getContents();
				String discountrate = sheet2.getCell(17, i).getContents();
				if (checkTool.checkNumbers(totalquantity,totalamount,discount,freight,othercharge,paidtype,paidstatus,status)) {
					Integer tq = Integer.valueOf(totalquantity);
					Double ta = Double.valueOf(totalamount);
					Double d = Double.valueOf(discount);
					Double dr = Double.valueOf(discountrate);
					Double f = Double.valueOf(freight);
					Double o = Double.valueOf(othercharge);
					Integer pt = Integer.valueOf(paidstatus);
					Integer pty = Integer.valueOf(paidtype);
					Integer s = Integer.valueOf(status);
					//create a new salerecord
					SaleRecord saleRecord = new SaleRecord(invoiceno, warehouse, customerid, operatorid, newsalRecordLists, currency, pty, d,dr);
					saleRecord.setTotalquantity(tq);
					saleRecord.setTotalamount(ta);
					saleRecord.setFreight(f);
					saleRecord.setOthercharge(o);
					saleRecord.setPaidstatus(pt);
					saleRecord.setRemark(remark);
					//saleRecord.setPaidtime(paidtime);
					saleRecord.setCreatetime(createtime);
					saleRecord.setUpdatetime(updatetime);
					saleRecord.setStatus(s);
					newSaleRecords.add(saleRecord);//add to list
				}
			}
				List<SaleRecordList> saleRecordLists = this.saleListService.listAll();
				List<SaleRecord> saleRecords = this.saleService.listAll();
				this.saleListService.deleteManyObjects(saleRecordLists);
				this.saleService.deleteManyObjects(saleRecords);
				this.saleListService.addManyObjects(newsalRecordLists);
				this.saleService.addManyObjects(newSaleRecords);
				this.setMessage(SUCCMESSAGE);
				return SUCCESS;
			}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	
	public void findCustomerSaleRecords(){
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter printWriter = response.getWriter();
			if(customer!=null){
				customer =  customer.trim();
				List<SaleRecord> saleRecords = this.saleService.findByCustomer(customer);
				System.out.println(customer);
				System.out.println(saleRecords.size());
				if(saleRecords!=null && saleRecords.size()>0){
					 String json = JsonTool.getInstance().createSalerecord(saleRecords);
					 printWriter.write(json);
					 printWriter.flush();
					 printWriter.close();
				}
				printWriter.write("error01");
				printWriter.flush();
				printWriter.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 销售明细
	 * @return
	 * @throws Exception
	 */
	public String findSaleRecordListByDate() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"sale"+FILESEPARATOR+"findRecordByDate.action");
		warehouses = this.warehouseService.findByKeyword("");
		if(begindate==null || begindate.trim().equals("")){
			begindate = DateTool.getInstance().DateToPattern1(new Date());
		}
		if(enddate==null || enddate.trim().equals("")){
			enddate = DateTool.getInstance().DateToPattern1(new Date());
		}
		warehouse = warehouse==null?"all":warehouse;
		if(warehouse.trim().equals("all")){
			recordLists = this.saleListService.listByWarehouseAndDate(begindate+" 00:00:00", enddate+" 23:59:59");
			int allrows = recordLists.size();
			if(page==null){
				page = new Page(1, 200, allrows);
			}else{
				page.setMaxResult(200);
				page.setAllRows(allrows);
			}
			recordLists = null;
			recordLists = this.saleListService.listByWarehouseAndDate(begindate+" 00:00:00", enddate+" 23:59:59", page.getFirstResult(), page.getMaxResult());
		}else{
			recordLists = this.saleListService.listByWarehouseAndDate(warehouse, begindate+" 00:00:00", enddate+" 23:59:59");
			int allrows = recordLists.size();
			if(page==null){
				page = new Page(1, 200, allrows);
			}else{
				page.setMaxResult(200);
				page.setAllRows(allrows);
			}
			recordLists = null;
			recordLists = this.saleListService.listByWarehouseAndDate(warehouse, begindate+" 00:00:00", enddate+" 23:59:59", page.getFirstResult(), page.getMaxResult());
		}
		return SUCCESS;
	}
	
	
	public SaleRecord getSaleRecord() {
		return saleRecord;
	}
	public void setSaleRecord(SaleRecord saleRecord) {
		this.saleRecord = saleRecord;
	}
	public List<SaleRecord> getSaleRecords() {
		return saleRecords;
	}
	public void setSaleRecords(List<SaleRecord> saleRecords) {
		this.saleRecords = saleRecords;
	}
	public SaleRecordList getRecordList() {
		return recordList;
	}
	public void setRecordList(SaleRecordList recordList) {
		this.recordList = recordList;
	}
	public List<SaleRecordList> getRecordLists() {
		return recordLists;
	}
	public void setRecordLists(List<SaleRecordList> recordLists) {
		this.recordLists = recordLists;
	}
	public List<Object[]> getObjects() {
		return objects;
	}
	public void setObjects(List<Object[]> objects) {
		this.objects = objects;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public String getOutstr() {
		return outstr;
	}
	public void setOutstr(String outstr) {
		this.outstr = outstr;
	}
	public String[] getImeis() {
		return imeis;
	}
	public void setImeis(String[] imeis) {
		this.imeis = imeis;
	}
	public String[] getBarcodes() {
		return barcodes;
	}
	public void setBarcodes(String[] barcodes) {
		this.barcodes = barcodes;
	}
	public String[] getDescs() {
		return descs;
	}
	public void setDescs(String[] descs) {
		this.descs = descs;
	}
	public Integer getPaidtype() {
		return paidtype;
	}
	public void setPaidtype(Integer paidtype) {
		this.paidtype = paidtype;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getBegindate() {
		return begindate;
	}
	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public Map<String, List<SaleRecordList>> getOutputList() {
		return outputList;
	}
	public void setOutputList(Map<String, List<SaleRecordList>> outputList) {
		this.outputList = outputList;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer[] getLines() {
		return lines;
	}
	public void setLines(Integer[] lines) {
		this.lines = lines;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Integer[] getQuans() {
		return quans;
	}
	public void setQuans(Integer[] quans) {
		this.quans = quans;
	}
	public Double[] getPrices() {
		return prices;
	}
	public void setPrices(Double[] prices) {
		this.prices = prices;
	}
	public Double[] getAmounts() {
		return amounts;
	}
	public void setAmounts(Double[] amounts) {
		this.amounts = amounts;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<Warehouse> getWarehouses() {
		return warehouses;
	}
	public void setWarehouses(List<Warehouse> warehouses) {
		this.warehouses = warehouses;
	}
	public Warehouse getWarehouse2() {
		return warehouse2;
	}
	public void setWarehouse2(Warehouse warehouse2) {
		this.warehouse2 = warehouse2;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	@Override
	public String getKeyword() {
		return keyword;
	}
	@Override
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public Double getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}
	public Integer getTotalquantity() {
		return totalquantity;
	}
	public void setTotalquantity(Integer totalquantity) {
		this.totalquantity = totalquantity;
	}
	public List<Object[]> getTypestatement() {
		return typestatement;
	}
	public void setTypestatement(List<Object[]> typestatement) {
		this.typestatement = typestatement;
	}
	
	
}
