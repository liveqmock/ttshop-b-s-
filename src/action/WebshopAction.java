package action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import pojo.Page;
import pojo.Picture;
import pojo.ProductInfo;
import pojo.WebOrder;
import pojo.WebOrderList;
import tools.DateTool;
import tools.JsonTool;

@SuppressWarnings("serial")
public class WebshopAction extends BaseAction {
	
	private List<ProductInfo> productInfos;
	private List<Picture> pictures;
	private Map<Integer, String> picturemap;
	private Page page;
	private Integer currentPage;
	private Integer id;
	private ProductInfo productInfo;
	private String url;
	private WebOrder webOrder;
	private List<WebOrderList> webOrderLists;
	private Integer addp_id;
	/**
	 * 转去商城页面
	 * @return
	 * @throws Exception
	 */
	public String towebshop() throws Exception{
		if(keyword!=null && !keyword.trim().equals("")){
			int allrow =  this.productinfoService.findWebShopList(keyword).size();
			if(page==null){
				page = new Page(1, 8, allrow);
			}else{
				page.setAllRows(allrow);
				page.setMaxResult(8);
			}
			productInfos = this.productinfoService.findWebShopList(keyword,page.getFirstResult(),page.getMaxResult());
		}else{
			int allrow =  this.productinfoService.findWebShopList("").size();
			if(page==null){
				page = new Page(1, 8, allrow);
			}else{
				page.setAllRows(allrow);
				page.setMaxResult(8);
			}
			productInfos = this.productinfoService.findWebShopList("",page.getFirstResult(),page.getMaxResult());
		}
		return SUCCESS;
	}
	
