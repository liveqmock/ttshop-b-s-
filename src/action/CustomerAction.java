package action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import pojo.Customer;
import tools.CheckTool;
import tools.DateTool;
import tools.JsonTool;

@SuppressWarnings("serial")
public class CustomerAction extends BaseAction {
	private Customer customer;
	private List<Customer> customers;
	private InputStream inputStream;
	private Integer[] ids;
	
	/**
	 * 生成所有customer的json
	 * @throws IOException
	 */
	public void findCustomer() throws IOException{
		PrintWriter printWriter = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		//response.setCharacterEncoding("utf-8");
		List<Customer> customers = this.customerService.findByKeyword("");
		List<String[]> list = new ArrayList<String[]>();
		//Map map = new HashMap();
		for (Customer customer : customers) {
			String[] args = {customer.getCustomerName(),customer.getCustomerName()+"|"+customer.getCustomerName()};
			list.add(args);
			//map.put("value", customer.getCustomerName());
			//map.put("label", customer.getCustomerName()+"|"+customer.getCustomerName());
		}
		String json = JsonTool.getInstance().formList(list, "value","label");
		//JSONObject jsonObject = JSONObject.fromObject(map);
		//System.out.println(jsonObject.toString());
		//String json = JsonTool.getInstance().formList(list);
		//System.out.println(json);
		printWriter.print(json);
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * 查询折扣
	 * @throws IOException
	 */
	public void findDiscount() throws IOException{
		PrintWriter printWriter = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		String cid = request.getParameter("cid");
		if(cid!=null && !cid.trim().equals("")){
			Customer cus = this.customerService.findById(cid);
			if(cus!=null){
				if(cus.getDiscountrate()!=null){
					printWriter.print("{discountrate:"+cus.getDiscountrate()+"}");
					printWriter.flush();
					printWriter.close();
					return;
				}
				printWriter.print("{discountrate:100}");
				printWriter.flush();
				printWriter.close();
				return;
			}
			printWriter.print("error02:"+"找不到此客户");
			printWriter.flush();
			printWriter.close();
			return;
		}
		printWriter.print("error01:"+"客户Id有误");
		printWriter.flush();
		printWriter.close();
		return;
	}
	
	
	/**
	 * ADD CUSTOMER
	 * @return
	 * @throws Exception 
	 */
	public String addCustomer() throws Exception {
		String customerid = request.getParameter("customerid");
		String customername = request.getParameter("customername");
		String customertel = request.getParameter("customertel");
		String customeraddress = request.getParameter("customeraddress");
		//String filename = request.getRealPath("js")+"/data.js";	
		this.returnurl = request.getContextPath()+FILESEPARATOR+"customer"+FILESEPARATOR+"listcustomer.action";
		if(this.customerService.findById(customerid)!=null){
			setMessage("SORRY~此客户ID已被使用");
			return INPUT;
		}
		if(customerid!=null && customerid.trim()!="" && customername!=null && customername.trim()!=""){
			customer = new Customer(customerid, customername);
			if(customertel!=null && customertel.trim()!=""){
				customer.setCustomerTel(customertel);
			}
			if(customeraddress!=null && customeraddress.trim()!=""){
				customer.setCustomerAddress(customeraddress);
			}
			customer.setCredits(0);
			customer.setDiscountrate(100.00);
			this.customerService.add(customer);
			//List<Customer> customers = this.customerService.findByKeyword("");
			//this.customerService.createJson(customers, filename);
			this.setMessage(SUCCMESSAGE);
			return SUCCESS;
		}
		this.setMessage(NECESSARY);
		return INPUT;
	}
	
	/**
	 * ADD CUSTOMER AJAX
	 * @return
	 * @throws Exception 
	 */
	public void addCustomerAjax() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		String customerid = request.getParameter("customerid");
		String customername = request.getParameter("customername");
		String customertel = request.getParameter("customertel");
		String customeraddress = request.getParameter("customeraddress");
		if(this.customerService.findById(customerid)!=null){
			//setMessage("SORRY~此客户ID已被使用");
			printWriter.write("error01:SORRY~ID HAVE BEEN USED");
			printWriter.flush();
			printWriter.close();
			return;
		}
		if(customerid!=null && !customerid.trim().equals("") && customername!=null && !customername.trim().equals("")){
			customer = new Customer(customerid, customername);
			if(customertel!=null && customertel.trim()!=""){
				customer.setCustomerTel(customertel);
			}
			if(customeraddress!=null && customeraddress.trim()!=""){
				customer.setCustomerAddress(customeraddress);
			}
			customer.setCredits(0);
			customer.setDiscountrate(100.00);
			this.customerService.add(customer);
			printWriter.write(SUCCMESSAGE);
			printWriter.flush();
			printWriter.close();
			return;
		}else{
			printWriter.write("error02:"+NECESSARY);
			printWriter.flush();
			printWriter.close();
			return;
		}
	}
	
