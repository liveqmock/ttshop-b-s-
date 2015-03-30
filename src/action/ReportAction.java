package action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import pojo.SaleRecordList;
import tools.DateTool;

@SuppressWarnings("serial")
public class ReportAction extends BaseAction {
	private List<Object[]> list;
	private String keyword;
	private String begindate;
	private String enddate;
	private List<SaleRecordList> recordLists;
	private List<Object[]> objects;
	private List<Object[]> todaysale;
	private List<Object[]> monthsale;
	private List<Object[]> yearsale;
	private List<Object[]> salecost;
	private List<Object[]> innotestatement;
	private List<Object[]> innoteproductstatement;
	private List<Object[]> typestatement;
	private List<Object[]> productstatement;
	private List<Object[]> cusstatement;
	private List<Object[]> returnsalestatement;
	private List<Object[]> returnsaleliststatement;
	private double totalamount;
	private double totalcost;
	private int totalquantitty;
	private double totalreturnamount;
	private int totalreturnquantitty;
//	/**
//	 * 销售明细(旧方法,已经不用)
//	 * @return
//	 * @throws Exception
//	 */
//	public String saleImeiReport() throws Exception{
//		User user = (User) request.getSession().getAttribute("user");
//		if (user==null) {
//			return LOGIN;
//		}
//		if (keyword==null) {
//			keyword = "";
//		}
//		String begin = DateTool.getInstance().DateToPattern2(new Date());
//		String end = DateTool.getInstance().DateToPattern2(new Date());
//		if (begindate!=null && !begindate.trim().equals("")) {
//			begin = begindate +" 00:00:00";
//		}
//		if (enddate!=null && !enddate.trim().equals("")) {
//			end = enddate +" 59:59:59";
//		}
//		list = this.saleListService.listRecordAndDetailByInvoiceNo(keyword, begin, end);
//		return SUCCESS;
//	}
	
