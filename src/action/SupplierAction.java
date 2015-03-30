package action;

import java.io.ByteArrayInputStream;
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
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import pojo.Supplier;
import tools.CheckTool;
import tools.DateTool;

@SuppressWarnings("serial")
public class SupplierAction extends BaseAction {
	
	private Supplier supplier;
	private List<Supplier> suppliers;
	private InputStream inputStream;
	private Integer[] ids;
	/**
	 * ADD SUPPLIER
	 * @return
	 * @throws RuntimeException
	 */
	public String addsupplier() throws RuntimeException{
		String supplierid = request.getParameter("supplierid");
		String suppliername = request.getParameter("suppliername");
		String suppliertel = request.getParameter("suppliertel");
		String supplieraddress = request.getParameter("supplieraddress");
		this.returnurl = request.getContextPath()+FILESEPARATOR+"supplier"+FILESEPARATOR+"listsupplier.action";
		if(this.supplierService.findById(supplierid)!=null){
			this.setMessage("SORRY~此供应商ID已被使用");
			return INPUT;
		}
		if(supplierid!=null && supplierid.trim()!="" && suppliername!=null && suppliername.trim()!=""){
			supplier = new Supplier(supplierid, suppliername);
			if(suppliertel!=null && suppliertel.trim()!=""){
				supplier.setSupplierTel(suppliertel);
			}
			if(supplieraddress!=null && supplieraddress.trim()!=""){
				supplier.setSupplierAddress(supplieraddress);
			}
			this.supplierService.add(supplier);
			this.setMessage(SUCCMESSAGE);
			return SUCCESS;
		}
		this.setMessage(NECESSARY);
		return INPUT;
		
	}
	
	
	/**
	 * ADD SUPPLIER AJAX
	 * @return
	 * @throws RuntimeException
	 * @throws Exception 
	 */
	public void addsupplierAjax() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		String supplierid = request.getParameter("supplierid");
		String suppliername = request.getParameter("suppliername");
		String suppliertel = request.getParameter("suppliertel");
		String supplieraddress = request.getParameter("supplieraddress");
		if(this.supplierService.findById(supplierid)!=null){
			printWriter.write("error01:SORRY~THIS ID HAVE BEEN USED");
			printWriter.flush();
			printWriter.close();
			return;
		}
		if(supplierid!=null && supplierid.trim()!="" && suppliername!=null && suppliername.trim()!=""){
			supplier = new Supplier(supplierid, suppliername);
			if(suppliertel!=null && suppliertel.trim()!=""){
				supplier.setSupplierTel(suppliertel);
			}
			if(supplieraddress!=null && supplieraddress.trim()!=""){
				supplier.setSupplierAddress(supplieraddress);
			}
			this.supplierService.add(supplier);
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
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"supplier"+FILESEPARATOR+"listsupplier.action");
		return SUCCESS;
	}
	
	/**
	 * IMPORT SUPPLIERS FROM EXCEL
	 * @return
	 * @throws RuntimeException
	 * @throws BiffException
	 * @throws Exception
	 */
	public String importsuppliers() throws RuntimeException, BiffException, Exception{
		List<String> errormegs = new ArrayList<String>();
		List<String> succemegs = new ArrayList<String>();
		List<Supplier> importlist = new ArrayList<Supplier>();
		String returnmegs = "";
		this.returnurl = request.getContextPath()+FILESEPARATOR+"supplier"+FILESEPARATOR+"listsupplier.action";
		if(file!=null && file.exists()){
			Workbook workbook = Workbook.getWorkbook(file); 
			Sheet sheet = workbook.getSheet(0);
			boolean flag = true;
			for (int i = 1; i < sheet.getRows(); i++) {
				String supplierid = sheet.getCell(0 , i).getContents();
				String suppliername = sheet.getCell(1 , i).getContents();
				String suppliertel = sheet.getCell(2 , i).getContents();
				String supplieraddress = sheet.getCell(3 , i).getContents();
				if(supplierid!=null && supplierid.trim()!="" && suppliername!=null && suppliername.trim()!="" ){
					if(this.supplierService.findById(supplierid)!=null){
						errormegs.add(i+ " 行的供应商ID "+supplierid+" 已被用!");
						flag = false;
					}else{
						Supplier newobj = new Supplier(supplierid, suppliername);
						if(suppliertel!=null && suppliertel.trim()!=""){
							newobj.setSupplierTel(suppliertel);
						}
						if(supplieraddress!=null && supplieraddress.trim()!=""){
							newobj.setSupplierAddress(supplieraddress);
						}
						succemegs.add(i+" 行添加成功 "+supplierid);
						importlist.add(newobj);
					}
				}else{
					errormegs.add( i +" 行的 * 栏目不能为空!");
					flag = false;
				}
			}

			if(flag){
				this.supplierService.addManyObjects(importlist);
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
	 * TO UPDATE PAGE
	 * @return
	 * @throws RuntimeException
	 */
	public String toupdsupplier() throws RuntimeException{
		String supplierid = request.getParameter("supplierid");
		this.returnurl = request.getContextPath()+FILESEPARATOR+"supplier"+FILESEPARATOR+"listsupplier.action";
		if(supplierid!=null && supplierid.trim()!=""){
			supplier = this.supplierService.findById(supplierid);
				if(supplier!=null){
					return "TOUPDSUPPLIER";
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
	 * UPDATE SUPPLIER
	 * @return
	 * @throws RuntimeException
	 */
	public String updsupplier() throws RuntimeException{
		String supplierid = request.getParameter("supplierid");
		String suppliername = request.getParameter("suppliername");
		String suppliertel = request.getParameter("suppliertel");
		String supplieraddress = request.getParameter("supplieraddress");
		this.returnurl = request.getContextPath()+FILESEPARATOR+"supplier"+FILESEPARATOR+"listsupplier.action";
		if(supplierid!=null && supplierid.trim()!=""){
			supplier = this.supplierService.findById(supplierid);
			if(supplier!=null){
				if(suppliername!=null && suppliername.trim()!=""){
					supplier.setSupplierName(suppliername);
				}
				if(suppliertel!=null && suppliertel.trim()!=""){
					supplier.setSupplierTel(suppliertel);
				}
				if(supplieraddress!=null && supplieraddress.trim()!=""){
					supplier.setSupplierAddress(supplieraddress);
				}
				supplier.setUpdateTime(DateTool.getInstance().DateToPattern2(new Date()));
				this.supplierService.update(supplier);
				this.setMessage(SUCCMESSAGE);
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
	 * DELETE 
	 * @return
	 * @throws RuntimeException
	 * @throws Exception 
	 */
	public void deleteSupplier() throws RuntimeException, Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		String supplierid = request.getParameter("supplierid");
		if(CheckTool.getInstance().checkNull(supplierid)){
			supplier = this.supplierService.findById(supplierid);
			if(supplier!=null){
				supplier.setStatus(0);
				this.supplierService.update(supplier);
//				this.supplierService.delete(supplier);
				printWriter.write(SUCCMESSAGE);
				printWriter.flush();
				printWriter.close();
				return;
			}else{
				printWriter.write("error:"+ERRORMESSAGE);
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
	 * DELETE MANY OBJECTS
	 * @return
	 * @throws RuntimeException
	 * @throws Exception 
	 */
	public void deleteSupplierAjax() throws RuntimeException, Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if(ids.length>0){
			List<Supplier> list = new ArrayList<Supplier>();
			for (int i = 0; i < ids.length; i++) {
				Integer id = ids[i];
				supplier = this.supplierService.get(Supplier.class, id);
				if(supplier==null){
					printWriter.write("error:"+ERRORMESSAGE);
					printWriter.flush();
					printWriter.close();
					return;
				}
				supplier.setStatus(0);
				list.add(supplier);
			}
			try {
				//this.supplierService.deleteManyObjects(list);
				this.supplierService.updateManyObjects(list);
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
	 * 查看所有 supplier
	 * @return
	 * @throws RuntimeException
	 */
	public String listsupplier() throws RuntimeException{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"infofunction.jsp");
		if(keyword!=null && !keyword.trim().equals("")){
			suppliers = this.supplierService.findByKeyword(keyword);
		}else{
			suppliers = this.supplierService.findByKeyword("");
		}
		return "LIST";
	}
	
	/**
	 * 导出excel文件
	 * @return
	 * @throws Exception
	 */
	public String createExcel() throws Exception{
		String keyword = request.getParameter("keyword");
		if(keyword!=null && !keyword.trim().equals("")){
			suppliers = this.supplierService.findByKeyword(keyword);
		}else{
			suppliers = this.supplierService.findByKeyword("");
		}
		ByteOutputStream outputStream = new ByteOutputStream();
		WritableWorkbook workbook = Workbook.createWorkbook(outputStream);
		WritableSheet sheet = workbook.createSheet("sheet1", 0);
		int row = 0;
		Label label01 = new Label(0, row, "SUPPLIER-ID*");
		Label label02 = new Label(1, row, "SUPPLIER-NAME*");
		Label label03 = new Label(2, row, "SUPPLIER-TEL");
		Label label04 = new Label(3, row, "SUPPLIER-ADDRESS");
		sheet.addCell(label01);
		sheet.addCell(label02);
		sheet.addCell(label03);
		sheet.addCell(label04);
		row++;
		for (Supplier supplier : suppliers) {
			Label label1 = new Label(0, row, supplier.getSupplierId());
			Label label2 = new Label(1, row, supplier.getSupplierName());
			Label label3 = new Label(2, row, supplier.getSupplierTel());
			Label label4 = new Label(3, row, supplier.getSupplierAddress());
			row++;
			sheet.addCell(label1);
			sheet.addCell(label2);
			sheet.addCell(label3);
			sheet.addCell(label4);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public List<Supplier> getSuppliers() {
		return suppliers;
	}
	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
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
