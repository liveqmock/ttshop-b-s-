package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pojo.Customer;
import pojo.ProductInfo;
import pojo.Pstock;
import pojo.ReturnSale;
import pojo.ReturnSaleList;
import pojo.SaleRecord;
import pojo.SaleRecordList;
import pojo.User;
import pojo.Warehouse;
import tools.CheckTool;
import tools.DateTool;
import tools.JsonTool;


@SuppressWarnings("serial")
public class TuihuoAction extends BaseAction {
	
	private ReturnSale returnSale;
	private List<ReturnSale> returnSales;
	private List<ReturnSaleList> returnSaleLists;
	private List<Warehouse> warehouses;
	private List<Object[]> objects;
	private String warehousename;
	private String jsondata;
	private String[] barcodes;
	private Double[] prices;
	private Integer[] quantitys;
	private Double[] amounts;
	private String remarks;
	private String customerid;
	private String begindate;
	private String enddate;
	private String returnsaleno;
	private String invoiceno;
	private Integer[] ids;
	private SaleRecord saleRecord;
	private List<SaleRecordList> saleRecordLists;
	private Integer[] choose;
	private String warehouse;
	/**
	 * 
	 */
	public void getSalerecordInfo(){
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter printWriter = response.getWriter();
			if(invoiceno!=null && !invoiceno.trim().equals("")){
				SaleRecord saleRecord = this.saleService.findByInvoiceNo(invoiceno);
				String json = JsonTool.getInstance().getsaleRecordJson(saleRecord);
				printWriter.write(json);
				printWriter.flush();
				printWriter.close();
				return;
			}else{
				printWriter.write("error01");
				printWriter.flush();
				printWriter.close();
				return;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * 通过invoiceno获得销售明细以便选择
	 */
	public void getSaleLists() throws Exception{
		response.setContentType("text/html;charset=utf-8");
			String json = "";
			PrintWriter printWriter = response.getWriter();
			if(invoiceno!=null && !invoiceno.trim().equals("")){
				List<SaleRecordList> recordLists = this.saleListService.listByInvoiceNo(invoiceno);
				if(!recordLists.isEmpty()){
					json = JsonTool.getInstance().createSalerecordInfo(recordLists);
					printWriter.write(json);
					printWriter.flush();
					printWriter.close();
					return;
				}else{
					printWriter.write("error01:no salerecordlists");
					printWriter.flush();
					printWriter.close();
					return;
				}
			}
			printWriter.write("error02:no invoiceno");
			printWriter.flush();
			printWriter.close();
			return;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toaddReturnSale() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"salefunction.jsp");
		warehouses = this.warehouseService.findByKeyword("");
		//限制搜索结果为s-开头的，因为s-开头的才是有效的销售单
		//List<SaleRecord> records = this.saleService.findBykeyword("s-");
		List<SaleRecord> records = this.saleService.findByHQL("FROM SaleRecord as s where s.status = 1");
		List<ProductInfo> infos = this.productinfoService.findByKeyword("");
		List<Customer> customers = this.customerService.findByKeyword("");
		if(records.size()>0){
			jsondata = JsonTool.getInstance().createSalerecordsJson(records)+JsonTool.getInstance().createInfoJson(infos)+JsonTool.getInstance().createCustomerJson(customers);
			return SUCCESS;
		}else{
			this.setMessage("没有销售单!");
			return INPUT;
		}
	}
	
	
	/**
	 * 创建退货单
	 * @return
	 * @throws Exception
	 */
	public String createreturnsale() throws Exception{
		//可要可不要，因为有loginfilter
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"tuihuo"+FILESEPARATOR+"toaddReturnSale.action");
		User user = (User) request.getSession().getAttribute("user");
		if(user==null){
			this.setMessage("请重新登录！");
			return LOGIN;
		}
		//判断是否有warehousename，因为warehousename为必要元素，与库存修改有关
		if(warehousename==null || warehousename.trim().equals("")){
			this.setMessage("请输入仓库!");
			return INPUT;
		}
		//customerid同为必要元素，记录进退货单的，便于查询操作
		if(customerid==null || customerid.trim().equals("")){
			return INPUT;
		}
		//invoiceno必要元素，退货单要对应有销售单
		if(invoiceno==null && invoiceno.trim().equals("")){
			this.setMessage("can not find invoiceno");
			return INPUT;
		}
		//查询销售单及其内容，salerecord and salerecordlists
		SaleRecord saleRecord = this.saleService.findByInvoiceNo(invoiceno);
		ReturnSale oldreturnSale = this.returnSaleService.findByInvoiceno(invoiceno); //曾经退过货的单
		//销售单存在的情况下才继续执行下面任务
		if(saleRecord!=null){
			List<SaleRecordList> recordLists = this.saleListService.listByInvoiceNo(invoiceno);
				if(barcodes.length>0 && prices.length>0 && quantitys.length>0){
					String returnno = this.myidService.createIDNumber("RE-");  //创建一个单号码
					returnSaleLists = new ArrayList<ReturnSaleList>();
					//List<SaleRecordList> duichonglist = new ArrayList<SaleRecordList>();
					for (int i = 0; i < barcodes.length; i++) {  //循环传入anction的barcodes
						String barcode = barcodes[i];
						if(barcode!=null &&!barcode.trim().equals("")){
							ProductInfo productInfo = this.productinfoService.findByBaecode(barcode);  //检查barcode是否有效
							if(productInfo!=null){
								Double price = prices[i];
								Integer quantity = quantitys[i];
								//重点：循环salerecordlist，检查是否有该barcode记录的销售单，有才往下执行
								for (SaleRecordList saleRecordList : recordLists) {
									if(barcode.equalsIgnoreCase(saleRecordList.getBarcode())){
										if(quantity>saleRecordList.getQuantity()){
											this.setMessage("\"退货数量\"大于\"发票销售数量\"");
											this.myidService.removeIdNumber("RE-", returnno);
											return INPUT;
										}
										//如果该发票曾经做过退货的时候如下处理
										if(oldreturnSale!=null){
											//首先，找到该barcode的returnsalelist，读取该次的退货数量
											//List<ReturnSaleList> oldReturnSaleList = this.returnSaleListService.findByreturnnoandbarcode(oldreturnSale.getReturnsaleno(),barcode);
											Integer oldReturnSaleListquantity = this.returnSaleListService.findOldQuantityByInvoiceAndbarcode(invoiceno, barcode);
											if(oldReturnSaleListquantity!=null){
												//Integer totalQuantity = oldReturnSaleListquantity;
												if(quantity>(saleRecordList.getQuantity()-oldReturnSaleListquantity)){
													this.setMessage("\"退货数量\"大于\"销售数量\"+\"曾经退货的数量\"!!!");
													this.myidService.removeIdNumber("RE-", returnno);
													return INPUT;
												}
											}
										}
										//在旧的销售发票上备注一下曾经做过退货操作，方便日后查询
										saleRecord.setRemark(saleRecord.getRemark()+" "+returnsaleno+":"
												+saleRecordList.getBarcode()+"--"+saleRecordList.getPdesc()+"退货数量:"+quantity
												+"退货价格:"+price);
									}
								}
								//创建一条returnsalelist，添加入列表，最后udatemany
								ReturnSaleList rsl = new ReturnSaleList(returnno, barcode, quantity, price, "");
								returnSaleLists.add(rsl);
							}
						}
					}
					returnSale  = new ReturnSale(returnno,invoiceno, returnSaleLists, user.getUserid(), customerid, warehousename, remarks);
					this.returnSaleListService.addManyObjects(returnSaleLists); //保存returnsalelists
					this.pstockService.updateReturn(returnSaleLists, warehousename);//更新库存
					this.returnSaleService.add(returnSale); //保存退货单
					this.saleService.update(saleRecord); //更新销售发票
					this.setReturnurl(request.getContextPath()+FILESEPARATOR+"tuihuo"+FILESEPARATOR+"listReturnSale.action");
					this.setMessage("操作成功!  ||  单号:"+returnno);
					return SUCCESS;
			}
			this.setMessage(ERRORMESSAGE);
			return INPUT;
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	/**
	 * 退货记录
	 * @return
	 * @throws Exception
	 */
	public String listReturnSale() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"salefunction.jsp");
		if(warehouses==null){
			warehouses = this.warehouseService.findByKeyword("");
		}
		warehouse = warehouse==null?"all":warehouse;
		if(begindate==null || begindate.trim().equals("")){
			begindate = DateTool.getInstance().DateToPattern1(new Date());
		}
		if(enddate==null || enddate.trim().equals("")){
			enddate = DateTool.getInstance().DateToPattern1(new Date());
		}
		if(warehouse.trim().equals("all")){
			returnSales = this.returnSaleService.listReturnSales(begindate+" 00:00:00", enddate+" 59:59:59");
		}else{
			returnSales = this.returnSaleService.listReturnSales(warehouse,begindate+" 00:00:00", enddate+" 59:59:59");
		}
		return SUCCESS;
	}
	
