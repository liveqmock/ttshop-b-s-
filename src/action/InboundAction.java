package action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import pojo.Innote;
import pojo.InnoteList;
import pojo.Page;
import pojo.Product;
import pojo.ProductInfo;
import pojo.Pstock;
import pojo.Ptype;
import pojo.Supplier;
import pojo.User;
import pojo.Warehouse;
import tools.CheckTool;
import tools.DateTool;
import tools.JsonTool;
import tools.SortTool;

@SuppressWarnings("serial")
public class InboundAction extends BaseAction {
	private List<Warehouse> warehouses;
	private Innote innote;
	private List<Innote> innotes;
	private List<InnoteList> innoteLists;
	private Pstock pstock;
	private List<Pstock> pstocks;
	private List<Supplier> suppliers;
	private List<Object[]> objects;
	private List<Object[]> innotelistdetial;
	private InputStream inputStream;
	private String[] ids;
	private String[] barcodes;
	private String[] quantitys;
	private String[] imeis;
	private String[] prices;
	private String notenumber;
	private String warehouse;
	private Integer warehouseid;
	private String quantity;
	private String barcode;
	private String price;
	private String supplierid;
	private String resJson;
	private String begindate;
	private String enddate;
	private List<Ptype> ptypes;
	private List<Object[]> innotestatement;
	private Page page;
	/**
	 * 保存临时入货表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void tempInbound() throws Exception{
		String barcode = request.getParameter("barcode");
		String desc = request.getParameter("desc");
		String quantity = request.getParameter("quantity");
		String price = request.getParameter("price");
		List<InnoteList> templist = new ArrayList<InnoteList>();
		if(request.getSession().getAttribute("tempinbound")!=null){
			templist = (List<InnoteList>) request.getSession().getAttribute("tempinbound");
		}
		if(CheckTool.getInstance().checkNull(barcode)&&CheckTool.getInstance().checkNumbers(quantity,price)) {
			Integer q = Integer.valueOf(quantity);
			Double p = Double.valueOf(price);
			InnoteList innoteList = new InnoteList("temp", barcode, q, p, desc);
			templist.add(innoteList);
		}
		request.getSession().setAttribute("tempinbound", templist);
	}
	/**
	 * 保存临时入货表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void tempInboundaddquanity() throws Exception{
		String barcode = request.getParameter("barcode");
		//String desc = request.getParameter("desc");
		String quantity = request.getParameter("quantity");
		String price = request.getParameter("price");
		List<InnoteList> templist = new ArrayList<InnoteList>();
		if(request.getSession().getAttribute("tempinbound")!=null){
			templist = (List<InnoteList>) request.getSession().getAttribute("tempinbound");
		}
		if(CheckTool.getInstance().checkNull(barcode)&&CheckTool.getInstance().checkNumbers(quantity,price)) {
			Integer q = Integer.valueOf(quantity);
			Double p = Double.valueOf(price);
			for (int i = 0; i < templist.size(); i++) {
				InnoteList innoteList = templist.get(i);
				if (innoteList.getBarcode().equalsIgnoreCase(barcode)) {
					innoteList.setQuantity(innoteList.getQuantity()+q);
					innoteList.setPrice(p);
				}
			}
		}
		request.getSession().setAttribute("tempinbound", templist);
	}
	/**
	 * 删除入货表一项
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void removetempInbound() throws Exception{
		String barcode = request.getParameter("barcode");
		//String desc = request.getParameter("desc");
		String quantity = request.getParameter("quantity");
		String price = request.getParameter("price");
		List<InnoteList> templist = new ArrayList<InnoteList>();
		if(request.getSession().getAttribute("tempinbound")!=null){
			templist = (List<InnoteList>) request.getSession().getAttribute("tempinbound");
		}
		if(CheckTool.getInstance().checkNull(barcode)&&CheckTool.getInstance().checkNumbers(quantity,price)) {
			for (int i = 0; i < templist.size(); i++) {
				InnoteList innoteList = templist.get(i);
				if (innoteList.getBarcode().equalsIgnoreCase(barcode)) {
					templist.remove(i);
				}
			}
		}
		request.getSession().setAttribute("tempinbound", templist);
	}
	
	
	/**
	 * TO INBOUND PAGE
	 * @return
	 * @throws Exception
	 */
	public String toinboundpage() throws Exception{
		String itype = request.getParameter("itype");
		if(msg!=null && CheckTool.getInstance().checkNull(msg)){
			this.setMessage(URLDecoder.decode(msg, "utf-8"));
		}
		warehouses = this.warehouseService.findByKeyword("");
		suppliers = this.supplierService.findByKeyword("");
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"warehousefunction.jsp");
		if(itype.equalsIgnoreCase("1")){
			List<ProductInfo> infos = this.productinfoService.findByBarcodeOrDesc("",1);
			List<String[]> list1 = new ArrayList<String[]>();
			for (ProductInfo productInfo : infos) {
				String[] args = {productInfo.getBarcode(),productInfo.getBarcode()+"|"+productInfo.getPdesc(),productInfo.getPdesc()};
				list1.add(args);
			}
			resJson= "var infos = "+ JsonTool.getInstance().formList(list1,"value","label","desc")+";";
			return "TYPE1";
		}
		if (itype.equalsIgnoreCase("2")) {
			List<ProductInfo> infos = this.productinfoService.findByBarcodeOrDesc("",2);
			List<String[]> list1 = new ArrayList<String[]>();
			for (ProductInfo productInfo : infos) {
				String[] args = {productInfo.getBarcode(),productInfo.getBarcode()+"|"+productInfo.getPdesc(),productInfo.getPdesc()};
				list1.add(args);
			}
			resJson= "var infos = "+ JsonTool.getInstance().formList(list1,"value","label","desc")+";";
			return "TYPE2";
		}
		if (itype.equalsIgnoreCase("3")) {
			List<ProductInfo> infos = this.productinfoService.findByKeyword("");
			List<String[]> list1 = new ArrayList<String[]>();
			for (ProductInfo productInfo : infos) {
				String[] args = {productInfo.getBarcode(),productInfo.getBarcode()+"|"+productInfo.getPdesc(),productInfo.getPdesc()};
				list1.add(args);
			}
			resJson= "var infos = "+ JsonTool.getInstance().formList(list1,"value","label","desc")+";";
			return "TYPE3";
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	/**
	 * 产品入库 - 没有 imei 产品
	 * @return
	 * @throws RuntimeException
	 */
	public String type1in() throws RuntimeException{
		User user = (User) request.getSession().getAttribute("user");
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"inbound"+FILESEPARATOR+"toinboundpage.action?itype=1");
		if(!this.checkLogin(user)){
			this.setMessage("请重新登录！");
			return "LOGIN";
		}
		CheckTool checkTool = CheckTool.getInstance();
		boolean flag = false;
		if(!checkTool.checkNull(supplierid)){
			this.setMessage("请选择一个供应商,如果选项没有请创建!");
			return INPUT;
		}
		Warehouse wh = this.warehouseService.get(Warehouse.class, warehouseid);
		if(wh==null){
			this.setMessage("请选择一个仓库,如果选项没有请创建！");
			return INPUT;
		}
		if(barcodes!=null && barcodes.length>0){
			warehouse = wh.getWname();
			List<InnoteList> innoteLists = new ArrayList<InnoteList>();
			int size = barcodes.length;
			for (int i = 0; i < size; i++) {
				String barcode = barcodes[i];
				if(checkTool.checkNull(barcode)){
					ProductInfo info = this.productinfoService.findByBaecode(barcode);
					if(info==null){
						this.setMessage("找不到,条码为"+barcode+"的产品,请先创建该产品!");
						return INPUT;
					}
					Integer quan = 0;
					Double price = 0.0;
					String temqua = quantitys[i];
					String temppri = prices[i];
					if(checkTool.checkNumber(temqua)){
						quan = Integer.valueOf(temqua);
					}
					if(checkTool.checkNumber(temppri)){
						price = Double.valueOf(temppri);
					}
					InnoteList innoteList = new InnoteList("TEMP", barcode, quan, price, "");
					innoteLists.add(innoteList);
					flag = true;
				}else{
					flag = false;
				}
			}
			if(flag){
				String notenumber = this.myidService.createIDNumber("IN-");
				for (InnoteList innoteList : innoteLists) {
					innoteList.setNotenumber(notenumber);
				}
				Innote innote = new Innote(notenumber, warehouse, "MULTI", user.getUserid(),supplierid,innoteLists);
				this.innoteListService.addManyObjects(innoteLists);
				this.innoteService.add(innote);
				this.pstockService.updateInbound(innoteLists, warehouse);
				this.setMessage(SUCCMESSAGE+"入货单编号:"+notenumber);
				request.getSession().removeAttribute("tempinbound");
				return SUCCESS;
			}
		}
		this.setMessage("没有添加任何产品!");
		return INPUT;
	}
	
