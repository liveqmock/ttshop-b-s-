package action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import pojo.InnoteList;
import pojo.Product;
import pojo.ProductInfo;
import pojo.Pstock;
import pojo.User;
import pojo.Warehouse;

public class StockAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Pstock pstock;
	private List<Pstock> pstocks;
	private List<Warehouse> warehouses;
	private InputStream inputStream;
	private String warehouse;
	private List<Object[]> objects;

	/**
	 * 查看产品库存状况
	 * @return
	 * @throws Exception
	 */
	public String listStock() throws Exception{
		warehouses = this.warehouseService.findByKeyword("");
		objects = new ArrayList<Object[]>();
		warehouse = warehouse==null?"all":warehouse;
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"warehousefunction.jsp");
		if(warehouse.trim().equals("all")){
			List<Object[]> objs = this.pstockService.listStock();
			for (int i = 0; i < objs.size(); i++) {
				String imeis = "";
				Object[] obs = new Object[8];
				Object[] temp = objs.get(i);
				String barcode = temp[1].toString();
				obs[0] = temp[0]; //id
				obs[1] = temp[1]; //barcode
				obs[2] = temp[2]; //pdesc
				obs[3] = temp[3]; //warehouse
				obs[4] = temp[4]; //quantity
				obs[5] = temp[5]; //updatetime
				obs[6] = temp[6]; //status
				//temp[7] //itype
				if(temp[7].toString().equals("2")){
					//itype类型为2才需要查找 imei 
					List<Product> products = this.productService.findImeis(barcode, obs[3].toString());
					for (int j = 0; j < products.size(); j++) {
						imeis=imeis+products.get(j).getImei();
						if(j<(products.size()-1)){
							imeis=imeis+",";
						}
					}
					obs[7] = imeis;
				}else{
					obs[7] = "此产品不含有 IMEI ";
				}
				objects.add(obs);
			}
		}else{
			//List<Object[]> objs = this.pstockService.listStock(1,warehouse);
			List<Object[]> objs = this.pstockService.listStock(warehouse);
			for (int i = 0; i < objs.size(); i++) {
				String imeis = "";
				Object[] obs = new Object[8];
				Object[] temp = objs.get(i);
				String barcode = temp[1].toString();
				obs[0] = temp[0];
				obs[1] = temp[1];
				obs[2] = temp[2];
				obs[3] = temp[3];
				obs[4] = temp[4];
				obs[5] = temp[5];
				obs[6] = temp[6];
				if(temp[7].toString().equals("2")){
					//itype类型为2才需要查找 imei 
					List<Product> products = this.productService.findImeis(barcode, warehouse);
					for (int j = 0; j < products.size(); j++) {
						imeis=imeis+products.get(j).getImei();
						if(j<(products.size()-1)){
							imeis=imeis+",";
						}
					}
					obs[7] = imeis;
				}else{
					obs[7] = "此产品不含有 IMEI ";
				}
				objects.add(obs);
			}
		}
		return SUCCESS;
	}
	
	
	/**
	 * 库存表导出到 excel 
	 * @return
	 * @throws Exception
	 */
	public String listStockExcel() throws Exception{
		warehouses = this.warehouseService.findByKeyword("");
		warehouse = warehouse==null?"all":warehouse;
		List<Object[]> objs = new ArrayList<Object[]>();
		if(warehouse.trim().equals("all")){
			objs = this.pstockService.listStock();
		}else{
			objs = this.pstockService.listStock(warehouse);
		}
		//List<Object[]> objs = this.pstockService.listStock(1,warehouse);
		ByteOutputStream os = new ByteOutputStream();
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		WritableSheet sheet = workbook.createSheet(warehouse+"存库表", 0);
		WritableCellFormat c = new WritableCellFormat();
		c.setAlignment(jxl.format.Alignment.CENTRE);
		
		int row = 0;
		Label l00 = new Label(0, 0, "产品编码",c);
		Label l01 = new Label(1, 0, "产品名称",c);
		Label l02 = new Label(2, 0, "仓存位置",c);
		Label l03 = new Label(3, 0, "数量",c);
		Label l04 = new Label(4, 0, "更新日期",c);
		Label l05 = new Label(5, 0, "Imei",c);
		sheet.addCell(l00);
		sheet.addCell(l01);
		sheet.addCell(l02);
		sheet.addCell(l03);
		sheet.addCell(l04);
		sheet.addCell(l05);
		row++;
		for (int i = 0; i < objs.size(); i++) {
			String imeis = "";
			Object[] temp = objs.get(i);
			String barcode = temp[1].toString();
			if(temp[7]!=null){
				if(temp[7].toString().equals("2")){
				List<Product> products = this.productService.findImeis(barcode, warehouse);
				for (int j = 0; j < products.size(); j++) {
					imeis=imeis+products.get(j).getImei();
					if(j<(products.size()-1)){
						imeis=imeis+",";
					}
				}
			  }
			}
			Integer quan = 0;
			if(temp[4]!=null && temp[4].toString().matches("-?[0-9]*")){
				quan = Integer.valueOf(temp[4].toString());
			}else{
				this.setMessage(temp[1]+"-"+temp[2]+","+"没有数量！请联系管理员！");
				return ERROR;
			}
			Label l0 = new Label(0, row, temp[1].toString(),c);
			Label l1 = new Label(1, row, temp[2].toString(),c);
			Label l2 = new Label(2, row, temp[3].toString(),c);
			Number l3 = new Number(3, row, quan,c);
			Label l4 = new Label(4, row, temp[5].toString(),c);
			Label l5 = new Label(5, row, imeis.toString(),c);
			sheet.addCell(l0);
			sheet.addCell(l1);
			sheet.addCell(l2);
			sheet.addCell(l3);
			sheet.addCell(l4);
			sheet.addCell(l5);
			row++;
		}
		String formulastr = "SUM(D"+2+":D"+row+")";
		Formula f03 = new Formula(3, row, formulastr,c);
		sheet.addCell(f03);
		for (int i = 0; i < sheet.getColumns(); i++) {
			sheet.setColumnView(i, 20);
		}
		workbook.write();
		workbook.close();
		response.reset();
		response.setContentType("application/vn.ms-xls");
		response.setCharacterEncoding("utf-8");
		this.inputStream = new ByteArrayInputStream(os.getBytes());
		return SUCCESS;
	}
	
	
	/**
	 * 初始化库存
	 * @return
	 * @throws Exception
	 */
	public String toinitStock() throws Exception{
		warehouses = this.warehouseService.findByKeyword("");
		return SUCCESS;
	}
	
	/**
	 * 初始化库存
	 * @return
	 * @throws Exception
	 */
	public String initStock() throws Exception{
		String msg = "";
		if(file!=null && file.exists()){
			if(warehouse!=null || !warehouse.trim().equals("")){
				List<Pstock> pstocks = this.pstockService.findByHQL("From Pstock as p");
				List<Pstock> newpstocks = new ArrayList<Pstock>();
				Workbook workbook = Workbook.getWorkbook(file);
				Sheet sheet = workbook.getSheet(0);
				for (int i = 1; i < sheet.getRows(); i++) {
					String barcode = sheet.getCell(0, i).getContents();
					//String pdesc = sheet.getCell(1, i).getContents();
					if(!barcode.trim().equals("")){
						ProductInfo info = this.productinfoService.findByBaecode(barcode);
						if(info!=null){
							String q = sheet.getCell(2, i).getContents();
							Integer quantity = Integer.valueOf(q);
							Pstock pstock = new Pstock(info, warehouse, quantity);
							newpstocks.add(pstock);
							msg+=barcode+"库存:"+quantity+"<br>";
						}else{
							msg+=barcode+"有错误,不能初始化!"+"<br>";
						}
					}
				}
				this.pstockService.deleteManyObjects(pstocks);
				this.pstockService.addManyObjects(newpstocks);
				this.setMessage(msg);
				return SUCCESS;
			}
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	/**
	 * 恢复库存
	 * @return
	 * @throws Exception
	 */
	public String recoverStock() throws Exception{
		User user = (User) request.getSession().getAttribute("user");
		if(user==null || !user.getRole().equals("0")){
			this.setMessage("请用管理员账号登陆！");
			return ERROR;
		}
		List<InnoteList> innoteLists = this.innoteListService.findByHQL("FROM InnoteList");
		for (InnoteList innoteList : innoteLists) {
			String barcode = innoteList.getBarcode();
			Integer totalsale = this.saleListService.sumTotalByBarcode(barcode);
			Integer totalin = this.innoteListService.sumTotalByBarcode(barcode);
			Integer cs = totalin-totalsale;
			pstock = this.pstockService.findbyBarcode(barcode);
			if(pstock!=null){
				pstock.setQuantity(cs);
				this.pstockService.update(pstock);
			}else{
				this.setMessage(ERRORMESSAGE);
				return INPUT;
			}
		}
		this.setMessage(SUCCMESSAGE);
		return SUCCESS;
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
	public List<Warehouse> getWarehouses() {
		return warehouses;
	}
	public void setWarehouses(List<Warehouse> warehouses) {
		this.warehouses = warehouses;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public List<Object[]> getObjects() {
		return objects;
	}
	public void setObjects(List<Object[]> objects) {
		this.objects = objects;
	}
	
	
	
}
