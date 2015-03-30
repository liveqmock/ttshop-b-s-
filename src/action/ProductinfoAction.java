package action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import pojo.Picture;
import pojo.ProductInfo;
import pojo.Pstock;
import pojo.Ptype;
import pojo.Warehouse;
import tools.CheckTool;
import tools.DateTool;
import tools.JsonTool;

@SuppressWarnings("serial")
public class ProductinfoAction extends BaseAction {
	
	private ProductInfo productinfo;
	private List<ProductInfo> productinfos;
	private List<Object[]> objects;
	private List<Ptype> ptypes;
	private InputStream inputStream;
	private String filename;
	private Integer[] ids;
	private String barcode;
	private String mainpictureurl;
	private String introduction;
	private Integer ptype; 
	private String pdesc;
	private String pcolor;
	private String pbrand;
	private double sprice;
	private double mprice;
	
	public void getbarcode() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		String barcode = request.getParameter("barcode");
		productinfo = this.productinfoService.findByBaecode(barcode);
		String json2 = "";
		if(productinfo!=null){
			json2 = JsonTool.getInstance().getInfoDesc(productinfo);
		}
		writer.print(json2);
		writer.flush();
		writer.close();
	}
	
	public void getinfo() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		String barcode = request.getParameter("barcode");
		String warehouse = request.getParameter("warehouse");
		Warehouse warehouse2 = this.warehouseService.get(Warehouse.class, Integer.valueOf(warehouse));
		if (warehouse2==null) {
			printWriter.write("error:"+"please select warehouse!");
			printWriter.flush();
			printWriter.close();
			return;
		}
		ProductInfo productInfo = this.productinfoService.findByBaecode(barcode);
		if(productInfo==null){
			printWriter.write("noinfo:"+"WE CAN NOT FIND THIS PRODUCT-INFO,PLEASE CREATE NEW ONE!");
			printWriter.flush();
			printWriter.close();
			return;
		}
		Pstock pstock = this.pstockService.findbyBarcodeAndWarehouse(barcode, warehouse2.getWname());
		if(pstock==null){
			printWriter.write("error:"+"WE CAN NOT SEARCH THIS PRODUCT IN ANY WAREHOUSES!");
			printWriter.flush();
			printWriter.close();
			return;
		}
		String json = JsonTool.getInstance().getInfoDetail(productInfo, "", pstock.getQuantity());
		printWriter.write(json.trim());
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 * 查询PRODUCTINFO资料
	 * 传入 warehouse 为 wid , barcode
	 * 
	 * @throws Exception
	 */
	public void findProductInfo() throws Exception{
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
		/**
		Pstock pstock = this.pstockService.findbyBarcodeAndWarehouse(barcode, warehouse2.getWname());
		if(pstock==null){
			printWriter.write("error:"+"no any stock record in warehouse!");
			printWriter.flush();
			printWriter.close();
			return;
		}*/
		//String json = JsonTool.getInstance().getInfoDetail(productInfo, "", pstock.getQuantity());
		String json = JsonTool.getInstance().getInfoDesc(productInfo);
		printWriter.write(json.trim());
		printWriter.flush();
		printWriter.close();
		return;
	}
	
	/**
	 * TO ADD PAGE
	 * @return
	 * @throws RuntimeException
	 * @throws Exception 
	 */
	public String toaddproductinfo() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"productinfo"+FILESEPARATOR+"listproductinfo.action");
		if(msg!=null && CheckTool.getInstance().checkNull(msg)){
			this.setMessage(URLDecoder.decode(msg,"utf-8"));
		}
		ptypes = this.ptypeService.findByKeyword("");
		return "TOADD";
	}
	
	
	/**
	 * ADD PRODUCT-INFO
	 * @return
	 * @throws RuntimeException
	 */
	public String addproductinfo() throws RuntimeException{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"productinfo"+FILESEPARATOR+"listproductinfo.action");
		if(barcode!=null && barcode.trim()!="" && ptype!=null && pdesc!=null && pdesc.trim()!="")
		{	
			//检查 barcode 是否被使用
			ProductInfo check = this.productinfoService.findByBaecode(barcode);
			if(check!=null){
				this.setMessage("此产品编码已被使用!");
				return INPUT;
			}
			//Ptype ptype2 = this.ptypeService.finybyName(ptype);
			Ptype ptype2 = this.ptypeService.get(Ptype.class, ptype);
			if(ptype2==null){
				this.setMessage("没有此类型");
				return INPUT;
			}
			productinfo = new ProductInfo(ptype2, barcode, pdesc);
			if(pbrand!=null && !pbrand.trim().equals("")){
				productinfo.setPbrand(pbrand);
			}
			if(pcolor!=null && !pcolor.trim().equals("")){
				productinfo.setPcolor(pcolor);
			}
			productinfo.setSprice(sprice);
			productinfo.setMprice(mprice);
			if(introduction!=null && !introduction.trim().equals("")){
				productinfo.setIntroduction(introduction);
			}
			this.productinfoService.add(productinfo);
			this.setMessage(SUCCMESSAGE);
			return SUCCESS;
		}
		this.setMessage(NECESSARY);
		return INPUT;
	}
	
	/**
	 * ADD PRODUCT-INFO Ajax
	 * @return
	 * @throws RuntimeException
	 * @throws Exception 
	 */
	public void addproductinfoAjax() throws RuntimeException, Exception{
		CheckTool checkTool = CheckTool.getInstance();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if(ptype==null){
			printWriter.write("error:系统错误,请选择产品类型!");
			printWriter.flush();
			printWriter.close();
			return;
		}
		if(checkTool.checkNull(barcode,pdesc)){
			//Ptype ptype2 = ptypeService.get(Ptype.class, Integer.valueOf(ptype));
			Ptype ptype2 = ptypeService.get(Ptype.class, ptype);
			productinfo = this.productinfoService.findByBaecode(barcode);
			if(productinfo!=null){
				printWriter.write("error01："+barcode+" 已被使用!");
				printWriter.flush();
				printWriter.close();
				return;
			}
			productinfo = new ProductInfo(ptype2, barcode, pdesc);
			if(pbrand!=null && !pbrand.trim().equals("")){
				productinfo.setPbrand(pbrand);
			}
			if(pcolor!=null && !pcolor.trim().equals("")){
				productinfo.setPcolor(pcolor);
			}
			productinfo.setSprice(sprice);
			productinfo.setMprice(mprice);
			if(introduction!=null && !introduction.trim().equals("")){
				productinfo.setIntroduction(introduction);
			}
			this.productinfoService.add(productinfo);
			printWriter.write("成功添加:"+barcode+"-"+pdesc);
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
	 * DELETE ProductInfos BY AJAX
	 * @throws Exception
	 */
	public void delproductinfoAjax() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if(ids.length>0){
			List<ProductInfo> infos  = new ArrayList<ProductInfo>();
			for (int i = 0; i < ids.length; i++) {
				//int id = Integer.valueOf(ids[i]);
				int id = ids[i];
				ProductInfo info = this.productinfoService.get(ProductInfo.class, id);
				if(info==null){
					printWriter.write("error:"+ERRORMESSAGE);
					printWriter.flush();
					printWriter.close();
					return;
				}
				info.setStatus(0);
				infos.add(info);
			}
			try{
				//this.productinfoService.deleteManyObjects(infos);
				this.productinfoService.updateManyObjects(infos);
				printWriter.write(SUCCMESSAGE);
				printWriter.flush();
				printWriter.close();
				return;
			}catch (Exception e) {
				printWriter.write("error:"+e.getMessage());
				printWriter.flush();
				printWriter.close();
				return;
			}
		}
		printWriter.write("error:"+"NO ID CHECKED!");
		printWriter.flush();
		printWriter.close();
		return;
	}
	
	/**
	 * 跳转至import页面
	 * @return
	 * @throws Exception
	 */
	public String toimportpage() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"productinfo"+FILESEPARATOR+"listproductinfo.action");
		return SUCCESS;
	}
	
	/**
	 * IMPORT PRODUCT-INFO FROM EXCEL
	 * @return
	 * @throws RuntimeException
	 * @throws BiffException
	 * @throws Exception
	 */
	public String importproductinfos() throws Exception{
		List<String> errormegs = new ArrayList<String>();
		List<String> succemegs = new ArrayList<String>();
		List<ProductInfo> importlist = new ArrayList<ProductInfo>();
		String returnmegs = "";
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"productinfo"+FILESEPARATOR+"listproductinfo.action");
		if(file!=null && file.exists()){
			Workbook workbook = Workbook.getWorkbook(file); 
			Sheet sheet = workbook.getSheet(0);
			boolean flag = true;
			for (int i = 1; i < sheet.getRows(); i++) {
				String barcode = sheet.getCell(0 , i).getContents();
				String ptype = sheet.getCell(1 , i).getContents();
				String pdesc = sheet.getCell(2 , i).getContents();
				String pbrand = sheet.getCell(3 , i).getContents();
				String pcolor = sheet.getCell(4 , i).getContents();
				String pricestr = sheet.getCell(5 , i).getContents();
				String mpricestr = sheet.getCell(6 , i).getContents();
				String itype = sheet.getCell(7 , i).getContents();
				if(barcode!=null && barcode.trim()!="" && ptype!=null && ptype.trim()!="" && pdesc!=null && pdesc.trim()!="" ){
					ProductInfo p1 = this.productinfoService.findByBaecode(barcode);
					if(p1!=null){
						errormegs.add(i+ " 行的产品条码 "+barcode+" 已被用!");
						flag = false;
					}else{
						Ptype pt1 = this.ptypeService.finybyName(ptype);
						if(pt1==null){
							Ptype ptype2 = new Ptype(ptype, Integer.valueOf(itype));
							this.ptypeService.add(ptype2);
							succemegs.add("创建产品类型"+ptype);
							pt1 = ptype2;
						}
						ProductInfo newobj = new ProductInfo(pt1, barcode, pdesc);
						Double sp = 0.0;
						Double mp = 0.0;
						if(!pbrand.equals("")){
							newobj.setPbrand(pbrand);
						}
						if(!pcolor.equals("")){
							newobj.setPcolor(pcolor);
						}
						if(!pricestr.trim().equals("")){
							sp = Double.valueOf(pricestr);
						}
						newobj.setSprice(sp);
						if(!mpricestr.equals("")){
							mp = Double.valueOf(mpricestr);
						}
						newobj.setMprice(mp);
						succemegs.add(i+" 行 产品添加成功 "+barcode);
						importlist.add(newobj);
					}
				}else{
					errormegs.add( i +" 行 * 栏目列不能为空！");
					flag = false;
				}
			}

			if(flag){
				this.productinfoService.addManyObjects(importlist);
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
	public String toupdproductinfo() throws RuntimeException{
		String barcode = request.getParameter("barcode");
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"productinfo"+FILESEPARATOR+"listproductinfo.action");
		if(barcode!=null && barcode.trim()!=""){
			productinfo = this.productinfoService.findByBaecode(barcode);
			/**
			 * 使用 OSS 服务器
			 */
//			if(productinfo.getPicture()!=null){
//				Picture picture = this.pictureService.get(Picture.class, productinfo.getPicture());
//				if(picture!=null){
//					OSSClient ossClient = new OSSClient(ENDPOINT,ACCESSKEYID, ACCESSKEYSECRET);
//					Date expiration = new Date(new Date().getTime() + 3600 * 1000);
//					mainpictureurl = ossClient.generatePresignedUrl(BUCKETNAME, picture.getFilename(), expiration).toString();
//				}
//			}
			/*
			 * 
			 *不使用 OSS
			 */ 
			if(productinfo.getPicture()!=null){
				Picture picture = this.pictureService.get(Picture.class, productinfo.getPicture());
				if(picture!=null){
					mainpictureurl = picture.getSmall();
				}
			}
			if(productinfo!=null){
				ptypes = this.ptypeService.findByKeyword("");
				return "TOUPD";
			}else{
				this.setMessage(ERRORMESSAGE);
				return INPUT;
			}
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	/**
	 * UPDATE PRODUCT-INFO
	 * @return
	 * @throws RuntimeException
	 */
	public String updproductinfo() throws RuntimeException{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"productinfo"+FILESEPARATOR+"listproductinfo.action");
		if(barcode!=null && barcode.trim()!="" && ptype!=null && pdesc!=null && pdesc.trim()!="")
		{
			productinfo = this.productinfoService.findByBaecode(barcode);
			//Ptype ptype2 = this.ptypeService.finybyName(ptype);
			Ptype ptype2 = this.ptypeService.get(Ptype.class, ptype);
			if(productinfo!=null){
				productinfo.setItype(ptype2.getItype());
				productinfo.setPtype(ptype2.getTypeName());
				//productinfo.setType(ptype2);
				productinfo.setPdesc(pdesc);
				if(pbrand!=null && !pbrand.trim().equals("")){
					productinfo.setPbrand(pbrand);
				}
				if(pcolor!=null && !pcolor.trim().equals("")){
					productinfo.setPcolor(pcolor);
				}
				productinfo.setSprice(sprice);
				productinfo.setMprice(mprice);
				if(introduction!=null && !introduction.trim().equals("")){
					productinfo.setIntroduction(introduction);
				}
				productinfo.setUpdateTime(DateTool.getInstance().DateToPattern2(new Date()));
				this.productinfoService.update(productinfo);
				if(productinfo.getPicture()!=null){
					Picture picture = this.pictureService.get(Picture.class, productinfo.getPicture());
					if(picture!=null){
						mainpictureurl = picture.getUrl();
					}
				}
				this.setMessage(SUCCMESSAGE);
				ptypes = this.ptypeService.findByKeyword("");
				return SUCCESS;
			}else{
				productinfo = new ProductInfo(ptype2, barcode, pdesc);
				if(pbrand!=null && pbrand.trim()!=""){
					productinfo.setPbrand(pbrand);
				}
				if(pcolor!=null && pcolor.trim()!=""){
					productinfo.setPcolor(pcolor);
				}
				productinfo.setSprice(sprice);
				productinfo.setMprice(mprice);
				if(introduction!=null && !introduction.trim().equals("")){
					productinfo.setIntroduction(introduction);
				}
				this.productinfoService.add(productinfo);
				this.setMessage("创建产品:" +barcode+" - "+pdesc);
				ptypes = this.ptypeService.findByKeyword("");
				return SUCCESS;
			}
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	
	
	/**
	 * FIND BY KEYWORD / LIST PRODUCT-INFOS
	 * @return
	 * @throws RuntimeException
	 */
	public String listproductinfo() throws RuntimeException{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"infofunction.jsp");
		if(keyword!=null && !keyword.trim().equals("")){
			productinfos = this.productinfoService.findByKeyword(keyword);
		}else{
			productinfos = this.productinfoService.findByKeyword("");
		}
		ptypes = this.ptypeService.findByKeyword("");
		return "LIST";
	}
	
	/**
	 * 导出 产品EXCEL表
	 * @return
	 * @throws Exception
	 */
	public String createExcel() throws Exception{
		if(keyword==null){
			keyword = "";
		}
		productinfos = this.productinfoService.findByKeyword(keyword);
		ByteOutputStream outputStream = new ByteOutputStream();
		WritableWorkbook workbook = Workbook.createWorkbook(outputStream);
		WritableSheet sheet = workbook.createSheet("sheet1", 0);
		int row = 0;
		Label label01 = new Label(0, row, "BARCODE *");
		Label label02 = new Label(1, row, "PTYPE *");
		Label label03 = new Label(2, row, "DESCRATION *");
		Label label04 = new Label(3, row, "PBRAND");
		Label label05 = new Label(4, row, "PCOLOR");
		Label label06 = new Label(5, row, "PRICE 注意格式");
		Label label07 = new Label(6, row, "MIN-PRICE 注意格式");
		Label label08 = new Label(7, row, "ITYPE * 货品性质 0-不记库存 1-配件（没有imei） 2-手机或电脑（有imei）");
		sheet.addCell(label01);
		sheet.addCell(label02);
		sheet.addCell(label03);
		sheet.addCell(label04);
		sheet.addCell(label05);
		sheet.addCell(label06);
		sheet.addCell(label07);
		sheet.addCell(label08);
		row++;
		for (ProductInfo productInfo : productinfos) {
			Label label1 = new Label(0, row, productInfo.getBarcode());
			Label label2 = new Label(1, row, productInfo.getPtype());
			Label label3 = new Label(2, row, productInfo.getPdesc());
			Label label4 = new Label(3, row, productInfo.getPbrand());
			Label label5 = new Label(4, row, productInfo.getPcolor());
			Number label6 = new Number(5, row, productInfo.getSprice());
			Number label7 = new Number(6, row, productInfo.getMprice());
			Number label8 = new Number(7, row, Double.valueOf(productInfo.getItype()));
			row++;
			sheet.addCell(label1);
			sheet.addCell(label2);
			sheet.addCell(label3);
			sheet.addCell(label4);
			sheet.addCell(label5);
			sheet.addCell(label6);
			sheet.addCell(label7);
			sheet.addCell(label8);
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
	
	
	public String printbarcode() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"productinfo"+FILESEPARATOR+"listproductinfo.action");
		if(barcode!=null && !barcode.trim().equals("")){
			productinfo = this.productinfoService.findByBaecode(barcode);
			if(productinfo!=null){
				return SUCCESS;
			}
		}
		this.setMessage("产品BARCODE无效");
		return INPUT;
	}	
	
	/**
	 * FIND BY KEYWORD / LIST PRODUCT-PRICE
	 * @return
	 * @throws RuntimeException
	 */
	public String listproductprice() throws RuntimeException{
		String barcode = request.getParameter("barcode");
		String ptype = request.getParameter("ptype");
		String pdesc = request.getParameter("pdesc");
		String pbrand = request.getParameter("pbrand");
		String pcolor = request.getParameter("pcolor");
		if(barcode==null){
			barcode = "";
		}
		if(ptype==null){
			ptype = "";
		}
		if(pdesc==null){
			pdesc = "";
		}
		if(pbrand==null){
			pbrand = "";
		}
		if(pcolor==null){
			pcolor = "";
		}
		productinfos = this.productinfoService.findByKeyword(pdesc);
		return "LIST";
	}
	
	/**
	 * CREATE PRODUCT-PRICE EXCEL
	 * @return
	 * @throws RuntimeException
	 * @throws Exception
	 */
	
	public String createpriceexcel() throws RuntimeException, Exception{
		String barcode = request.getParameter("barcode");
		String ptype = request.getParameter("ptype");
		String pdesc = request.getParameter("pdesc");
		String pbrand = request.getParameter("pbrand");
		String pcolor = request.getParameter("pcolor");
		if(barcode==null){
			barcode = "";
		}
		if(ptype==null){
			ptype = "";
		}
		if(pdesc==null){
			pdesc = "";
		}
		if(pbrand==null){
			pbrand = "";
		}
		if(pcolor==null){
			pcolor = "";
		}
		productinfos = this.productinfoService.findByKeyword(pdesc);
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		os.reset();
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		WritableSheet sheet = workbook.createSheet("价格", 0);
		
		WritableCellFormat format = new WritableCellFormat();
		format.setAlignment(jxl.format.Alignment.CENTRE);

		for (int i=0;i<productinfos.size();i++) {
			ProductInfo p = productinfos.get(i);
			Label label1 = new Label(0,i+1,p.getBarcode());
			Label label2 = new Label(1,i+1,p.getPdesc());
			if(p.getSprice()!=null){
				Number number3 = new Number(2,i+1,p.getSprice());
				sheet.addCell(number3);
			}else{
				Label label3 = new Label(2,i+1,"未设置");
				sheet.addCell(label3);
			}
			if(p.getMprice()!=null){
				Number number4 = new Number(3,i+1,p.getMprice());
				sheet.addCell(number4);
			}else{
				Label label4 = new Label(3,i+1,"未设置");
				sheet.addCell(label4);
			}
			sheet.addCell(label1);
			sheet.addCell(label2);
			
		}
		
		Label labe01 = new Label(0, 0, "BARCODE",format);
		Label labe02 = new Label(1, 0, "产品名称",format);
		Label labe03 = new Label(2, 0, "售价",format);
		Label labe04 = new Label(3, 0, "最低价",format);
		sheet.addCell(labe01);
		sheet.addCell(labe02);
		sheet.addCell(labe03);
		sheet.addCell(labe04);
		
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
		try {
			this.inputStream = new ByteArrayInputStream(os.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			os.close();
		}
		return SUCCESS;
	}
	
	/**
	 * UPDATE PRICE FROM EXCEL
	 * @return
	 * @throws Exception 
	 * @throws BiffException 
	 */
	public String updatepricefromexcel() throws RuntimeException, BiffException, Exception{
		List<String> errormegs = new ArrayList<String>();
		List<String> succemegs = new ArrayList<String>();
		List<ProductInfo> updatelist = new ArrayList<ProductInfo>();
		String returnmegs = "";
		if(file!=null && file.exists()){
			Workbook workbook = Workbook.getWorkbook(file); 
			Sheet sheet = workbook.getSheet(0);
			boolean flag = true;
			for (int i = 1; i < sheet.getRows(); i++) {
				String barcode = sheet.getCell(0 , i).getContents();
				String pricestr = sheet.getCell(2 , i).getContents();
				String mpricestr = sheet.getCell(3 , i).getContents();
				if(barcode!=null && barcode.trim()!=""){
					ProductInfo p1 = this.productinfoService.findByBaecode(barcode);
					if(pricestr!=null && pricestr.trim()!=""){
						Double price = Double.valueOf(pricestr);
						p1.setSprice(price);
					}else{
						errormegs.add( i +" ROW SALE-PRICE CAN NOT BE NULL!");
						flag = false;
					}
					if(mpricestr!=null && mpricestr.trim()!=""){
						Double mprice = Double.valueOf(mpricestr);
						p1.setMprice(mprice);
					}else{
						errormegs.add( i +" ROW MIN-PRICE CAN NOT BE NULL!");
						flag = false;
					}
					p1.setUpdateTime(DateTool.getInstance().DateToPattern2(new Date()));
					updatelist.add(p1);
					succemegs.add(i+" ROW UPDATE SUCCESS!");
				}else{
					errormegs.add( i +" ROW * COLUM CAN NOT BE NULL!");
					flag = false;
				}
			}

			if(flag){
				this.productinfoService.updateManyObjects(updatelist);
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
	 * 设置商品上架
	 * 传入ids，updatemany
	 * @throws Exception
	 */
	public void setallup() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if(ids!=null){
			productinfos = this.productinfoService.findByKeyword("");
			List<ProductInfo> list = new ArrayList<ProductInfo>();
			for (int i = 0; i < ids.length; i++) {
				for (int j = 0; j < productinfos.size(); j++) {
					if(ids[i].equals(productinfos.get(j).getId())){
						ProductInfo p = productinfos.get(j);
						if(p!=null){
							p.setSaleornot(1);
							list.add(p);
						}
					}
				}
			}
			this.productinfoService.updateManyObjects(list);
			printWriter.write(SUCCMESSAGE);
			printWriter.flush();
			printWriter.close();
			return;
		}
		printWriter.write(ERRORMESSAGE);
		printWriter.flush();
		printWriter.close();
		return;
	} 
	/**
	 * 设置商品下架
	 * 传入ids，updatemany
	 * @throws Exception
	 */
	public void setalldown() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if(ids!=null){
			productinfos = this.productinfoService.findByKeyword("");
			List<ProductInfo> list = new ArrayList<ProductInfo>();
			for (int i = 0; i < ids.length; i++) {
				for (int j = 0; j < productinfos.size(); j++) {
					if(ids[i].equals(productinfos.get(j).getId())){
						ProductInfo p = productinfos.get(j);
						if(p!=null){
							p.setSaleornot(0);
							list.add(p);
						}
					}
				}
			}
			this.productinfoService.updateManyObjects(list);
			printWriter.write(SUCCMESSAGE);
			printWriter.flush();
			printWriter.close();
			return;
		}
		printWriter.write(ERRORMESSAGE);
		printWriter.flush();
		printWriter.close();
		return;
	}
	
	
	
	public ProductInfo getProductinfo() {
		return productinfo;
	}
	public void setProductinfo(ProductInfo productinfo) {
		this.productinfo = productinfo;
	}
	public List<ProductInfo> getProductinfos() {
		return productinfos;
	}
	public void setProductinfos(List<ProductInfo> productinfos) {
		this.productinfos = productinfos;
	}
	public List<Object[]> getObjects() {
		return objects;
	}
	public void setObjects(List<Object[]> objects) {
		this.objects = objects;
	}
	public List<Ptype> getPtypes() {
		return ptypes;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public void setPtypes(List<Ptype> ptypes) {
		this.ptypes = ptypes;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Integer[] getIds() {
		return ids;
	}
	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getMainpictureurl() {
		return mainpictureurl;
	}

	public void setMainpictureurl(String mainpictureurl) {
		this.mainpictureurl = mainpictureurl;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getPtype() {
		return ptype;
	}

	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}

	public String getPdesc() {
		return pdesc;
	}

	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}
	public String getPcolor() {
		return pcolor;
	}

	public void setPcolor(String pcolor) {
		this.pcolor = pcolor;
	}

	public String getPbrand() {
		return pbrand;
	}

	public void setPbrand(String pbrand) {
		this.pbrand = pbrand;
	}

	public double getSprice() {
		return sprice;
	}

	public void setSprice(double sprice) {
		this.sprice = sprice;
	}

	public double getMprice() {
		return mprice;
	}

	public void setMprice(double mprice) {
		this.mprice = mprice;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