	/**
	 * 单一入仓
	 * @return
	 * @throws RuntimeException
	 */
	public String type2in() throws RuntimeException{
		User user = (User) request.getSession().getAttribute("user");
		if(!this.checkLogin(user)){
			this.setMessage("请重新登录！");
			return "LOGIN";
		}
		String warehouse = request.getParameter("warehouse");
		String quantity = request.getParameter("quantity");
		String barcode = request.getParameter("barcode");
		String price = request.getParameter("price");
		String supplierid = request.getParameter("supplierid");
		List<String> realimeis = SortTool.getInstance().sortString(imeis);
		if(CheckTool.getInstance().checkNull(barcode,warehouse)&&CheckTool.getInstance().checkNumber(quantity)){
			if(realimeis.size()!=Integer.valueOf(quantity)){
				this.setMessage("imei与数量不一致");
				return INPUT;
			}
			ProductInfo info = productinfoService.findByBaecode(barcode);
			if(info==null){
				this.setMessage("没有找到此产品资料");
				return INPUT;
			}
			Integer q = Integer.valueOf(quantity);
			Double p = 0.0;
			if(CheckTool.getInstance().checkNumber(price)){
				p = Double.valueOf(price);
			}
			List<Product> products = new ArrayList<Product>();
			for (int i = 0; i < realimeis.size(); i++) {
				Product product = new Product(barcode, warehouse, realimeis.get(i), 1);
				products.add(product);
			}
			String notenumber = this.myidService.createIDNumber("IN-");
			List<InnoteList> inLists = new ArrayList<InnoteList>();
			InnoteList innoteList = new InnoteList(notenumber, barcode, q, p, SortTool.getInstance().listToString(realimeis));
			inLists.add(innoteList);
			Innote in = new Innote(notenumber, warehouse, barcode, user.getUserid(), supplierid, inLists);
			try {
				this.pstockService.updateInbound(inLists, warehouse);  //更新库存数量
				this.productService.inboundProduct(products);		//记录入库手机
				this.innoteListService.addManyObjects(inLists);    
				this.innoteService.add(in);
				this.setMessage(SUCCMESSAGE);
				return SUCCESS;
			} catch (Exception e) {
				this.setMessage(e.getMessage());
				//this.myidService.removeIdNumber("IN-", notenumber);
				return ERROR;
			}
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	/**
	 * 
	 * @return
	 * @throws RuntimeException
	 */
	public String totype2import() throws RuntimeException{
		warehouses = this.warehouseService.findByKeyword("");
		suppliers = this.supplierService.findByKeyword("");
		List<ProductInfo> infos = this.productinfoService.findByBarcodeOrDesc("",2);
		List<String[]> list1 = new ArrayList<String[]>();
		for (ProductInfo productInfo : infos) {
			String[] args = {productInfo.getBarcode(),productInfo.getBarcode()+"|"+productInfo.getPdesc()};
			list1.add(args);
		}
		resJson= "var infos = "+ JsonTool.getInstance().formList(list1,"value","label")+";";
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws RuntimeException
	 * @throws BiffException
	 * @throws IOException
	 */
	public String type2import() throws RuntimeException, BiffException, IOException{
		User user = (User) request.getSession().getAttribute("user");
		if(!this.checkLogin(user)){
			this.setMessage("请重新登录！");
			return "LOGIN";
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	/**
	 * 门市入仓
	 * @return
	 * @throws Exception
	 */
	public String storeInbound() throws Exception{
		User user = (User) request.getSession().getAttribute("user");
		if(user==null){
			this.setMessage("请重新登录!");
			return "LOGIN";
		}
		List<InnoteList> tempList = new ArrayList<InnoteList>();
		for (int i = 0; i < imeis.length; i++) {
			String b = barcodes[i];
			String imei = imeis[i];
			if(!imei.trim().equals("") && b.equals("")){
				this.setMessage(imei+"没有输入产品编号！");
				return INPUT;
			}
			ProductInfo info = this.productinfoService.findByBaecode(b);
			if(info!=null){
				InnoteList temp = new InnoteList("temp", b, 1, 0.0, imei);
				tempList.add(temp);
			}
		}
		if(tempList.size()>0){
		List<String> realbarcode = SortTool.getInstance().sortString(barcodes);
		List<InnoteList> innotelist = new ArrayList<InnoteList>();
		List<Product> products = new ArrayList<Product>();  //若果效率较低，将考虑取消这项
		String notenumber = this.myidService.createIDNumber("IN-");
		for (String rb : realbarcode) {
			List<String> ims = new ArrayList<String>();
			for (InnoteList il : tempList) {
				if(il.getBarcode().equals(rb)){
					Product product = new Product(rb, warehouse, il.getImei(), 1);
					ims.add(il.getImei());
					products.add(product);
				}
			}
			InnoteList inlist = new InnoteList(notenumber, rb, ims.size(), 0.0, SortTool.getInstance().listToString(ims));
			innotelist.add(inlist);
		}
		innote = new Innote(notenumber, warehouse, "MUTIL", user.getUserid(), supplierid, innotelist);
		try {
			this.innoteListService.addManyObjects(innotelist);
			this.productService.inboundProduct(products);
			this.innoteService.add(innote);
			this.pstockService.updateInbound(innotelist, warehouse);
			this.setMessage(SUCCMESSAGE + "入库单号:"+notenumber);
			return SUCCESS;
		} catch (Exception e) {
			this.setMessage(e.getMessage());
			return INPUT;
		}
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	
	/**
	 * 入货单列表
	 * @return
	 * @throws Exception
	 */
	public String innoteRecords() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"warehousefunction.jsp");
		if(begindate==null){
			begindate = DateTool.getInstance().DateToPattern1(new Date());
		}
		if(enddate==null){
			enddate = DateTool.getInstance().DateToPattern1(new Date());
		}
		warehouses = this.warehouseService.findByKeyword("");
		if(warehouse==null){
			warehouse = "all"; //页面 listinnote 对应
		}
		if(warehouse.equals("all")){
			int allrows = this.innoteService.findallrow(begindate+" 00:00:00",enddate+" 23:59:59");
			if(page==null){
				page = new Page(1, 200, allrows);
			}else{
				page.setMaxResult(200);
				page.setAllRows(allrows);
			}
			innotes = this.innoteService.listByDate(begindate+" 00:00:00",enddate+" 23:59:59",page.getFirstResult(),page.getMaxResult());
			innotestatement = this.innoteService.sumInnote(begindate+" 00:00:00", enddate+" 23:59:59");
		}else{
			int allrows = this.innoteService.findallrow(warehouse,begindate+" 00:00:00",enddate+" 23:59:59");
			if(page==null){
				page = new Page(1, 200, allrows);
			}else{
				page.setMaxResult(200);
				page.setAllRows(allrows);
			}
			innotes = this.innoteService.listByDate(warehouse,begindate+" 00:00:00",enddate+" 23:59:59",page.getFirstResult(),page.getMaxResult());
			//innotestatement = this.innoteService.sumInnote(begindate+" 00:00:00", enddate+" 23:59:59");
			Double totalquantity = 0.0;
			Double totalamount = 0.0;
			for (int i = 0; i < innotes.size(); i++) {
				Innote ino = innotes.get(i);
				totalquantity+=ino.getQuantity();
				totalamount+=ino.getAmount();
			}
			innotestatement = new ArrayList<Object[]>();
			Object[] objects = new Object[3];
			objects[0] = totalquantity;
			objects[1] = totalamount;
			objects[2] = warehouse;
			innotestatement.add(objects);
		}
		return SUCCESS;
	}
	
	/**
	 * 入库单明细 by notenumber
	 * @return
	 * @throws Exception
	 */
	public String innotelistdetail() throws Exception{
		String notenumber = request.getParameter("notenumber");
		innote = this.innoteService.findByNotenumber(notenumber);
		if(CheckTool.getInstance().checkNull(notenumber)&&innote!=null){
			objects  = this.innoteListService.findInotelistByNotenumber(notenumber);
			return SUCCESS;
		}
		this.setMessage("没有此入库单或已作废");
		return INPUT;
	}
	
	/**
	 * 生成一个明细表
	 * @return
	 * @throws Exception
	 */
	public String createInnoteList() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"inbound"+FILESEPARATOR+"innoteRecords.action");
		if(begindate==null || enddate==null){
			begindate = DateTool.getInstance().DateToPattern1(new Date());
			enddate = DateTool.getInstance().DateToPattern1(new Date());
		}
		warehouses = this.warehouseService.findByKeyword("");
		String begindate1 = begindate+" 00:00:00";
		String enddate1 = enddate+" 59:59:59";
		if(warehouse==null || warehouse.trim().equals("")){
			warehouse = "all";
		}
		if(warehouse.trim().equals("all")){
			innotelistdetial = this.innoteListService.listInotelistByDate(begindate1, enddate1);
			int allrows = innotelistdetial.size();
			if(page==null){
				page = new Page(1, 200, allrows);
			}else{
				page.setMaxResult(200);
				page.setAllRows(allrows);
			}
			innotelistdetial = this.innoteListService.listInotelistByDate(begindate1, enddate1, page.getFirstResult(), page.getMaxResult());
		}else{
			innotelistdetial = this.innoteListService.listInotelistByDate(warehouse,begindate1, enddate1);
			int allrows = innotelistdetial.size();
			if(page==null){
				page = new Page(1, 200, allrows);
			}else{
				page.setMaxResult(200);
				page.setAllRows(allrows);
			}
			innotelistdetial = this.innoteListService.listInotelistByDate(warehouse,begindate1, enddate1, page.getFirstResult(), page.getMaxResult());
		}
		//objects = this.innoteListService.sumTotalIn(begindate1, enddate1);
		return SUCCESS;
	}
	
	/**
	 * 生成概览表
	 * @return
	 * @throws Exception
	 */
	public String createStatisticsTable() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"inbound"+FILESEPARATOR+"innoteRecords.action");
		if(begindate==null || enddate==null){
			begindate = DateTool.getInstance().DateToPattern1(new Date());
			enddate = DateTool.getInstance().DateToPattern1(new Date());
		}
		warehouses = this.warehouseService.findByKeyword("");
		String begindate1 = begindate+" 00:00:00";
		String enddate1 = enddate+" 59:59:59";
		if(warehouse==null || warehouse.trim().equals("")){
			warehouse = "all";
		}
		if(warehouse.trim().equals("all")){
			objects = this.innoteListService.sumTotalIn(begindate1, enddate1);
		}else{
			objects = this.innoteListService.sumTotalIn(warehouse,begindate1, enddate1);
		}
		return SUCCESS;
	}
	
/*	*//**
	 * TO更新入货单
	 * @return
	 * @throws Exception
	 *//*
	public String toUpdateInbound() throws Exception{
		String notenumber = request.getParameter("notenumber");
		innote = this.innoteService.findByNotenumber(notenumber);
		objects = this.innoteListService.findInotelistByNotenumber(notenumber);
		return SUCCESS;
	}*/
	
	/**
	 * 更新入货单 ajax
	 * @return
	 * @throws Exception
	 */
	public void updateInboundAjax() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if(ids!=null && ids.length>0 && notenumber!=null && !notenumber.trim().equals("")){
			innote = this.innoteService.findByNotenumber(notenumber);
			String warehouse = innote.getWarehouse();
			innoteLists = new ArrayList<InnoteList>();
			for (int i = 0; i < ids.length; i++) {
				int id = Integer.valueOf(ids[i]);
				InnoteList list = this.innoteListService.get(InnoteList.class, id);
				if(CheckTool.getInstance().checkNumber(prices[i])){
					double price = Double.valueOf(prices[i]);
					list.setPrice(price);
				}else{
					printWriter.write("error:系统出错，出错原因，价格格式不正确！");
					printWriter.flush();
					printWriter.close();
					return;
				}
				if(CheckTool.getInstance().checkNumber(quantitys[i])){
					Integer quantity = Integer.valueOf(quantitys[i]);
					Pstock pstock = this.pstockService.findbyBarcodeAndWarehouse(list.getBarcode(), warehouse);
					if(pstock==null){
						printWriter.write("error:系统出错，出错原因，找不到该产品库存！");
						printWriter.flush();
						printWriter.close();
						return;
					}
					Integer newquantity = pstock.getQuantity() + (quantity-list.getQuantity()); //new = 原有库存 +差额  
					this.pstockService.updateChangeInbound(pstock, newquantity);
					list.setQuantity(quantity); //修改innotelist里面的quantity，一定要计算了新库存才能set
					list.updateAmount();
				}else{
					printWriter.write("error:系统出错，出错原因，请输入正确数量！");
					printWriter.flush();
					printWriter.close();
					return;
				}
				innoteLists.add(list);
			}
			innote.updateInnoteList(innoteLists);
			this.innoteService.update(innote);
			this.innoteListService.updateManyObjects(innoteLists);
			printWriter.write("success:操作成功！");
			printWriter.flush();
			printWriter.close();
			return;
		}
	}
	