	/**
	 * 用ajax加载更多的product信息
	 * @throws Exception
	 */
	public void moreproduct() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		String json="";
		if(keyword!=null && !keyword.trim().equals("")){
			int allrow =  this.productinfoService.findWebShopList(keyword).size();
			if(currentPage==null){
				page = new Page(1, 8, allrow);
			}else{
				page = new Page(currentPage, 8, allrow);
			}
			productInfos = this.productinfoService.findWebShopList(keyword,page.getFirstResult(),page.getMaxResult());
			json = JsonTool.getInstance().createWebshopProduct(productInfos);
			printWriter.write(json);
			printWriter.flush();
			printWriter.close();
			return;
		}else{
			int allrow =  this.productinfoService.findWebShopList("").size();
			if(currentPage==null){
				page = new Page(1, 8, allrow);
			}else{
				page = new Page(currentPage, 8, allrow);
			}
			productInfos = this.productinfoService.findWebShopList("",page.getFirstResult(),page.getMaxResult());
			json = JsonTool.getInstance().createWebshopProduct(productInfos);
			printWriter.write(json);
			printWriter.flush();
			printWriter.close();
			return;
		}
	}
	
	/**
	 * 获取商品详细信息
	 * @throws Exception
	 */
	public void getonedetail() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if(id!=null){
			StringBuffer buffer= new StringBuffer();
			productInfo = this.productinfoService.get(ProductInfo.class, id);
			if(productInfo!=null){
				List<Picture> pictures = this.pictureService.listAllPicture(productInfo.getBarcode(), 0, 20);
				System.out.println(pictures.size());
				buffer.append("{");
				buffer.append("\"id\":\""+productInfo.getId()+"\"");
				buffer.append(",");
				buffer.append("\"pdesc\":\""+productInfo.getPdesc()+"\"");
				buffer.append(",");
				buffer.append("\"introduction\":\""+productInfo.getIntroduction()+"\"");
				buffer.append(",");
				buffer.append("\"sprice\":\""+productInfo.getSprice()+"\"");
				buffer.append(",");
				buffer.append("\"images\":[");
				if(!pictures.isEmpty()){
					for (int i = 0; i < pictures.size(); i++) {
						buffer.append("\"");
						buffer.append(pictures.get(i).getUrl());
						buffer.append("\"");
						if(i<=pictures.size()-1){
							buffer.append(",");
						}
					}
				}
				buffer.append("]");
				buffer.append("}");
				printWriter.write(buffer.toString());
				printWriter.flush();
				printWriter.close();
				return;
			}
			printWriter.write(ERRORMESSAGE);
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
	 * 图片浏览器
	 * @return
	 * @throws Exception
	 */
	public String viewer() throws Exception{
		if(url!=null&&!url.trim().equals("")){
			return SUCCESS;
		}else{
			url = "./images/im.png";
			return SUCCESS;
		}
	}
	
	/**
	 * 添加到购物车
	 * @throws Exception
	 */
	public void addToCar() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter(); 
		if(request.getSession().getAttribute("car")!=null){
			webOrder  = (WebOrder) request.getSession().getAttribute("car");
			webOrderLists = webOrder.getLists();
			if(addp_id!=null){
				for (WebOrderList list : webOrderLists) {
					System.out.println(addp_id);
					if(list.getProductInfo().getId().equals(addp_id)){
						printWriter.write("该商品已添加到购物车");
						printWriter.flush();
						printWriter.close();
						return;
					}
				}
				productInfo = this.productinfoService.get(ProductInfo.class, addp_id);
				if(productInfo!=null){
					WebOrderList webOrderList = new WebOrderList(webOrder.getOrder_no(), productInfo, productInfo.getSprice(), 1, 0.0);
					webOrderLists.add(webOrderList);
					webOrder.setLists(webOrderLists);
					request.getSession().setAttribute("car", webOrder);
					printWriter.write("添加成功");
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
		}else{
			webOrder = new WebOrder();
			webOrder.setOrder_no(DateTool.getInstance().DateToPattern4(new Date()));
			webOrderLists = new ArrayList<WebOrderList>();
			if(addp_id!=null){
				productInfo = this.productinfoService.get(ProductInfo.class, addp_id);
				if(productInfo!=null){
					WebOrderList webOrderList = new WebOrderList(webOrder.getOrder_no(), productInfo, productInfo.getSprice(), 1, 0.0);
					webOrderLists.add(webOrderList);
					webOrder.setLists(webOrderLists);
					request.getSession().setAttribute("car", webOrder);
					printWriter.write("添加成功");
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
		printWriter.write(ERRORMESSAGE);
		printWriter.flush();
		printWriter.close();
		return;
	}
	
	public void getCarInfo() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		//String json = "";
		if(request.getSession().getAttribute("car")!=null){
			webOrder  = (WebOrder) request.getSession().getAttribute("car");
			StringBuffer buffer = new StringBuffer();
			buffer.append("[");
			if(webOrder.getLists()!=null && !webOrder.getLists().isEmpty()){
				for (int i = 0; i < webOrder.getLists().size(); i++) {
					buffer.append("{");
					ProductInfo info = webOrder.getLists().get(i).getProductInfo();
					System.out.println(info);
					buffer.append("\"desc\":");
					buffer.append("\""+info.getPdesc()+"\"");
					buffer.append(",");
					buffer.append("\"introduction\":");
					buffer.append("\""+info.getIntroduction()+"\"");
					buffer.append(",");
					buffer.append("\"sprice\":");
					buffer.append(""+webOrder.getLists().get(i).getPrice()+"");
					buffer.append(",");
					buffer.append("\"quantity\":");
					buffer.append(""+webOrder.getLists().get(i).getQuantity()+"");
					buffer.append(",");
					buffer.append("\"amount\":");
					buffer.append(""+webOrder.getLists().get(i).getAmount()+"");
					buffer.append(",");
					buffer.append("\"discount\":");
					buffer.append(""+webOrder.getLists().get(i).getDiscount()+"");
					buffer.append("}");
					if(i<webOrder.getLists().size()-1){
						buffer.append(",");
					}
				}
			}
			buffer.append("]");
			printWriter.write(buffer.toString());
			printWriter.flush();
			printWriter.close();
			return;
		}else{
			printWriter.write("no data");
			printWriter.flush();
			printWriter.close();
			return;
			
		}
		
		
	}
	
	
	public void getCarsize() throws Exception{
		response.setContentType("text/html;charset=utf8");
		PrintWriter printWriter = response.getWriter();
		if(request.getSession().getAttribute("car")!=null){
			webOrder = (WebOrder) request.getSession().getAttribute("car");
			webOrderLists = webOrder.getLists();
			printWriter.write(webOrderLists.size());
			printWriter.flush();
			printWriter.close();
			return;
		}else{
			printWriter.write(0);
			printWriter.flush();
			printWriter.close();
			return;
		}
	}
	
	
	public List<ProductInfo> getProductInfos() {
		return productInfos;
	}
	public void setProductInfos(List<ProductInfo> productInfos) {
		this.productInfos = productInfos;
	}
	public List<Picture> getPictures() {
		return pictures;
	}
	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}
	public Map<Integer, String> getPicturemap() {
		return picturemap;
	}
	public void setPicturemap(Map<Integer, String> picturemap) {
		this.picturemap = picturemap;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public WebOrder getWebOrder() {
		return webOrder;
	}
	public void setWebOrder(WebOrder webOrder) {
		this.webOrder = webOrder;
	}
	public List<WebOrderList> getWebOrderLists() {
		return webOrderLists;
	}
	public void setWebOrderLists(List<WebOrderList> webOrderLists) {
		this.webOrderLists = webOrderLists;
	}
	public Integer getAddp_id() {
		return addp_id;
	}
	public void setAddp_id(Integer addp_id) {
		this.addp_id = addp_id;
	}
	
	
	
	
	
}
