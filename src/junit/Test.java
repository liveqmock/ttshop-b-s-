package junit;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pojo.Customer;
import pojo.Innote;
import pojo.InnoteList;
import pojo.Page;
import pojo.Picture;
import pojo.ProductInfo;
import pojo.Pstock;
import pojo.Ptype;
import pojo.SaleRecordList;
import pojo.StockChecking;
import pojo.Warehouse;
import pojo.WebOrder;
import pojo.WebOrderList;
import service.InnoteListService;
import service.InnoteService;
import service.PictureService;
import service.ProductinfoService;
import service.PstockService;
import service.PtypeService;
import service.ReturnSaleListService;
import service.ReturnSaleService;
import service.SaleListService;
import service.SaleService;
import service.StockCheckingService;
import service.WarehouseService;
import service.WebOrderListService;
import service.WebOrderService;
import tools.DateTool;
import tools.JsonTool;
import tools.PictureTool;
import tools.UploadFile;

public class Test {
	
	ApplicationContext context;
	ReturnSaleService returnSaleService;
	ReturnSaleListService returnSaleListService;
	PictureService pictureService;
	PstockService pstockService;
	SaleService saleService;
	SaleListService saleListService;
	ProductinfoService productinfoService;
	WebOrderService webOrderService;
	WebOrderListService webOrderListService;
	StockCheckingService stockCheckingService;
	WarehouseService warehouseService;
	PtypeService ptypeService;
	InnoteService innoteService;
	InnoteListService innoteListService;
	private static String ACCESSKEYID = "vLLrdpKJPCLUzd19";
	private static String ACCESSKEYSECRET  = "BLb9Ju3tBgbX7fYNCNEDWbSQ7vGEN3";
	private static String ENDPOINT ="http://oss-cn-shenzhen.aliyuncs.com"; //深圳点
	private static String BUCKETNAME = "ttshop";
	
	@Before
	public void init(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		returnSaleService = (ReturnSaleService) context.getBean("returnSaleService");
		returnSaleListService = (ReturnSaleListService) context.getBean("returnSaleListService");
		pictureService = (PictureService) context.getBean("pictureService");
		pstockService = (PstockService) context.getBean("pstockService");
		saleService = (SaleService) context.getBean("saleService");
		saleListService = (SaleListService) context.getBean("saleListService");
		productinfoService = (ProductinfoService) context.getBean("productinfoService");
		webOrderService = (WebOrderService) context.getBean("webOrderService");
		webOrderListService =(WebOrderListService) context.getBean("webOrderListService");
		stockCheckingService =(StockCheckingService) context.getBean("stockCheckingService");
		warehouseService = (WarehouseService) context.getBean("warehouseService");
		ptypeService = (PtypeService) context.getBean("ptypeService");
		innoteService = (InnoteService) context.getBean("innoteService");
		innoteListService = (InnoteListService) context.getBean("innoteListService");
	}
	
	@org.junit.Test
	public void test(){
		String num  = "-123";
		String reg = "-?[0-9]*";
		System.out.println(num.matches(reg));
	}
	
	@org.junit.Test
	public void test1() throws Exception{
		String date  = DateTool.getInstance().DateToPattern1(new Date());
		this.returnSaleService.listReturnSales("2014-01-01"+" 00:00:00", date+" 59:59:59");
	}
	
	@org.junit.Test
	public void testAllrow() throws Exception{
		int all = this.pictureService.getAllrows(3);
		Page page = new Page(2, 2, all);
		System.out.println(page.getFirstResult());
		System.out.println(page.getTotalPage());
		List<Picture> pictures = this.pictureService.listAllPicture(3,page.getFirstResult(), page.getMaxResult());
		for (Picture picture : pictures) {
			System.out.println(picture.getId());
		}
	}
	@org.junit.Test
	public void saletest() throws Exception{
		List<Object[]> list = this.pstockService.listStock();
		for (Object[] objects : list) {
			System.out.println(objects[2]);
		}
	}
	
