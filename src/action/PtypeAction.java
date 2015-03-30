package action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import pojo.Ptype;
import tools.DateTool;

@SuppressWarnings("serial")
public class PtypeAction extends BaseAction {
	private Ptype ptype;
	private List<Ptype> ptypes;
	private File file;
	private Integer[] ids;
	private Integer itype;
	private InputStream inputStream;
	/**
	 * ADD PTYPE
	 * @return
	 * @throws RuntimeException
	 * @throws Exception 
	 */
	public String addptype() throws Exception {
		String typename = request.getParameter("typename");
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"ptype"+FILESEPARATOR+"listptype.action");
		if(this.ptypeService.finybyName(typename)!=null){
			setMessage("SORRY~已被使用");
			return INPUT;
		}
		if(itype!=null && typename!=null && !typename.trim().equals("")){
			ptype = new Ptype(typename,itype);
			this.ptypeService.add(ptype);
			this.setMessage(SUCCMESSAGE);
			return SUCCESS;
		}
		this.setMessage(NECESSARY);
		return INPUT;
	}
	
	/**
	 * 跳转至import页面
	 * @return
	 * @throws Exception
	 */
	public String toimportpage() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"ptype"+FILESEPARATOR+"listptype.action");
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String importptypes() throws Exception{
		List<String> errormegs = new ArrayList<String>();
		List<String> succemegs = new ArrayList<String>();
		List<Ptype> importlist = new ArrayList<Ptype>();
		String returnmegs = "";
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"ptype"+FILESEPARATOR+"listptype.action");
		if(file!=null && file.exists()){
			Workbook workbook = Workbook.getWorkbook(file); 
			Sheet sheet = workbook.getSheet(0);
			boolean flag = true;
			for (int i = 1; i < sheet.getRows(); i++) {
				String itype = sheet.getCell(0 , i).getContents();
				String typeName = sheet.getCell(1 , i).getContents();
				if(itype!=null && itype.trim()!="" && typeName!=null && typeName.trim()!="" ){
					if(this.ptypeService.finybyName(typeName)!=null){
						errormegs.add(i+ "行的产品类型 "+typeName+" 已被用!");
						flag = false;
						//System.out.println("1"+flag);
					}else{
						Integer it = Integer.valueOf(itype);
						Ptype newobj = new Ptype(typeName, it);
						succemegs.add(i+" 行添加成功 "+typeName);
						importlist.add(newobj);
						//System.out.println("2"+flag);
					}
				}else{
					errormegs.add( i +" 行的 * 栏目不能为空!");
					flag = false;
				}
			}
			//System.out.println("3"+flag);
			if(flag){
				this.ptypeService.addManyObjects(importlist);
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
	 * ADD PTYPE AJAX
	 * @return
	 * @throws RuntimeException
	 */
	public void addptypeAjax() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		String typename = request.getParameter("typename");
		String itype = request.getParameter("itype");
		if(this.ptypeService.finybyName(typename)!=null){
			printWriter.write("error01:ID HAVE BEEN USED");
			printWriter.flush();
			printWriter.close();
			return;
		}
		if(itype!=null && typename!=null && !typename.trim().equals("") && !itype.trim().equals("")){
			ptype = new Ptype(typename,Integer.valueOf(itype));
			this.ptypeService.add(ptype);
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
	 * DELETE PTYPE AJAX
	 * @return
	 * @throws RuntimeException
	 */
	public void deleteptypeAjax() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if(ids.length>0){
			List<Ptype> list = new ArrayList<Ptype>();
			for (int i = 0; i < ids.length; i++) {
				Integer id = ids[i];
				ptype = this.ptypeService.get(Ptype.class, id);
				if(ptype==null){
					printWriter.write("error:"+ERRORMESSAGE);
					printWriter.flush();
					printWriter.close();
					return;
				}
				ptype.setStatus(0);
				list.add(ptype);
			}
			try {
				this.ptypeService.updateManyObjects(list);
//				this.ptypeService.deleteManyObjects(list);
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
	public String toupdptype() throws RuntimeException{
		String typeid = request.getParameter("typeid");
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"ptype"+FILESEPARATOR+"listptype.action");
		if(typeid!=null && typeid.trim()!=""){
			Integer tid = Integer.valueOf(typeid);
			ptype = this.ptypeService.load(Ptype.class, tid);
				if(ptype!=null){
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
	 * UPDATE PTYPE
	 * @return
	 * @throws RuntimeException
	 */
	public String updptype() throws RuntimeException{
		String typeid = request.getParameter("typeid");
		String typename = request.getParameter("typename");
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"ptype"+FILESEPARATOR+"listptype.action");
		if(itype!=null && typeid!=null && typeid.trim()!=""){
			Integer tid = Integer.valueOf(typeid);
			ptype = this.ptypeService.load(Ptype.class, tid);
			if(ptype!=null){
				ptype.setTypeName(typename);
				ptype.setItype(itype);
				ptype.setUpdateTime(DateTool.getInstance().DateToPattern2(new Date()));
				this.ptypeService.update(ptype);
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
	 * FIND BY KEYWORD / LIST PTYPES
	 * @return
	 * @throws RuntimeException
	 */
	public String listptype() throws RuntimeException{
		//String keyword = request.getParameter("keyword");
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"infofunction.jsp");
		if(keyword!=null && !keyword.trim().equals("")){
			ptypes = this.ptypeService.findByKeyword(keyword);
		}else{
			ptypes = this.ptypeService.findByKeyword("");
		}
		return "LIST";
	}
	
	
	public String createExcel() throws Exception{
		String keyword = request.getParameter("keyword");
		if(keyword!=null && !keyword.equals("")){
			ptypes = this.ptypeService.findByKeyword(keyword);
		}else{
			ptypes = this.ptypeService.findByKeyword("");
		}
		ByteOutputStream outputStream = new ByteOutputStream();
		WritableWorkbook workbook = Workbook.createWorkbook(outputStream);
		WritableSheet sheet = workbook.createSheet("sheet1", 0);
		int row = 0;
		Label label01 = new Label(0, row, "INVENT-TYPE*(0-非库存类型 1-库存类型 2-库存有IMEI)");
		Label label02 = new Label(1, row, "typeName*");
		sheet.addCell(label01);
		sheet.addCell(label02);
		row++;
		for (Ptype ptype : ptypes) {
			Number label1 = new Number(0, row, ptype.getItype());
			Label label2 = new Label(1, row, ptype.getTypeName());
			row++;
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
		response.setContentType("application/vn.ms-xls");
		response.setCharacterEncoding("utf-8");
		this.inputStream = new ByteArrayInputStream(outputStream.getBytes());
		return SUCCESS;
	}
	
	
	
	public Ptype getPtype() {
		return ptype;
	}

	public void setPtype(Ptype ptype) {
		this.ptype = ptype;
	}

	public List<Ptype> getPtypes() {
		return ptypes;
	}

	public void setPtypes(List<Ptype> ptypes) {
		this.ptypes = ptypes;
	}

	@Override
	public File getFile() {
		return file;
	}

	@Override
	public void setFile(File file) {
		this.file = file;
	}
	public Integer[] getIds() {
		return ids;
	}
	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	public Integer getItype() {
		return itype;
	}
	public void setItype(Integer itype) {
		this.itype = itype;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	

	
	
	
	
	
	
	
	

	
	
	
	
}
