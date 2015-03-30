package action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import pojo.PrintOption;
import tools.DateTool;

@SuppressWarnings("serial")
public class PrintOptionAction extends BaseAction {
	private PrintOption printOption;
	private List<PrintOption> printOptions;
	
	public String toupdate() throws Exception{
		printOptions = this.printOptionService.findOptionList("");
		if(printOptions!=null && printOptions.size()>0){
			printOption = printOptions.get(0);
			return SUCCESS;
		}
		return INPUT;
	}
	
	public void updateAjax() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printOption.setUpdatetime(DateTool.getInstance().DateToPattern2(new Date()));
		printOption.setStatus(1);
		try {
			this.printOptionService.update(printOption);
			request.getSession().setAttribute("printOption", printOption);
			printWriter.write("su");
			printWriter.flush();
			printWriter.close();
		} catch (Exception e) {
			printWriter.write("er");
			printWriter.flush();
			printWriter.close();
			e.printStackTrace();
		}
	}
	
	
	public PrintOption getPrintOption() {
		return printOption;
	}
	public void setPrintOption(PrintOption printOption) {
		this.printOption = printOption;
	}
	public List<PrintOption> getPrintOptions() {
		return printOptions;
	}
	public void setPrintOptions(List<PrintOption> printOptions) {
		this.printOptions = printOptions;
	}
	
	
	
}