	@org.junit.Test
	public void returnsaletest(){
		Integer quantity = this.returnSaleListService.findOldQuantityByInvoiceAndbarcode("s-10000001", "88093442024");
		System.out.println(quantity);
	}
	@org.junit.Test
	public void osstest() {
		try {
		} catch (Exception e) {
			System.out.println("use other");
			e.printStackTrace();
		}
		    // 获取Object，返回结果为OSSObject对象
		    //OSSObject object = ossClient.getObject("ttshop", "20150311101449-1110317736.png");
		//GetObjectRequest object = new GetObjectRequest(BUCKETNAME, "20150311101449-1110317736.png");
//		Date expiration = new Date(new Date().getTime() + 3600 * 1000);
//		URL url = ossClient.generatePresignedUrl(BUCKETNAME, "20150311101449-1110317736.png", expiration);
//		System.out.println(url);
//		String path = ossClient.getBucketLocation(BUCKETNAME);
//		System.out.println(path);
//		ObjectListing objectList =  ossClient.listObjects("ttshop");
//		for (OSSObjectSummary ossObjectSummary : objectList.getObjectSummaries()) {
//			System.out.println(ossObjectSummary.getKey()+"-"+ossObjectSummary.getBucketName());
//			String key = ossObjectSummary.getKey();
//			String bname = ossObjectSummary.getBucketName();
//			Date expiration = new Date(new Date().getTime() + 3600 * 1000);
//			GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bname, key);
//			request.setExpiration(expiration);
//			URL url = ossClient.generatePresignedUrl(request);
//			System.out.println(url);
//			System.out.println("http://"+BUCKETNAME+"."+path+".aliyuncs.com/"+key);
//		}
	}
	@org.junit.Test
	public void image() throws Exception{
		PictureTool pictureTool = PictureTool.getInstance();
		File file = new File("/Users/mac/Desktop/截图/honey face chiffon fluid 粉底液/QB20150312-2@2x.png");
		File file2 = pictureTool.resize(file, 600, 0, true,"/Users/mac/Desktop/截图/","1.jpg");
		System.out.println(file2.getName());
	}
	@org.junit.Test
	public void uploadimage() throws Exception{
		System.out.println(Runtime.getRuntime().totalMemory()/1024/1024+"M");
		File file = new File("/Users/mac/Desktop/截图/未命名文件夹/IMG_5315.JPG");
		System.out.println(Runtime.getRuntime().totalMemory()/1024/1024+"M");
//		pictureTool.resize(file, 600, 0, true,"/Users/mac/Desktop/截图/small/","1.jpg");
//		pictureTool.resize(file1, 600, 0, true,"/Users/mac/Desktop/截图/small/","2.jpg");
//		pictureTool.resize(file2, 600, 0, true,"/Users/mac/Desktop/截图/small/","3.jpg");
//		pictureTool.resize(file3, 600, 0, true,"/Users/mac/Desktop/截图/small/","4.jpg");
//		pictureTool.resize(file2, 600, 0, true,"/Users/mac/Desktop/截图/small/","5.jpg");
//		pictureTool.resize(file4, 600, 0, true,"/Users/mac/Desktop/截图/small/","6.jpg");
		//pictureTool.uploadImage(file, "/Users/mac/Desktop/截图/", "111.jpg");
		//File out = new File("/Users/mac/Desktop/截图/small/","new.jpg");
		UploadFile.getInstance().upload(file,".jpg", "/Users/mac/Desktop/截图/small/");
		
//		pictureTool.creteImages(file, "/Users/mac/Desktop/截图/", "/Users/mac/Desktop/截图/small/", "1.jpg");
//		System.out.println(Runtime.getRuntime().totalMemory()/1024/1024+"M");
//		pictureTool.creteImages(file1, "/Users/mac/Desktop/截图/", "/Users/mac/Desktop/截图/small/", "2.jpg");
//		System.out.println(Runtime.getRuntime().totalMemory()/1024/1024+"M");
//		pictureTool.creteImages(file2, "/Users/mac/Desktop/截图/", "/Users/mac/Desktop/截图/small/", "3.jpg");
		System.out.println(Runtime.getRuntime().totalMemory()/1024/1024+"M");
	}
	@org.junit.Test
	public void product() throws Exception{
//		List<ProductInfo> productInfos = this.productinfoService.findWebShopList("蜗牛",0,6);
//		for (ProductInfo productInfo : productInfos) {
//			System.out.println(productInfo.getPdesc()+"-"+productInfo.getImage().getSmall());
//		}
		ProductInfo info = this.productinfoService.get(ProductInfo.class, 195);
		//System.out.println("🐌".getBytes("utf-8").toString());
		//info.setIntroduction("🐌");
		System.out.println(info.getIntroduction());
		System.out.println(new String(info.getIntroduction().getBytes("utf-8")));
		//this.productinfoService.update(info);
	}
	@org.junit.Test
	public void testurl(){
		String url = "./ttshop";
		System.out.println(url.startsWith("/"));
		System.out.println(url.indexOf("/user/")!=-1);
		System.out.println(url.indexOf("/webshop/")!=-1);
		if(url.indexOf("/user/")!=-1||url.indexOf("/webshop/")!=-1){
			System.out.println("do nothing");
		}else{
			System.out.println("dofilter");
			
		}
	}
	@org.junit.Test
	public void testOrder(){
		String order_no = DateTool.getInstance().DateToPattern4(new Date());
		System.out.println(order_no);
		ProductInfo productInfo = this.productinfoService.get(ProductInfo.class, 180);
		System.out.println(productInfo.getSprice());
		List<WebOrderList> list = new ArrayList<WebOrderList>();
		WebOrderList webOrderList = new WebOrderList(order_no, productInfo, productInfo.getSprice(), 1, 10.0);
		System.out.println(webOrderList.getAmount());
		list.add(webOrderList);
		WebOrder webOrder = new WebOrder(order_no,new Customer(),list, 0.0, 1);
		webOrderListService.addManyObjects(list);
		webOrderService.add(webOrder);
		System.out.println(webOrder.getAmount());
	}
	
