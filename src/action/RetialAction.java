package action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pojo.Customer;
import pojo.Product;
import pojo.ProductInfo;
import pojo.Pstock;
import pojo.SaleRecord;
import pojo.SaleRecordList;
import pojo.User;
import pojo.Warehouse;
import tools.CheckTool;
import tools.DateTool;
import tools.JsonTool;
import tools.SortTool;

@SuppressWarnings("serial")
public class RetialAction extends BaseAction{
	private String date;
	private String[] imeis;
	private String[] barcodes;
	private String[] pdescs;
	private Integer[] quantitys;
	private double[] prices;
	private double amount;
	private Integer paidtype; //0,1,2,3,4,5
	private String currency; //hkd mop rmb
	private String customer; 
	private String jsondata;
	private String warehouse;
	private String remark;
	private double discount;
	private double pay;
	private double change;
	private List<Warehouse> warehouses;
	private SaleRecord saleRecord;
	private SaleRecordList recordList;
	private List<SaleRecordList> recordLists;
	private Warehouse warehouse2;
	private String customerid;
	private Double discountrate;
	private String token;
	/**
	 * 通过 imei 查询产品信息
	 * @throws Exception
	 */
	public void findProduct() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		String imei = request.getParameter("imei");
		String warehouse = request.getParameter("warehouse");
		Warehouse warehouse2 = this.warehouseService.get(Warehouse.class, Integer.valueOf(warehouse));
		if (warehouse2==null) {
			printWriter.write("error:"+"please select warehouse!");
			printWriter.flush();
			printWriter.close();
			return;
		}
		Product product = this.productService.findByImeiAndWarehouse(imei,warehouse2.getWname());
		if(product==null){
			printWriter.write("error:"+"WE CAN NOT SEARCH THIS IMEI IN DATABASE!");
			printWriter.flush();
			printWriter.close();
			return;
		}
		ProductInfo productInfo = this.productinfoService.findByBaecode(product.getBarcode());
		Pstock pstock = this.pstockService.findbyBarcodeAndWarehouse(productInfo.getBarcode(), warehouse2.getWname());
		if(pstock==null){
			printWriter.write("error:"+"WE CAN NOT SEARCH THIS PRODUCT IN ANY WAREHOUSES!");
			printWriter.flush();
			printWriter.close();
			return;
		}
		String json = JsonTool.getInstance().getInfoDetail(productInfo, imei, pstock.getQuantity());
		printWriter.write(json.trim());
		printWriter.flush();
		printWriter.close();
		return;
	}
	
	/**
	 * 用 ajax 查询产品库存
	 * @throws Exception
	 */
	public void findStock() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		String barcode = request.getParameter("barcode");
		String warehouse = request.getParameter("warehouse");
		Warehouse warehouse2 = this.warehouseService.get(Warehouse.class, Integer.valueOf(warehouse));
		if (warehouse2==null) {
			printWriter.write("error01:"+" please select warehouse!");
			printWriter.flush();
			printWriter.close();
			return;
		}
		ProductInfo productInfo = this.productinfoService.findByBaecode(barcode);
		if(productInfo==null){
			printWriter.write("error02:"+" no productInfo!");
			printWriter.flush();
			printWriter.close();
			return;
		}
		Pstock pstock = this.pstockService.findbyBarcodeAndWarehouse(barcode, warehouse2.getWname());
		if(pstock==null){
			printWriter.write("error03:"+"no any stock record in warehouse!");
			printWriter.flush();
			printWriter.close();
			return;
		}
		String json = JsonTool.getInstance().getInfoDetail(productInfo, "", pstock.getQuantity());
		printWriter.write(json.trim());
		printWriter.flush();
		printWriter.close();
		return;
	}
	
	
	/**
	 * TO THE POS PAGE,GIVE THE BASE INFOS FOR AUTOCOMIT
	 * @return
	 */
	public String topos() throws Exception{
		date = DateTool.getInstance().DateToPattern1(new Date());
		User user = (User) request.getSession().getAttribute("user");
		if(request.getSession().getAttribute("user")==null || user==null){
			this.setMessage("请登录!");
			return LOGIN;
		}
		warehouses = this.warehouseService.findByKeyword("");
		if(warehouses==null){
			this.setMessage("未设置仓库！");
			return INPUT;
		}
		List<ProductInfo> infos = this.productinfoService.findByKeyword("");
		List<Customer> customers = this.customerService.findByKeyword("");
		if(infos.size()>0){
			jsondata = JsonTool.getInstance().createCustomerJson(customers)+JsonTool.getInstance().createInfoJson(infos);
			return SUCCESS;
		}else{
			this.setMessage("请先添加货品基本资料!");
			return INPUT;
		}
	}
	
	/**
	 * 保存销售单
	 * @return
	 * @throws Exception
	 */
	public String createAdeal() throws Exception{
		User user = (User) request.getSession().getAttribute("user");
		if(user==null){
			this.setMessage("请重新登录!");
			return LOGIN;
		}
		/**
		 * 自设 token 防止刷新重复提交
		 */
		if(request.getSession().getAttribute("stoken")==null){
		}else{
			String stoken = (String) request.getSession().getAttribute("stoken");
			System.out.println(token.equalsIgnoreCase(stoken));
			if(token.equalsIgnoreCase(stoken)){
				return "TOKEN";
			}
		}
		request.getSession().setAttribute("stoken", token);
		this.setReturnurl(request.getContextPath()+"/retial/topos.action");
		CheckTool checkTool = CheckTool.getInstance();
		List<Product> products = new ArrayList<Product>();
		recordLists = new ArrayList<SaleRecordList>();
		Warehouse tempwarehouse = this.warehouseService.get(Warehouse.class, warehouse2.getId());//select选项读取id，根据id查找warehouse
		if(tempwarehouse==null){
			this.setMessage("操作错误，请选择仓库！");
			return ERROR;
		}
		if(checkTool.checkNull(customer)){
			Customer customer1 = this.customerService.findById(customer);
			//先确定是否为已做过挂起操作的单，若是的话，取消该挂起单，生成一张正式销售单
			String handupid = request.getParameter("hanpupid");
			if(handupid!=null && !handupid.trim().equals("")){
				SaleRecord record = this.saleService.findByInvoiceNo(handupid);
				List<SaleRecordList> handLists = this.saleListService.listByInvoiceNo(handupid);
				if(record!=null){
					record.setStatus(0); //注销
				}
				if(handLists!=null && handLists.size()>0){
					for (SaleRecordList saleRecordList : handLists) {
						saleRecordList.setStatus(0);
					}
				}
				this.saleService.update(record);
				this.saleListService.updateManyObjects(handLists);
			}
			//生成销售单
			String invoiceno = this.myidService.createIDNumber("S-");
			for (int i = 0; i < barcodes.length; i++) {
				ProductInfo info = this.productinfoService.findByBaecode(barcodes[i]);
				if(info==null){
					this.setMessage(barcodes[i]+"出错，操作有误，联系系统管理人员！");
					this.myidService.removeIdNumber("S-", invoiceno);
					return ERROR;
				}
				//0-没库存类型 1-有库存没IMEI 2-有库存有IMEI
				//先判断是不是2类型，2类型要处理imei问题
				if(info.getItype().equals(2)){
					if(!imeis[i].trim().equals("")){
						for (int j = 0; j < imeis.length; j++) {
							List<String> ims = SortTool.getInstance().stringToList(imeis[i]);
							for (String string : ims) {
								Product product = this.productService.findByImeiAndWarehouse(string, tempwarehouse.getWname());
								if(product==null){
									this.setMessage("没有此产品 "+imeis[i]);
									this.myidService.removeIdNumber("S-", invoiceno); //有错误操作不能继续执行时，要取消生成的发票号码
									return ERROR;
								}
								products.add(product);
							}
						}
						SaleRecordList saleRecordList = new SaleRecordList(invoiceno, i+1, info.getBarcode(), info.getPdesc(), quantitys[i], prices[i]);
						saleRecordList.setRemark(imeis[i]);
						recordLists.add(saleRecordList);
					}
				}else{
					SaleRecordList saleRecordList = new SaleRecordList(invoiceno, i+1, info.getBarcode(), info.getPdesc(), quantitys[i], prices[i]);
					recordLists.add(saleRecordList);
				}
			}
			saleRecord = new SaleRecord(invoiceno,tempwarehouse.getWname(), customer, user.getUserid(), recordLists, currency, paidtype, discount,discountrate);
			saleRecord.setPaidstatus(1); //0-未处理    1-已付
			if(paidtype.equals(0)){  //0为现金付款
				saleRecord.setPay(pay);
				saleRecord.setCashchange(change);
			}else{
				saleRecord.setPay(pay);
				saleRecord.setCashchange(0.00);
			}
			if(customer1!=null && customer1.getCredits()!=null){
				int creditplus = saleRecord.getTotalamount().intValue();
				customer1.setCredits(customer1.getCredits()+creditplus);
				this.customerService.update(customer1); //更新用户积分
			}else if(customer1!=null && customer1.getCredits()==null){
				int creditplus = saleRecord.getTotalamount().intValue();
				customer1.setCredits(creditplus);
				this.customerService.update(customer1); //更新用户积分
			}
			/**
			if(pay<0){
				saleRecord.setStatus(2); // 2为退货单
			}
			*/
			//date = DateTool.getInstance().DateToPattern2(new Date());
			//saleRecord.setCreatetime(saleRecord.getUpdatetime()); //实例化时已经设置了时间
			saleRecord.setPaidtime(saleRecord.getCreatetime()); //系统默认付快，设置付款时间为开单时间
			saleRecord.setRemark(remark);
			this.saleService.add(saleRecord);
			this.saleListService.addManyObjects(recordLists);
			this.pstockService.updateSale(recordLists, tempwarehouse.getWname());
			this.productService.saleOrTransferProduct(products);
			return SUCCESS;
		}
		this.setMessage("没有填写客户，保存发票操作失败，请联系管理员！");
		return ERROR;
	}
	
	/**
	 * 取消一张挂起单,与 saleaction 里面的 cancel 不一样功能
	 * @return
	 * @throws Exception
	 */
	public String cancelAdeal() throws Exception{
		String handupid = request.getParameter("hanpupid");
		if(handupid!=null && !handupid.trim().equals("")){
			SaleRecord record = this.saleService.findByInvoiceNo(handupid);
			List<SaleRecordList> handLists = this.saleListService.listByInvoiceNo(handupid);
			if(record!=null){
				record.setStatus(0); //注销
			}
			if(handLists!=null && handLists.size()>0){
				for (SaleRecordList saleRecordList : handLists) {
					saleRecordList.setStatus(0);
				}
			}
			this.saleService.update(record);
			this.saleListService.updateManyObjects(handLists);
			return SUCCESS;
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	/**
	 * 调出一张挂起单操作
	 * @return
	 * @throws Exception
	 */
	public String recoverAdeal() throws Exception{
		String id = request.getParameter("id");
		if(id!=null && !id.trim().equals("")){
			saleRecord = this.saleService.findByInvoiceNo(id);
			recordLists = this.saleListService.listByInvoiceNo(id);
			return SUCCESS;
		}
		return INPUT;
	}
	
	/**
	 * 挂起销售单
	 * @throws Exception
	 */
	public void handupAdeal() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		User user = (User) request.getSession().getAttribute("user");
		if(user==null){
			this.setMessage("请重新登录!");
			printWriter.write("error01");
			return;
		}
		CheckTool checkTool = CheckTool.getInstance();
		List<Product> products = new ArrayList<Product>();
		recordLists = new ArrayList<SaleRecordList>();
		Warehouse tempwarehouse = this.warehouseService.get(Warehouse.class, warehouse2.getId());
		if(tempwarehouse==null){
			printWriter.write("error02");
			return;
		}
		String invoiceno = null;
		String handupid = request.getParameter("hanpupid");
		if(handupid!=null && !handupid.trim().equals("")){
			SaleRecord record = this.saleService.findByInvoiceNo(handupid);
			List<SaleRecordList> handLists = this.saleListService.listByInvoiceNo(handupid);
			if(record!=null){
				record.setStatus(0); //注销
			}
			if(handLists!=null && handLists.size()>0){
				for (SaleRecordList saleRecordList : handLists) {
					saleRecordList.setStatus(0);
				}
			}
			invoiceno = record.getInvoiceno();
			this.saleService.delete(record);
			this.saleListService.deleteManyObjects(handLists);
		}else{
			invoiceno = this.myidService.createIDNumber("Handup-");
		}
		if(checkTool.checkNull(customer)){
			for (int i = 0; i < barcodes.length; i++) {
				ProductInfo info = this.productinfoService.findByBaecode(barcodes[i]);
				if(!info.getItype().equals(2)){
					if(!imeis[i].trim().equals("")){
						for (int j = 0; j < imeis.length; j++) {
							List<String> ims = SortTool.getInstance().stringToList(imeis[i]);
							for (String string : ims) {
								Product product = this.productService.findByImeiAndWarehouse(string, tempwarehouse.getWname());
								if(product==null){
									printWriter.write("error03");
									return;
								}
								products.add(product);
							}
						}
					}
				}
				SaleRecordList saleRecordList = new SaleRecordList(invoiceno, i+1, info.getBarcode(), info.getPdesc(), quantitys[i], prices[i]);
				//saleRecordList.setRemark(imeis[i]);
				saleRecordList.setStatus(2);
				recordLists.add(saleRecordList);
			}
			saleRecord = new SaleRecord(invoiceno,tempwarehouse.getWname(), customer, user.getUserid(), recordLists, currency, paidtype, discount,discountrate);
			saleRecord.setPaidstatus(1); //0-未处理
			if(paidtype.equals(0)){
				saleRecord.setPay(pay);
				saleRecord.setCashchange(change);
			}else{
				saleRecord.setPay(saleRecord.getTotalamount());
				saleRecord.setCashchange(0.00);
			}
			date = DateTool.getInstance().DateToPattern2(new Date());
			saleRecord.setCreatetime(date);
			saleRecord.setPaidtime(date);
			saleRecord.setStatus(2); // 添加入挂起
			this.saleListService.addManyObjects(recordLists);
			this.saleService.add(saleRecord);
			printWriter.write("success");
			return;
		}
		this.setMessage("保存发票操作失败，请联系管理员！");
		printWriter.write("error04");
		return;
	}
	
	/**
	 * 查看挂起的单
	 * @throws Exception
	 */
	public void listHandup() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String warehouseid = request.getParameter("warehosueid");
		PrintWriter printWriter = response.getWriter();
		if(warehouseid!=null && !warehouseid.trim().equals("")){
			Integer id = Integer.valueOf(warehouseid);
			Warehouse warehouse1 = null;
			warehouse1 = this.warehouseService.get(Warehouse.class, id);
			if(warehouse1!=null && warehouse1.getWname()!=null){
				List<SaleRecord> saleRecords = this.saleService.findHandUp(warehouse1.getWname());
				if(!saleRecords.isEmpty() && saleRecords.size()>0){
					String json = JsonTool.getInstance().createSalerecord(saleRecords);
					printWriter.write(json);
					printWriter.flush();
					printWriter.close();
				}else{
					printWriter.write("error03"); // 没有salerecords
					printWriter.flush();
					printWriter.close();
				}
			}else{
				printWriter.write("error01"); //没有找到warehouse
				printWriter.flush();
				printWriter.close();
			}
		}else{
			printWriter.write("error02");//没有传入warehouseid
			printWriter.flush();
			printWriter.close();
		}
	}
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public String[] getPdescs() {
		return pdescs;
	}
	public void setPdescs(String[] pdescs) {
		this.pdescs = pdescs;
	}
	public Integer[] getQuantitys() {
		return quantitys;
	}
	public void setQuantitys(Integer[] quantitys) {
		this.quantitys = quantitys;
	}
	public double[] getPrices() {
		return prices;
	}
	public void setPrices(double[] prices) {
		this.prices = prices;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
	public String getJsondata() {
		return jsondata;
	}
	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}
	public List<Warehouse> getWarehouses() {
		return warehouses;
	}
	public void setWarehouses(List<Warehouse> warehouses) {
		this.warehouses = warehouses;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public SaleRecord getSaleRecord() {
		return saleRecord;
	}
	public void setSaleRecord(SaleRecord saleRecord) {
		this.saleRecord = saleRecord;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Warehouse getWarehouse2() {
		return warehouse2;
	}

	public void setWarehouse2(Warehouse warehouse2) {
		this.warehouse2 = warehouse2;
	}
	public double getPay() {
		return pay;
	}
	public void setPay(double pay) {
		this.pay = pay;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		this.change = change;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	public Double getDiscountrate() {
		return discountrate;
	}
	public void setDiscountrate(Double discountrate) {
		this.discountrate = discountrate;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
	
	
	
	
	
}