	/**
	 *  销售统计报表
	 * @return
	 * @throws Exception
	 */
	public void daytodaySaleReportAjax() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		Date date = new Date();
		String today1 = DateTool.getInstance().DateToPattern1(date)+" 00:00:00";
		String today2 = DateTool.getInstance().DateToPattern1(date)+" 59:59:59";
		String month1 = DateTool.getInstance().DateToMonth(date).get(0);
		String month2 = DateTool.getInstance().DateToMonth(date).get(1);
		String year1 = DateTool.getInstance().DateToYear(date).get(0);
		String year2 = DateTool.getInstance().DateToYear(date).get(1);
		//System.out.println(month1 +"--"+month2);
		todaysale = this.saleService.sumTotalBydateNoGroup("", today1, today2);
		monthsale = this.saleService.sumTotalBydateNoGroup("", month1, month2);
		yearsale = this.saleService.sumTotalBydateNoGroup("", year1, year2);
		//System.out.println(todaysale.get(0)[0]);
		String json="";
		if(todaysale==null || todaysale.size()<=0 || todaysale.get(0)[0]==null || todaysale.get(0)[1]==null){
			json+= "{\"todaysalequantity\":"+0+",";
			json+="\"todaysaleamount\":"+0.0+",";
		}else{
			json+= "{\"todaysalequantity\":"+todaysale.get(0)[0]+",";
			json+="\"todaysaleamount\":"+todaysale.get(0)[1]+",";
		}
		if(monthsale==null || monthsale.size()<=0 || monthsale.get(0)[0]==null){
			json+="\"monthsalequantity\":"+0+",";
			json+="\"monthsaleamount\":"+0.0+",";
		}else{
			json+="\"monthsalequantity\":"+monthsale.get(0)[0]+",";
			json+="\"monthsaleamount\":"+monthsale.get(0)[1]+",";
		}
		if(yearsale==null || yearsale.size()<=0 || yearsale.get(0)[0]==null ){
			json+="\"yearsalequantity\":"+0+",";
			json+="\"yearsaleamount\":"+0.0+"}";
		}else{
			json+="\"yearsalequantity\":"+yearsale.get(0)[0]+",";
			json+="\"yearsaleamount\":"+yearsale.get(0)[1]+"}";
		}
		printWriter.write(json);
		printWriter.flush();
		printWriter.close();
	}
	
	/**
	 *  销售统计报表
	 * @return
	 * @throws Exception
	 */
	public String daytodaySaleReport() throws Exception{
		return SUCCESS;
	}
	
	/**
	 *  销售统计报表 - byproduct
	 * @return
	 * @throws Exception
	 */
	public String daytodaySaleReportByproduct() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"reportfunction.jsp");
		if(keyword==null){
			keyword = "s-";
		}
		if(begindate==null || enddate==null){
			begindate = DateTool.getInstance().DateToPattern2(new Date());
			enddate = DateTool.getInstance().DateToPattern2(new Date());
		}
		begindate = begindate+" 00:00:00";
		enddate = enddate+" 59:59:59";
		productstatement = this.saleService.sumTotalBydateByProduct(keyword, begindate, enddate);
		begindate= begindate.substring(0, 10);
		enddate= enddate.substring(0, 10);
		return SUCCESS;
	}
	
	/**
	 *  销售统计报表 -bytype
	 * @return
	 * @throws Exception
	 */
	public String daytodaySaleReportBytype() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"reportfunction.jsp");
		if(keyword==null){
			keyword = "";
		}
		if(begindate==null || enddate==null){
			begindate = DateTool.getInstance().DateToPattern2(new Date());
			enddate = DateTool.getInstance().DateToPattern2(new Date());
		}
		begindate = begindate+" 00:00:00";
		enddate = enddate+" 59:59:59";
		typestatement = this.saleService.sumTotalBydateByType(keyword, begindate, enddate);
		begindate= begindate.substring(0, 10);
		enddate= enddate.substring(0, 10);
		return SUCCESS;
	}
	
	/**
	 *  销售统计报表 - bypay
	 * @return
	 * @throws Exception
	 */
	public String daytodaySaleReportBypay() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"reportfunction.jsp");
		if(keyword==null){
			keyword = "";
		}
		if(begindate==null || enddate==null){
			begindate = DateTool.getInstance().DateToPattern2(new Date());
			enddate = DateTool.getInstance().DateToPattern2(new Date());
		}
		begindate = begindate+" 00:00:00";
		enddate = enddate+" 59:59:59";
		objects = this.saleService.sumTotalBydate(keyword, begindate, enddate);
		begindate= begindate.substring(0, 10);
		enddate= enddate.substring(0, 10);
		totalamount = 0.0;
		totalquantitty = 0;
		for (Object[] object : objects) {
			Double temp = Double.valueOf(object[1].toString());
			Integer tempin = Integer.valueOf(object[0].toString());
			totalamount = totalamount + temp;
			totalquantitty = totalquantitty+tempin;
		}
		return SUCCESS;
	}
	
	/**
	 *  销售统计报表 - bycustomer
	 * @return
	 * @throws Exception
	 */
	public String daytodaySaleReportBycustomer() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"reportfunction.jsp");
		if(keyword==null){
			keyword = "";
		}
		if(begindate==null || enddate==null){
			begindate = DateTool.getInstance().DateToPattern2(new Date());
			enddate = DateTool.getInstance().DateToPattern2(new Date());
		}
		begindate = begindate+" 00:00:00";
		enddate = enddate+" 59:59:59";
		cusstatement = this.saleService.sumTotalBydateByCustomer(keyword, begindate, enddate);
		begindate= begindate.substring(0, 10);
		enddate= enddate.substring(0, 10);
		return SUCCESS;
	}
	
	/**
	 * 利润报表
	 * @return
	 * @throws Exception
	 */
	public String profitReport() throws Exception{
		if(keyword==null){
			keyword = "";
		}
		if(begindate==null || enddate==null){
			begindate = DateTool.getInstance().DateToPattern1(new Date());
			enddate = DateTool.getInstance().DateToPattern1(new Date());
		}
		begindate = begindate+" 00:00:00";
		enddate = enddate+" 59:59:59";
		//计算销售单
		objects = this.saleService.sumTotalBydate(keyword, begindate, enddate); //salerecord
		productstatement = this.saleService.sumTotalBydateByProduct(keyword, begindate, enddate);//salerecordlist
		returnsalestatement = this.returnSaleService.sumTotalReturnSale(begindate, enddate);//returnsale
		returnsaleliststatement = this.returnSaleListService.sumTotalreturnsalelist(begindate, enddate);//returnsalelist
		Map<String, Double> allcost = this.innoteListService.sumAllProductCost(begindate, enddate);
		totalcost = 0.0;
		if(productstatement!=null && productstatement.size()>0){
			for (int i = 0; i < productstatement.size(); i++) {
				String b = productstatement.get(i)[2].toString(); //barcode
				Integer q = 0;
				if(productstatement.get(i)[0]!=null){
					q = Integer.valueOf(productstatement.get(i)[0].toString());
				}
				Double avgcost = allcost.get(b);
				//System.out.println(avgcost);
//				try {
//					//avgcost = this.innoteListService.avgInnotePrice(b,enddate);  // 计算平均入货价
//				} catch (Exception e) {
//					this.setMessage("出错原因为有产品没有入库存！");
//					throw e;
//				}
				totalcost = totalcost+q*avgcost;
			}
		}
		totalamount = 0.0;
		totalquantitty = 0;
		if(objects!=null && objects.size()>0){
			for (Object[] object : objects) {
				Double temp = Double.valueOf(object[1].toString());
				Integer tempin = Integer.valueOf(object[0].toString());
				totalamount = totalamount + temp;
				totalquantitty = totalquantitty+tempin;
			}
		}
		//计算退货单总数及成本
		Double totalreturncost = 0.0;
		if(returnsaleliststatement!=null && returnsaleliststatement.size()>0){
			for (int j = 0; j < returnsaleliststatement.size(); j++) {
				String b = returnsaleliststatement.get(j)[2].toString();
//				Double returnavgcost = this.innoteListService.avgInnotePrice(b,enddate);
				Double returnavgcost = allcost.get(b);
				Object[]  objects = returnsaleliststatement.get(j);
				Integer tq = 0;
				if(objects[0]!=null&&objects[1]!=null){
					tq  = Integer.valueOf(objects[0].toString());
				}
				//System.out.println(returnavgcost+"--"+tq);
				totalreturncost = totalreturncost + tq*returnavgcost ;
			}
		}
		totalreturnamount = 0.0;
		totalreturnquantitty = 0;
		if(returnsalestatement!=null && returnsalestatement.size()>0){
			for (int j = 0; j < returnsalestatement.size(); j++) {
				if(returnsalestatement.get(j)!=null){
					Double temp = Double.valueOf(returnsalestatement.get(j)[1].toString());
					Integer tempin = Integer.valueOf(returnsalestatement.get(j)[0].toString());
					totalreturnamount = totalreturnamount + temp;
					totalreturnquantitty = totalreturnquantitty+tempin;
				}
			}
		}
		
		totalcost = totalcost-totalreturncost; //统计的销售成本减去已退货的产生的成本才是真正的销售成本*******
		begindate= begindate.substring(0, 10);
		enddate= enddate.substring(0, 10);
		return SUCCESS;
	}
	
	
	
	
	
	public List<Object[]> getList() {
		return list;
	}
	public void setList(List<Object[]> list) {
		this.list = list;
	}
	@Override
	public String getKeyword() {
		return keyword;
	}
	@Override
	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
	public List<SaleRecordList> getRecordLists() {
		return recordLists;
	}
	public void setRecordLists(List<SaleRecordList> recordLists) {
		this.recordLists = recordLists;
	}
	public List<Object[]> getObjects() {
		return objects;
	}
	public void setObjects(List<Object[]> objects) {
		this.objects = objects;
	}
	public List<Object[]> getTypestatement() {
		return typestatement;
	}
	public void setTypestatement(List<Object[]> typestatement) {
		this.typestatement = typestatement;
	}
	public double getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(double totalamount) {
		this.totalamount = totalamount;
	}
	public int getTotalquantitty() {
		return totalquantitty;
	}
	public void setTotalquantitty(int totalquantitty) {
		this.totalquantitty = totalquantitty;
	}
	public List<Object[]> getProductstatement() {
		return productstatement;
	}
	public void setProductstatement(List<Object[]> productstatement) {
		this.productstatement = productstatement;
	}
	public List<Object[]> getCusstatement() {
		return cusstatement;
	}
	public void setCusstatement(List<Object[]> cusstatement) {
		this.cusstatement = cusstatement;
	}
	public List<Object[]> getInnotestatement() {
		return innotestatement;
	}
	public void setInnotestatement(List<Object[]> innotestatement) {
		this.innotestatement = innotestatement;
	}
	public List<Object[]> getInnoteproductstatement() {
		return innoteproductstatement;
	}
	public void setInnoteproductstatement(List<Object[]> innoteproductstatement) {
		this.innoteproductstatement = innoteproductstatement;
	}
	public List<Object[]> getSalecost() {
		return salecost;
	}
	public void setSalecost(List<Object[]> salecost) {
		this.salecost = salecost;
	}
	public double getTotalcost() {
		return totalcost;
	}
	public void setTotalcost(double totalcost) {
		this.totalcost = totalcost;
	}

	public List<Object[]> getTodaysale() {
		return todaysale;
	}

	public void setTodaysale(List<Object[]> todaysale) {
		this.todaysale = todaysale;
	}
	

	public List<Object[]> getMonthsale() {
		return monthsale;
	}

	public void setMonthsale(List<Object[]> monthsale) {
		this.monthsale = monthsale;
	}

	public List<Object[]> getYearsale() {
		return yearsale;
	}

	public void setYearsale(List<Object[]> yearsale) {
		this.yearsale = yearsale;
	}

	

	public List<Object[]> getReturnsaleliststatement() {
		return returnsaleliststatement;
	}

	public void setReturnsaleliststatement(List<Object[]> returnsaleliststatement) {
		this.returnsaleliststatement = returnsaleliststatement;
	}

	public double getTotalreturnamount() {
		return totalreturnamount;
	}

	public void setTotalreturnamount(double totalreturnamount) {
		this.totalreturnamount = totalreturnamount;
	}

	public int getTotalreturnquantitty() {
		return totalreturnquantitty;
	}

	public void setTotalreturnquantitty(int totalreturnquantitty) {
		this.totalreturnquantitty = totalreturnquantitty;
	}

	public List<Object[]> getReturnsalestatement() {
		return returnsalestatement;
	}

	public void setReturnsalestatement(List<Object[]> returnsalestatement) {
		this.returnsalestatement = returnsalestatement;
	}
	
	
	
}