	/**
	 * 获得某一单的信息
	 * @return
	 * @throws Exception
	 */
	public String findByReturnNo() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"tuihuo"+FILESEPARATOR+"listReturnSale.action");
		if(returnsaleno!=null && !returnsaleno.trim().equals("")){
			returnSale = this.returnSaleService.findByReturnsale(returnsaleno);
			objects = this.returnSaleListService.findByReturnNo(returnsaleno);
			return SUCCESS;
		}
		return INPUT;
	}
	
	/**
	 * 作废退货单
	 * @return
	 * @throws Exception
	 */
	public String cancel() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"tuihuo"+FILESEPARATOR+"listReturnSale.action");
		if(returnsaleno!=null && !returnsaleno.trim().equals("")){
			returnSale = this.returnSaleService.findByReturnsale(returnsaleno);
			returnSaleLists = this.returnSaleListService.listByReturnNo(returnsaleno);
			if(returnSale!=null){
				returnSale.setStatus(0);
				String warehouse = returnSale.getWarehouse();
				for (ReturnSaleList returnSaleList : returnSaleLists) {
					Pstock pstock = this.pstockService.findbyBarcodeAndWarehouse(returnSaleList.getBarcode(), warehouse);
					if(pstock==null){
						this.setMessage(returnSaleList.getBarcode() + "");
						return INPUT;
					}
					Integer newquantity = pstock.getQuantity() - returnSaleList.getQuantity(); 
					this.pstockService.updateChangeInbound(pstock, newquantity);
					returnSaleList.setStatus(0);
				}
				this.returnSaleService.update(returnSale);
				this.returnSaleListService.updateManyObjects(returnSaleLists);
				return SUCCESS;
			}
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	/**
	 * 修改退货单，用ajax方式，系统更加统一
	 * @throws Exception
	 */
	public void updateReturnsaleAjax() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if(ids!=null && ids.length>0 && returnsaleno!=null && !returnsaleno.trim().equals("")){
			returnSale = this.returnSaleService.findByReturnsale(returnsaleno);
			List<SaleRecordList> saleRecordLists = this.saleListService.listByInvoiceNo(returnSale.getInvoiceno());
			String warehouse = returnSale.getWarehouse();
			returnSaleLists = new ArrayList<ReturnSaleList>();
			for (int i = 0; i < ids.length; i++) {
				int id = ids[i];
				ReturnSaleList list = this.returnSaleListService.get(ReturnSaleList.class, id);
				int salequantity = 0;
				for (SaleRecordList saleRecordList : saleRecordLists) {
					if(saleRecordList.getBarcode().equalsIgnoreCase(list.getBarcode())){
						salequantity = saleRecordList.getQuantity();
					}
				}
				if(CheckTool.getInstance().checkNumber(prices[i])){
					double price = prices[i];
					list.setPrice(price);
				}else{
					printWriter.write("error01:请输入正确的退货价格！");
					printWriter.flush();
					printWriter.close();
					return;
				}
				if(CheckTool.getInstance().checkNumber(quantitys[i])){
					Integer quantity = quantitys[i];
					if(quantity>salequantity){
						printWriter.write("error03:退货数大于销售数!请输入正确的退货数量！");
						printWriter.flush();
						printWriter.close();
						return;
					}
					Pstock pstock = this.pstockService.findbyBarcodeAndWarehouse(list.getBarcode(), warehouse);
					if(pstock==null){
						printWriter.write("error02:没有找到此商品的库存录入！"); 
						printWriter.flush();
						printWriter.close();
						return;
					}
					Integer newquantity = pstock.getQuantity() + (quantity-list.getQuantity());  //更新库存情况，最新库存 = 现有库存 + 操作差额 
					this.pstockService.updateChangeInbound(pstock, newquantity);
					list.setQuantity(quantity);
					list.setAmount(quantity*list.getPrice());
				}else{
					printWriter.write("error03:请输入正确的退货数量！");
					printWriter.flush();
					printWriter.close();
					return;
				}
				returnSaleLists.add(list);
			}
			returnSale.update(returnSaleLists);
			this.returnSaleListService.updateManyObjects(returnSaleLists);
			this.returnSaleService.update(returnSale);
			printWriter.write("success:操作成功！");
			printWriter.flush();
			printWriter.close();
			return;
		}
	}
	
	
	public ReturnSale getReturnSale() {
		return returnSale;
	}
	public void setReturnSale(ReturnSale returnSale) {
		this.returnSale = returnSale;
	}
	public List<ReturnSale> getReturnSales() {
		return returnSales;
	}
	public void setReturnSales(List<ReturnSale> returnSales) {
		this.returnSales = returnSales;
	}
	public List<ReturnSaleList> getReturnSaleLists() {
		return returnSaleLists;
	}
	public void setReturnSaleLists(List<ReturnSaleList> returnSaleLists) {
		this.returnSaleLists = returnSaleLists;
	}
	public List<Warehouse> getWarehouses() {
		return warehouses;
	}
	public void setWarehouses(List<Warehouse> warehouses) {
		this.warehouses = warehouses;
	}
	public String getWarehousename() {
		return warehousename;
	}
	public void setWarehousename(String warehousename) {
		this.warehousename = warehousename;
	}

	public String getJsondata() {
		return jsondata;
	}
	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}
	public String[] getBarcodes() {
		return barcodes;
	}
	public void setBarcodes(String[] barcodes) {
		this.barcodes = barcodes;
	}

	public Double[] getPrices() {
		return prices;
	}

	public void setPrices(Double[] prices) {
		this.prices = prices;
	}

	public Integer[] getQuantitys() {
		return quantitys;
	}

	public void setQuantitys(Integer[] quantitys) {
		this.quantitys = quantitys;
	}

	public Double[] getAmounts() {
		return amounts;
	}

	public void setAmounts(Double[] amounts) {
		this.amounts = amounts;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCustomerid() {
		return customerid;
	}
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
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
	public List<Object[]> getObjects() {
		return objects;
	}
	public void setObjects(List<Object[]> objects) {
		this.objects = objects;
	}
	public String getReturnsaleno() {
		return returnsaleno;
	}
	public void setReturnsaleno(String returnsaleno) {
		this.returnsaleno = returnsaleno;
	}
	public Integer[] getIds() {
		return ids;
	}
	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public SaleRecord getSaleRecord() {
		return saleRecord;
	}

	public void setSaleRecord(SaleRecord saleRecord) {
		this.saleRecord = saleRecord;
	}

	public List<SaleRecordList> getSaleRecordLists() {
		return saleRecordLists;
	}

	public void setSaleRecordLists(List<SaleRecordList> saleRecordLists) {
		this.saleRecordLists = saleRecordLists;
	}
	public Integer[] getChoose() {
		return choose;
	}
	public void setChoose(Integer[] choose) {
		this.choose = choose;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	
	
	
}
