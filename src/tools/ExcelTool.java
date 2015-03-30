package tools;

import pojo.ProductInfo;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;

public class ExcelTool implements ExcelToolFactory {
	
	public  WritableFont wf_title = new WritableFont(WritableFont.TIMES, 12,
            WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
            jxl.format.Colour.BLUE);
	public  WritableFont wf_text = new WritableFont(WritableFont.TIMES, 10,  
			WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
			jxl.format.Colour.BLACK);
	public  WritableCellFormat wcf_title = new WritableCellFormat(wf_title);
	public  WritableCellFormat wcf_text = new WritableCellFormat(wf_text);
	public  WritableCellFormat wcf = new WritableCellFormat();
	public  WritableCellFormat wcf_textwithborder = new WritableCellFormat(wf_text);
	public  WritableCellFormat border = new WritableCellFormat();
	public  WritableCellFormat noborder = new WritableCellFormat(wf_text);
	public  WritableCellFormat topline = new WritableCellFormat();
	public  WritableCellFormat underline = new WritableCellFormat();
	public  WritableCellFormat leftline = new WritableCellFormat();
	public  WritableCellFormat rightline = new WritableCellFormat();
	
	public ExcelTool createExcelTool() {
		ExcelTool excelTool = new ExcelTool();
		return excelTool;
	}

	public void init() throws Exception{
		wcf_title.setAlignment(jxl.format.Alignment.CENTRE);
		wcf_text.setAlignment(jxl.format.Alignment.CENTRE);
		wcf_textwithborder.setAlignment(jxl.format.Alignment.CENTRE);
		border.setAlignment(jxl.format.Alignment.CENTRE);
		wcf.setAlignment(jxl.format.Alignment.CENTRE);
		wcf.setBorder(Border.ALL,BorderLineStyle.THIN, Colour.BLACK);
		wcf_textwithborder.setBorder(Border.ALL,BorderLineStyle.THIN, Colour.BLACK);
		border.setBorder(Border.ALL,BorderLineStyle.THIN, Colour.BLACK);
		noborder.setAlignment(jxl.format.Alignment.CENTRE);
		topline.setBorder(Border.TOP,BorderLineStyle.MEDIUM, Colour.BLACK);
		underline.setBorder(Border.BOTTOM,BorderLineStyle.MEDIUM, Colour.BLACK);
		leftline.setBorder(Border.LEFT,BorderLineStyle.THIN, Colour.BLACK);
		rightline.setBorder(Border.RIGHT,BorderLineStyle.THIN, Colour.BLACK);
	}
	
	public void initDetailAndInvoiceSheet(WritableSheet detial,WritableSheet invoice,String invoiceno,String customer,String date) throws Exception{
		this.init();
		this.initInvoiceBorder(invoice);
		//detail sheet
		Label label0 = new Label(0, 0, invoiceno, wcf);
		Label label1 = new Label(0, 1, customer, wcf);
		Label label2 = new Label(0, 2, date, wcf);
		detial.addCell(label2);
		detial.addCell(label0);
		detial.addCell(label1);
		//invoice sheet
		{
		Label label30 = new Label(0, 0, "  ", wcf_title);
		invoice.addCell(label30);
		invoice.mergeCells(0, 0, 4, 0);
		Label label300 = new Label(0, 1, "客 户："+customer, wcf_text);	
		Label label301 = new Label(2, 1, "发 票："+invoiceno, wcf_text);
		invoice.mergeCells(2, 1, 4, 1);
		invoice.mergeCells(2, 2, 4, 2);
		Label label302 = new Label(2, 2, "日期："+date, wcf_text);
		invoice.addCell(label300);
		invoice.addCell(label301);
		invoice.addCell(label302);
		Label label310 = new Label(0, 3, "产品编号", wcf);	
		Label label311 = new Label(1, 3, "产品名称", wcf);	
		Label label312 = new Label(2, 3, "数量", wcf);	
		Label label313 = new Label(3, 3, "单价", wcf);	
		Label label314 = new Label(4, 3, "合计", wcf);
		invoice.addCell(label310);
		invoice.addCell(label311);
		invoice.addCell(label312);
		invoice.addCell(label313);
		invoice.addCell(label314);
		}
	}
	
	public void endformat(WritableSheet detial,WritableSheet invoice) throws Exception{
		for (int i = 0; i < detial.getColumns()+2; i++) {
			detial.setColumnView(i, 30);
		}
		detial.setColumnView(1, 10);
		for (int i = 0; i < detial.getRows(); i++) {
			detial.setRowView(i, 300);
		}
		for (int i = 0; i < invoice.getColumns(); i++) {
			invoice.setColumnView(i, 30);
		}
		for (int i = 0; i < 35; i++) {
			invoice.setRowView(i, 400);
		}
		//invoice sheet view setting
		{
			invoice.setRowView(0, 600);
			invoice.setColumnView(0, 15);
			invoice.setColumnView(1, 45);
			invoice.setColumnView(2, 8);
			invoice.setColumnView(3, 9);
			invoice.setColumnView(4, 10);
		}
		
	}
	
	//SET INVOICE DISCOUNT AND TOTAL
	public void setInvoiceFormula(WritableSheet invoice) throws Exception{
		invoice.mergeCells(0, 14, 1, 15);
		invoice.mergeCells(2, 14, 3, 14);
		invoice.mergeCells(2, 15, 3, 15);
		Label label311 = new Label(2, 14, "DISCOUNT:",wcf_textwithborder);
		Number number311 = new Number(4, 14, 0, wcf);
		Label label310 = new Label(2, 15, "TOTAL:",wcf_textwithborder);
		String formulastr = "SUM(E4:E15)";
		Formula f311 = new Formula(4, 15, formulastr, wcf);
		invoice.addCell(label311);
		invoice.addCell(number311);
		invoice.addCell(f311);
		invoice.addCell(label310);
		
	}
	
	//SET LINE
	public void setInvoiceLineAmount(WritableSheet invoice,int row,ProductInfo info,int quanity) throws Exception{
		int sheetrow = row+1;
		Label label301 = new Label(0, row , info.getBarcode(), wcf);
		Label label302 = new Label(1, row , info.getPdesc(), wcf);
		Number number303 = new Number(2, row, quanity, wcf);
		Number number304 = new Number(3, row, info.getSprice(), wcf);
		String formulastr = "C"+sheetrow+"*D"+sheetrow;
		Formula f = new Formula(4, row, formulastr,wcf);
		invoice.addCell(label301);
		invoice.addCell(label302);
		invoice.addCell(number303);
		invoice.addCell(number304);
		invoice.addCell(f);
	}
	
	//设置边框
	public void initInvoiceBorder(WritableSheet invoice) throws Exception{
		for (int i = 0; i < 5; i++) {
			for (int j = 4; j < 15; j++) {
				Label label = new Label(i,j,"",border);
				invoice.addCell(label);
			}
		}

	}
	
	public void backupexcel(WritableSheet sheet) throws Exception{
		for (int j = 0; j < sheet.getRows(); j++) {
			sheet.setRowView(j, 400);
		}
		for (int i = 0; i < sheet.getColumns(); i++) {
			sheet.setColumnView(i,20);
		}
	}
	
}