	/**
	 * 作废入货单
	 * @return
	 * @throws Exception
	 */
	public String cancelInbound() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"inbound"+FILESEPARATOR+"innoteRecords.action");
		if (CheckTool.getInstance().checkNull(notenumber)) {
			innote = this.innoteService.findByNotenumber(notenumber);
			innoteLists = this.innoteListService.findByNotenumber(notenumber);
			innote.setStatus(0); //0 作废
			String warehouse = innote.getWarehouse();
			List<Product> products = new ArrayList<Product>();
			for (InnoteList innoteList : innoteLists) {
				innoteList.setStatus(0);
				Integer quantity = -innoteList.getQuantity();
				innoteList.setQuantity(quantity);
				innoteList.setUpdatetime(DateTool.getInstance().DateToPattern2(new Date()));
				if(innoteList.getImei()!=null&&!innoteList.getImei().trim().equals("")){
					String imeis = innoteList.getImei();
					List<String> ims = SortTool.getInstance().stringToList(imeis);
					//System.out.println(ims);
					for (String string : ims) {
						Product product = this.productService.findByImeiAndWarehouse(string, warehouse);
						if(product!=null){
							products.add(product);
							System.out.println(product);
							continue;
						}
						this.setMessage(string+"已被使用，不能作废此单，请做调货！");
						return ERROR;
					}
				}
				
			}
			this.pstockService.updateInbound(innoteLists, warehouse);
			this.productService.cancelInboundProduct(products);
			this.innoteListService.updateManyObjects(innoteLists);
			this.innoteService.update(innote);
			return SUCCESS;
		}
		this.setMessage("找不到该单号,请联系管理员");
		return INPUT;
	}
	
	/**
	 * 导出入货明细到 excel 表
	 * @return
	 * @throws RuntimeException
	 * @throws IOException
	 * @throws WriteException
	 */
	public String innotelistdetailexcel() throws RuntimeException, IOException, WriteException{
		String notenumber = request.getParameter("notenumber");
		innote = this.innoteService.findByNotenumber(notenumber);
		if(notenumber!=null && notenumber.trim()!="" && innote!=null){
			innoteLists  = this.innoteListService.findByNotenumber(notenumber);
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			os.reset();
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet("入库单", 0);
			
			WritableCellFormat format = new WritableCellFormat();
			format.setAlignment(jxl.format.Alignment.CENTRE);
			
			Label label00 = new Label(0, 0, "入库单号:"+innote.getNotenumber());
			Label label01 = new Label(1, 0, "操作者: "+innote.getOperatorid());
			Label label02 = new Label(2, 0, "仓 库: "+innote.getWarehouse());
			Label label03 = new Label(3, 0, "供应商:"+innote.getSupplierid());
			Label label10 = new Label(0, 1, "类 型："+innote.getInboundType());
			Label label11 = new Label(1, 1, "数 量:"+innote.getQuantity());
			Label label12 = new Label(2, 1, "总 额:"+innote.getAmount());
			Label label13 = new Label(3, 1, "入库时间: "+innote.getUpdatetime());
			sheet.addCell(label00);
			sheet.addCell(label01);
			sheet.addCell(label02);
			sheet.addCell(label03);
			sheet.addCell(label10);
			sheet.addCell(label11);
			sheet.addCell(label12);
			sheet.addCell(label13);
			
			Label label30 = new Label(0, 3, "产品编码");
			Label label31 = new Label(1, 3, "IMEI");
			sheet.addCell(label30);
			sheet.addCell(label31);
			
			for (int i = 0; i < innoteLists.size(); i++) {
				InnoteList innoteList = innoteLists.get(i);
				Label label1 = new Label(0, i+4, innoteList.getBarcode());
				Label label2 = new Label(1, i+4, innoteList.getImei());
				sheet.addCell(label1);
				sheet.addCell(label2);
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
			
			this.inputStream = new ByteArrayInputStream(os.toByteArray());
			os.close();
			return SUCCESS;
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	/**
	 * 入货明细表
	 * @return
	 * @throws Exception
	 */
	public String listinnotelist() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"inbound"+FILESEPARATOR+"innoteRecords.action");
		if(begindate==null || enddate==null){
			begindate = DateTool.getInstance().DateToPattern1(new Date());
			enddate = DateTool.getInstance().DateToPattern1(new Date());
		}
		String begindate1 = begindate+" 00:00:00";
		String enddate1 = enddate+" 59:59:59";
		warehouses = this.warehouseService.findByKeyword("");
		if(warehouse==null || warehouse.trim().equals("") ){
			warehouse = "all";
		}
		if(warehouse.trim().equals("all")){
			innotelistdetial = this.innoteListService.listInotelistByDate(begindate1, enddate1);
			int allrows = innotelistdetial.size();
			if(page==null){
				page = new Page(1, 200, allrows);
			}else{
				page.setMaxResult(200);
				page.setAllRows(allrows);
			}
			innotelistdetial = this.innoteListService.listInotelistByDate(begindate1, enddate1, page.getFirstResult(), page.getMaxResult());
		}else{
			innotelistdetial = this.innoteListService.listInotelistByDate(warehouse, begindate1, enddate1);
			int allrows = innotelistdetial.size();
			if(page==null){
				page = new Page(1, 200, allrows);
			}else{
				page.setMaxResult(200);
				page.setAllRows(allrows);
			}
			innotelistdetial = this.innoteListService.listInotelistByDate(enddate1, begindate1, enddate1, page.getFirstResult(), page.getMaxResult());
		}
		objects = this.innoteListService.sumTotalIn(begindate1, enddate1);
		return SUCCESS;
	}
	
	
	
	public List<Warehouse> getWarehouses() {
		return warehouses;
	}
	public void setWarehouses(List<Warehouse> warehouses) {
		this.warehouses = warehouses;
	}
	public Innote getInnote() {
		return innote;
	}
	public void setInnote(Innote innote) {
		this.innote = innote;
	}
	public List<Innote> getInnotes() {
		return innotes;
	}
	public void setInnotes(List<Innote> innotes) {
		this.innotes = innotes;
	}
	public Pstock getPstock() {
		return pstock;
	}
	public void setPstock(Pstock pstock) {
		this.pstock = pstock;
	}
	public List<Pstock> getPstocks() {
		return pstocks;
	}
	public void setPstocks(List<Pstock> pstocks) {
		this.pstocks = pstocks;
	}
	public String[] getImeis() {
		return imeis;
	}
	public void setImeis(String[] imeis) {
		this.imeis = imeis;
	}
	public List<Supplier> getSuppliers() {
		return suppliers;
	}
	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}
	public List<Object[]> getObjects() {
		return objects;
	}
	public void setObjects(List<Object[]> objects) {
		this.objects = objects;
	}
	public List<InnoteList> getInnoteLists() {
		return innoteLists;
	}
	public void setInnoteLists(List<InnoteList> innoteLists) {
		this.innoteLists = innoteLists;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String[] getBarcodes() {
		return barcodes;
	}
	public void setBarcodes(String[] barcodes) {
		this.barcodes = barcodes;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public String getResJson() {
		return resJson;
	}

	public void setResJson(String resJson) {
		this.resJson = resJson;
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
	public String[] getQuantitys() {
		return quantitys;
	}
	public void setQuantitys(String[] quantitys) {
		this.quantitys = quantitys;
	}
	public String[] getPrices() {
		return prices;
	}
	public void setPrices(String[] prices) {
		this.prices = prices;
	}
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	public String getNotenumber() {
		return notenumber;
	}
	public void setNotenumber(String notenumber) {
		this.notenumber = notenumber;
	}
	public List<Ptype> getPtypes() {
		return ptypes;
	}
	public void setPtypes(List<Ptype> ptypes) {
		this.ptypes = ptypes;
	}
	public List<Object[]> getInnotelistdetial() {
		return innotelistdetial;
	}
	public void setInnotelistdetial(List<Object[]> innotelistdetial) {
		this.innotelistdetial = innotelistdetial;
	}
	public List<Object[]> getInnotestatement() {
		return innotestatement;
	}
	public void setInnotestatement(List<Object[]> innotestatement) {
		this.innotestatement = innotestatement;
	}
	public Integer getWarehouseid() {
		return warehouseid;
	}
	public void setWarehouseid(Integer warehouseid) {
		this.warehouseid = warehouseid;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	
	
	
	
	
	
	
	
}