	/**
	 * 跳转至import页面
	 * @return
	 * @throws Exception
	 */
	public String toimportpage() throws Exception{
		this.returnurl = request.getContextPath()+FILESEPARATOR+"customer"+FILESEPARATOR+"listcustomer.action";
		return SUCCESS;
	}
	
	/**
	 * 从 EXCEL 表导入客户
	 * @return
	 * @throws RuntimeException
	 * @throws Exception 
	 * @throws BiffException 
	 */
	public String importcustomers() throws RuntimeException, BiffException, Exception{
		List<String> errormegs = new ArrayList<String>();
		List<String> succemegs = new ArrayList<String>();
		List<Customer> importlist = new ArrayList<Customer>();
		String returnmegs = "";
		this.returnurl = request.getContextPath()+FILESEPARATOR+"customer"+FILESEPARATOR+"listcustomer.action";
		if(file!=null && file.exists()){
			Workbook workbook = Workbook.getWorkbook(file); 
			Sheet sheet = workbook.getSheet(0);
			boolean flag = true;
			for (int i = 1; i < sheet.getRows(); i++) {
				String customerid = sheet.getCell(0 , i).getContents();
				String customername = sheet.getCell(1 , i).getContents();
				String customertel = sheet.getCell(2 , i).getContents();
				String customeraddress = sheet.getCell(3 , i).getContents();
				if(customerid!=null && customerid.trim()!="" && customername!=null && customername.trim()!="" ){
					if(this.customerService.findById(customerid)!=null){
						errormegs.add(i+ " 行的客户ID号： "+customerid+" 已被用!");
						flag = false;
					}else{
						Customer newobj = new Customer(customerid, customername);
						if(customertel!=null && customertel.trim()!=""){
							newobj.setCustomerTel(customertel);
						}
						if(customeraddress!=null && customeraddress.trim()!=""){
							newobj.setCustomerAddress(customeraddress);
						}
						succemegs.add(i+" 行添加成功， "+customerid);
						newobj.setCredits(0);
						newobj.setDiscountrate(100.00);
						importlist.add(newobj);
					}
				}else{
					errormegs.add( i +"行，带*的列不能为空!");
					flag = false;
				}
			}

			if(flag){
				this.customerService.addManyObjects(importlist);
				for (String str : succemegs) {
					returnmegs = returnmegs + str+"</br>";
				}
				this.setMessage(returnmegs);
				return SUCCESS;
			}else{
				for (String str : errormegs) {
					returnmegs = returnmegs + str+"</br>";
				}
				this.setMessage(returnmegs);
				return INPUT;
			}
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	/**
	 * 导出客户到 excel 表
	 * @return
	 * @throws Exception
	 */
	public String createExcel() throws Exception{
		String keyword = request.getParameter("keyword");
		if(keyword!=null && !keyword.equals("")){
			customers = this.customerService.findByKeyword(keyword);
		}else{
			customers = this.customerService.findByKeyword("");
		}
		ByteOutputStream outputStream = new ByteOutputStream();
		WritableWorkbook workbook = Workbook.createWorkbook(outputStream);
		WritableSheet sheet = workbook.createSheet("sheet1", 0);
		int row = 0;
		Label label01 = new Label(0, row, "CUSTOMER-ID*");
		Label label02 = new Label(1, row, "CUSTOMER-NAME*");
		Label label03 = new Label(2, row, "CUSTOMER-TEL");
		Label label04 = new Label(3, row, "CUSTOMER-ADDRESS");
		Label label05 = new Label(4, row, "CREADIT");
		Label label06 = new Label(5, row, "DISCOUNT-RATE");
		sheet.addCell(label01);
		sheet.addCell(label02);
		sheet.addCell(label03);
		sheet.addCell(label04);
		sheet.addCell(label05);
		sheet.addCell(label06);
		row++;
		for (Customer customer : customers) {
			Label label1 = new Label(0, row, customer.getCustomerId());
			Label label2 = new Label(1, row, customer.getCustomerName());
			Label label3 = new Label(2, row, customer.getCustomerTel());
			Label label4 = new Label(3, row, customer.getCustomerAddress());
			Number label5 = new Number(4, row, customer.getCredits());
			Number label6 = new Number(5, row, customer.getDiscountrate());
			row++;
			sheet.addCell(label1);
			sheet.addCell(label2);
			sheet.addCell(label3);
			sheet.addCell(label4);
			sheet.addCell(label5);
			sheet.addCell(label6);
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
		response.setContentType("application/vn.ms-xls");
		response.setCharacterEncoding("utf-8");
		this.inputStream = new ByteArrayInputStream(outputStream.getBytes());
		return SUCCESS;
	}
	
	
	
	
	/**
	 * DELETE 
	 * @return
	 * @throws RuntimeException
	 * @throws Exception 
	 */
	public void deleteCustomer() throws RuntimeException, Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		String customerid = request.getParameter("customerid");
		if(CheckTool.getInstance().checkNull(customerid)){
			customer = this.customerService.findById(customerid);
			if(customer!=null){
				customer.setStatus(0);
				//this.customerService.delete(customer);
				this.customerService.update(customer);
				printWriter.write(SUCCMESSAGE);
				printWriter.flush();
				printWriter.close();
				return;
			}else{
				printWriter.write(ERRORMESSAGE);
				printWriter.flush();
				printWriter.close();
				return;
			}
		}else{
			printWriter.write(ERRORMESSAGE);
			printWriter.flush();
			printWriter.close();
			return;
		}
	}
	
	/**
	 * DELETE MANY OBJECTS
	 * @return
	 * @throws RuntimeException
	 * @throws Exception 
	 */
	public void deleteCustomerAjax() throws RuntimeException, Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if(ids.length>0){
			List<Customer> list = new ArrayList<Customer>();
			for (int i = 0; i < ids.length; i++) {
				Integer id = ids[i];
				customer = this.customerService.get(Customer.class, id);
				if(customer==null){
					printWriter.write("error:"+ERRORMESSAGE);
					printWriter.flush();
					printWriter.close();
					return;
				}
				customer.setStatus(0);
				list.add(customer);
			}
			try {
				this.customerService.updateManyObjects(list);
				//this.customerService.deleteManyObjects(list);
				printWriter.write(SUCCMESSAGE);
				printWriter.flush();
				printWriter.close();
				return;
			} catch (Exception e) {
				printWriter.write("error:"+e.getMessage());
				printWriter.flush();
				printWriter.close();
				return;
			}
		}else{
			printWriter.write("error:"+ERRORMESSAGE);
			printWriter.flush();
			printWriter.close();
			return;
		}
	}
	
	
	/**
	 * TO UPDATE PAGE
	 * @return
	 * @throws RuntimeException
	 */
	public String toupdCustomer() throws RuntimeException{
		String customerid = request.getParameter("customerid");
		this.returnurl = request.getContextPath()+FILESEPARATOR+"customer"+FILESEPARATOR+"listcustomer.action";
		if(customerid!=null && customerid.trim()!=""){
			customer = this.customerService.findById(customerid);
				if(customer!=null){
					return "TOUPDCUSTOMER";
				}else{
					this.setMessage(ERRORMESSAGE);
					return INPUT;
				}
		}else{
			this.setMessage(ERRORMESSAGE);
			return INPUT;
		}
	}
	
	/**
	 * UPDATE CUSTOMER
	 * @return
	 * @throws Exception 
	 */
	public String updCustomer() throws Exception{
		String customerid = request.getParameter("customerid");
		String customername = request.getParameter("customername");
		String customertel = request.getParameter("customertel");
		String customeraddress = request.getParameter("customeraddress");
		String discountrate = request.getParameter("discountrate");
		this.returnurl = request.getContextPath()+FILESEPARATOR+"customer"+FILESEPARATOR+"listcustomer.action";
		if(customerid!=null && customerid.trim()!=""){
			customer = this.customerService.findById(customerid);
			if(customer!=null){
				if(customername!=null && customername.trim()!=""){
					customer.setCustomerName(customername);
				}
				if(customertel!=null && customertel.trim()!=""){
					customer.setCustomerTel(customertel);
				}
				if(customeraddress!=null && customeraddress.trim()!=""){
					customer.setCustomerAddress(customeraddress);
				}
				if(discountrate!=null && CheckTool.getInstance().checkNumber(discountrate)){
					double dis = Double.valueOf(discountrate);
					customer.setDiscountrate(dis);
				}else{
					this.setMessage("请输入正确的折扣率");
					return INPUT;
				}
				customer.setUpdateTime(DateTool.getInstance().DateToPattern2(new Date()));
				this.customerService.update(customer);
				this.setMessage("操作成功！");
				return SUCCESS;
			}else{
				this.setMessage(ERRORMESSAGE);
				return INPUT;
			}
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	
	
	/**
	 * FIND BY KEYWORD / LIST CUSTOMERS
	 * @return
	 * @throws RuntimeException
	 */
	public String listCustomer() throws RuntimeException{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"infofunction.jsp");
		if(keyword!=null && !keyword.trim().equals("")){
			customers = this.customerService.findByKeyword(keyword);
		}else{
			customers = this.customerService.findByKeyword("");
		}
		return "LIST";
	}
	
	
	
	
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public Integer[] getIds() {
		return ids;
	}
	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	
	
	
	
	
}