	@org.junit.Test
	public void stockChecking(){
//		Pstock pstock = this.pstockService.load(Pstock.class, 1);
//		System.out.println(pstock.getWarehouse());
//		System.out.println(pstock.getQuantity());
//		Warehouse warehouse = warehouseService.finybyName(pstock.getWarehouse());
//		System.out.println(warehouse.getWname());
//		System.out.println(warehouse.getId());
//		StockChecking stockChecking = new StockChecking(pstock, 65, warehouse);
//		stockCheckingService.add(stockChecking);
//		StockChecking checking = this.stockCheckingService.get(StockChecking.class, 1);
//		System.out.println(checking.getPstock().getBarcode());
//		StockChecking checking = this.stockCheckingService.
//		System.out.println(checking.getPstock().getProductInfo());
//		System.out.println(checking.getWarehouse().getWname());
	}
	
	@org.junit.Test
	public void repairPstock() throws Exception{
//		/**
//		 * 映射更新修正数据库
//		 */
//		String hql = "select p FROM Pstock as p inner join p.productInfo";
//		List<Pstock> list = this.pstockService.findByHQL(hql);
		List<Pstock> list = this.pstockService.findByHQL("FROM Pstock");
		System.out.println(list.size());
		List<Pstock> deletelist = new ArrayList<Pstock>();
		for (Pstock pstock : list) {
			ProductInfo info = this.productinfoService.findByBaecode(pstock.getBarcode());
			System.out.println(info);
			if(info==null){
				System.out.println(pstock.getId()+"--" + pstock.getBarcode());
				//deletelist.add(pstock);
			}else{
				//pstock.setProductInfo(info.getId());
				pstock.setProductInfo(info);
			}
		}
		this.pstockService.updateManyObjects(list);
		this.pstockService.deleteManyObjects(deletelist);
//		for (Pstock pstock : list) {
//			System.out.println(pstock.getProductInfo());
//		}
//		List<StockChecking> checkings = this.stockCheckingService.findCheckingListByDateAndWarehouse("2015-04-03", 5);
//		System.out.println(checkings.size());
//		for (StockChecking stockChecking : checkings) {
//			System.out.println(stockChecking.getPstock().getProductInfo().getPdesc());
//		}
	}
	@org.junit.Test
	public void ssss(){
		//List<Object> objects = new ArrayList<Object>();
		//objects.add(Expression.eq("barcode", "8809318929360"));
		Pstock pstock = this.pstockService.findbyBarcode("8809318929360");
		//pstock = this.pstockService.get(Pstock.class, pstock.getId());
		System.out.println(pstock.getProductInfo().getBarcode());
		pstock.setQuantity(pstock.getQuantity()+1);
		this.pstockService.update(pstock);
	}
	
	@org.junit.Test
	public void teststock() {
		List<SaleRecordList> recordLists = new ArrayList<SaleRecordList>();
		SaleRecordList saleRecordList = new SaleRecordList("aa", 1, "000", "", 10, 100.0);
		SaleRecordList saleRecordList1 = new SaleRecordList("aa", 2, "8809294557137", "", 10, 150.0);
		recordLists.add(saleRecordList);
		recordLists.add(saleRecordList1);
		this.pstockService.updateSale(recordLists, "ttshop");
	}
	@org.junit.Test
	public void testInbound(){
		List<InnoteList> innoteLists = new ArrayList<InnoteList>();
		InnoteList innoteList = new InnoteList("", "8809294557137", 100, 20.0, "");
		innoteLists.add(innoteList);
		Innote innote = new Innote("", "ttshop", "1", "user", "01", innoteLists);
		this.pstockService.updateInbound(innoteLists, "ttshop");
		this.innoteListService.updateManyObjects(innoteLists);
		this.innoteService.update(innote);
//		ProductInfo productInfo =	productinfoService.findByBaecode("8809294557137");
//		System.out.println(productInfo.getBarcode());
	}
	
	@org.junit.Test
	public void testBackUp() {
//		List<ProductInfo> infos = this.productinfoService.findByKeyword("");
//		for (ProductInfo productInfo : infos) {
//			System.out.println(productInfo.getId());
//		}
		ProductInfo productInfo = this.productinfoService.get(ProductInfo.class, 430);
		System.out.println(productInfo);
		ProductInfo productInfo1 = this.productinfoService.get(ProductInfo.class, 430);
		System.out.println(productInfo.equals(productInfo1));
	}
	@org.junit.Test
	public void yztest(){
//		String APP_ID = "d1ca58f88a71c5b720"; //这里换成你的app_id
//		String APP_SECRET = "67f269f94be434dd1b3bb167068f40b8"; //这里换成你的app_secret
		
	}
}