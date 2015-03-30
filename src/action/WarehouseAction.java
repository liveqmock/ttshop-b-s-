package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import pojo.Warehouse;
import tools.DateTool;

@SuppressWarnings("serial")
public class WarehouseAction extends BaseAction {
	private Warehouse warehouse;
	private List<Warehouse> warehouses;
	private Integer[] ids;
	private InputStream inputStream;
	/**
	 * ADD WAREHOUSE
	 * @return
	 * @throws RuntimeException
	 */
	public String addwarehouse() throws RuntimeException {
		String wname = request.getParameter("wname");
		String wadmin = request.getParameter("wadmin");
		String wnickname = request.getParameter("wnickname");
		this.returnurl = request.getContextPath()+FILESEPARATOR+"warehouse"+FILESEPARATOR+"listwarehouse.action";
		if(this.warehouseService.finybyName(wname)!=null){
			setMessage("SORRY~已被使用");
			return INPUT;
		}
		if(wname!=null && !wname.trim().equals("")){
			warehouse = new Warehouse(wname);
			if(wnickname!=null && !wnickname.trim().equals("")){
				warehouse.setWnickname(wnickname);
			}
			if(wadmin!=null && !wadmin.trim().equals("")){
				warehouse.setWadmin(wadmin);
			}
			this.warehouseService.add(warehouse);
			this.setMessage(SUCCMESSAGE);
			return SUCCESS;
		}
		this.setMessage(NECESSARY);
		return INPUT;
	}
	/**
	 * ADD WAREHOUSE AJAX
	 * @return
	 * @throws RuntimeException
	 * @throws Exception 
	 */
	public void addwarehouseAjax() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		String wname = request.getParameter("wname");
		String wadmin = request.getParameter("wadmin");
		String wnickname = request.getParameter("wnickname");
		if(this.warehouseService.finybyName(wname)!=null){
			printWriter.write("error01:SORRY~ID HAVE BEEN USED");
			printWriter.flush();
			printWriter.close();
			return;
		}
		if(wname!=null && !wname.trim().equals("")){
			warehouse = new Warehouse(wname);
			if(wnickname!=null && !wnickname.trim().equals("")){
				warehouse.setWnickname(wnickname);
			}
			if(wadmin!=null && !wadmin.trim().equals("")){
				warehouse.setWadmin(wadmin);
			}
			this.warehouseService.add(warehouse);
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
	 * DELETE warehouse AJAX
	 * @return
	 * @throws RuntimeException
	 */
	public void deletewarehouseAjax() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if(ids.length>0){
			List<Warehouse> list = new ArrayList<Warehouse>();
			for (int i = 0; i < ids.length; i++) {
				Integer id = ids[i];
				warehouse = this.warehouseService.get(Warehouse.class, id);
				if(warehouse==null){
					printWriter.write("error:"+ERRORMESSAGE);
					printWriter.flush();
					printWriter.close();
					return;
				}
				warehouse.setStatus(0);
				list.add(warehouse);
			}
			try {
				//this.warehouseService.deleteManyObjects(list);
				this.warehouseService.updateManyObjects(list);
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
	public String toupdwarehouse() throws RuntimeException{
		String id = request.getParameter("id");
		this.returnurl = request.getContextPath()+FILESEPARATOR+"warehouse"+FILESEPARATOR+"listwarehouse.action";
		if(id!=null && id.trim()!=""){
			Integer wid = Integer.valueOf(id);
			warehouse = this.warehouseService.load(Warehouse.class, wid);
				if(warehouse!=null){
					return "TOUPD";
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
	 * UPDATE WAREHOUSE
	 * @return
	 * @throws RuntimeException
	 */
	public String updwarehouse() throws RuntimeException{
		String id = request.getParameter("id");
		String wname = request.getParameter("wname");
		String wadmin = request.getParameter("wadmin");
		String wnickname = request.getParameter("wnickname");
		this.returnurl = request.getContextPath()+FILESEPARATOR+"warehouse"+FILESEPARATOR+"listwarehouse.action";
		if(id!=null && wname!=null && wname.trim()!=""){
			Integer wid = Integer.valueOf(id);
			warehouse = this.warehouseService.load(Warehouse.class, wid);
			if(warehouse!=null){
				warehouse.setWname(wname);
				if(wnickname!=null && !wnickname.trim().equals("")){
					warehouse.setWnickname(wnickname);
				}
				if(wadmin!=null && !wadmin.trim().equals("")){
					warehouse.setWadmin(wadmin);
				}
				warehouse.setUpdateTime(DateTool.getInstance().DateToPattern2(new Date()));
				this.warehouseService.update(warehouse);
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
	 * FIND BY KEYWORD / LIST WAREHOUSES
	 * @return
	 * @throws RuntimeException
	 */
	public String listwarehouse() throws RuntimeException{
		//String keyword = request.getParameter("keyword");
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"infofunction.jsp");
		if(keyword!=null && !keyword.trim().equals("")){
			warehouses = this.warehouseService.findByKeyword(keyword);
		}else{
			warehouses = this.warehouseService.findByKeyword("");
		}
		return "LIST";
	}
	
	/**
	 * 导出到 excel 表
	 * @return
	 * @throws Exception
	 */
	public String createExcel() throws Exception{
		//String keyword = request.getParameter("keyword");
		System.out.println(keyword);
		if(keyword!=null && !keyword.equals("")){
			warehouses = this.warehouseService.findByKeyword(keyword);
			System.out.println(warehouses.size());
		}else{
			warehouses = this.warehouseService.findByKeyword("");
		}
		ByteOutputStream outputStream = new ByteOutputStream();
		WritableWorkbook workbook = Workbook.createWorkbook(outputStream);
		WritableSheet sheet = workbook.createSheet("sheet1", 0);
		int row = 0;
		Label label01 = new Label(0, row, "WAREHOUSE-编号*");
		Label label02 = new Label(1, row, "WAREHOUSE-名称*");
		Label label03 = new Label(2, row, "WAREHOUSE-管理员*");
		sheet.addCell(label01);
		sheet.addCell(label02);
		sheet.addCell(label03);
		row++;
		for (Warehouse warehouse : warehouses) {
			Label label1 = new Label(0, row, warehouse.getWname());
			Label label2 = new Label(1, row, warehouse.getWnickname());
			Label label3 = new Label(2, row, warehouse.getWadmin());
			row++;
			sheet.addCell(label1);
			sheet.addCell(label2);
			sheet.addCell(label3);
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
	
	
	
	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public List<Warehouse> getWarehouses() {
		return warehouses;
	}

	public void setWarehouses(List<Warehouse> warehouses) {
		this.warehouses = warehouses;
	}
	public Integer[] getIds() {
		return ids;
	}
	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	
	
	
	
	
	
	
	

	
	
	
	
}
