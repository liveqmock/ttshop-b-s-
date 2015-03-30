package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pojo.Product;
import pojo.ProductInfo;
import pojo.Transfer;
import pojo.TransferList;
import pojo.Warehouse;
import tools.DateTool;
import tools.JsonTool;

@SuppressWarnings("serial")
public class TransferAction extends BaseAction {
	
	private Transfer transfer;
	private List<Transfer> transfers;
	private TransferList transferList;
	private List<TransferList> transferLists;
	private List<Warehouse> warehouses;
	private String warehouse;
	private Warehouse warehouse2;
	private String resJson;
	private String[] barcodes;
	private String[] imeis;
	private Integer receiverid;
	private String reason;
	private String transferno;
	private Integer quantitys[];
	private String bdate;
	private String edate;
	private String keyword;
	private Integer id;
	
	public String toTransfer() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"warehousefunction.jsp");
		List<ProductInfo> infos = this.productinfoService.findByKeyword("");
		warehouses = this.warehouseService.findByKeyword("");
		List<String[]> list1 = new ArrayList<String[]>();
		for (ProductInfo productInfo : infos) {
			String[] args = {productInfo.getBarcode(),productInfo.getBarcode()+"|"+productInfo.getPdesc(),productInfo.getPdesc()};
			list1.add(args);
		}
		resJson= "var infos = "+ JsonTool.getInstance().formList(list1,"value","label","desc")+";";
		return SUCCESS;
	}
	
	/**
	 * 调货单保存 ，暂时废掉有IMEI设备调货记录
	 * @return
	 * @throws Exception
	 */
	public String addTransfer() throws Exception{
		//receiverid 是接受仓库的 id
		//warehouse2 是转出仓库的 id
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"transfer"+FILESEPARATOR+"toTransfer.action");
		if(barcodes.length>0 && receiverid!=null && warehouse2!=null){
			transferLists = new ArrayList<TransferList>();
			Integer w2id = warehouse2.getId();
			Warehouse warehouse2 = this.warehouseService.get(Warehouse.class, w2id);
			Warehouse receiverWarehouse = this.warehouseService.get(Warehouse.class, receiverid);
			transferno = this.myidService.createIDNumber("TR-");
			for (int i = 0; i < barcodes.length; i++) {
				ProductInfo productInfo = this.productinfoService.findByBaecode(barcodes[i]);
				if (productInfo!=null) {
					TransferList transferList = new TransferList(transferno, productInfo.getBarcode(), quantitys[i], productInfo.getPdesc(),"");
					transferLists.add(transferList);
				}
			}
			/**
			for (int i = 0; i < imeis.length; i++) {
				String imei = imeis[i];
				if(!imei.trim().equals("")){
					Product product = this.productService.findByImeiAndWarehouse(imei, warehouse);
					if (product==null) {
						this.setMessage("没有此"+imei);
						return ERROR;
					}
					products.add(product);
				}
			}**/
			transfer = new Transfer(transferno,receiverWarehouse.getWname(),warehouse2.getWname(),transferLists, reason);
			System.out.println(reason);
			//this.productService.saleOrTransferProduct(products);
			this.pstockService.updateTransfer(transferLists, warehouse2.getWname());
			this.pstockService.recieveTransfer(transferLists, receiverWarehouse.getWname());
			this.transferListService.addManyObjects(transferLists);
			this.transferService.add(transfer);
			this.setReturnurl(request.getContextPath()+FILESEPARATOR+"transfer"+FILESEPARATOR+"listTransfer.action");
			this.setMessage(SUCCMESSAGE+" || 单号:"+transferno);
			return SUCCESS;
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	/**
	 * 调货记录
	 * @return
	 * @throws Exception
	 */
	public String listTransfer() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"warehousefunction.jsp");
		if(warehouses==null){
			warehouses = this.warehouseService.findByKeyword("");
		}
		if(bdate==null || bdate.trim().equals("")){
			bdate = DateTool.getInstance().DateToPattern1(new Date());
		}
		if(edate==null || edate.trim().equals("")){
			edate = DateTool.getInstance().DateToPattern1(new Date());
		}
		warehouse = warehouse==null?"all":warehouse;
		if(warehouse.trim().equals("all")){
			transfers = this.transferService.listTransfers(bdate+" 00:00:00", edate+" 59:59:59");
		}else{
			transfers = this.transferService.listTransfers(warehouse, bdate+" 00:00:00", edate+" 59:59:59");
		}
		return SUCCESS;
	}
	
	
	public String transferDetial() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"transfer"+FILESEPARATOR+"listTransfer.action");
		if (id==null) {
			this.setMessage(ERRORMESSAGE);
			return INPUT;
		}
		transfer = this.transferService.get(Transfer.class, id);
		transferLists = this.transferListService.findByTransferNo(transfer.getTransferno());
		if(transfer==null){
			this.setMessage(ERRORMESSAGE);
			return INPUT;
		}
		return SUCCESS;
	}
	
	
	public String cancelTransfer() throws Exception{
		this.setReturnurl(request.getContextPath()+FILESEPARATOR+"transfer"+FILESEPARATOR+"listTransfer.action");
		if(id==null){
			this.setMessage(ERRORMESSAGE);
			return INPUT;
		}
		transfer = this.transferService.get(Transfer.class, id);
		transferLists = this.transferListService.findByTransferNo(transfer.getTransferno());
		List<Product> products = new ArrayList<Product>();
		if(transfer!=null){
			transfer.setStatus(0);
			if(transferLists.size()>0){
				for (TransferList tl : transferLists) {
					tl.setStatus(0);
					Integer quantity = -tl.getQuantity();
					tl.setQuantity(quantity);
					if(tl.getImei()!=null&&!tl.getImei().trim().equals("")){
						//System.out.println(tl.getImei()+transfer.getWarehouse());
						Product product = this.productService.findByImeiAndWarehouseNoLimit(tl.getImei(), transfer.getWarehouse());
						//System.out.println(product);
						if(product!=null){
							products.add(product);
							//System.out.println("add"+product);
							continue;
						}
						this.setMessage("找不到此IMEI");
						return INPUT;
					}
				}
			}
			try {
				this.productService.calcelSaleOrTransferProduct(products);
				this.pstockService.recieveTransfer(transferLists, transfer.getReceiverid());
				this.pstockService.updateTransfer(transferLists, transfer.getWarehouse());
				this.transferListService.updateManyObjects(transferLists);
				this.transferService.update(transfer);
				this.setMessage(SUCCMESSAGE);
				return SUCCESS;
			} catch (Exception e) {
				this.setMessage(e.getMessage());
				return ERROR;
			}
		}
		this.setMessage(ERRORMESSAGE);
		return INPUT;
	}
	
	
	
	
	public Transfer getTransfer() {
		return transfer;
	}
	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}
	public List<Transfer> getTransfers() {
		return transfers;
	}
	public void setTransfers(List<Transfer> transfers) {
		this.transfers = transfers;
	}
	public TransferList getTransferList() {
		return transferList;
	}
	public void setTransferList(TransferList transferList) {
		this.transferList = transferList;
	}
	public List<TransferList> getTransferLists() {
		return transferLists;
	}
	public void setTransferLists(List<TransferList> transferLists) {
		this.transferLists = transferLists;
	}
	public String getResJson() {
		return resJson;
	}
	public void setResJson(String resJson) {
		this.resJson = resJson;
	}
	public String[] getBarcodes() {
		return barcodes;
	}
	public void setBarcodes(String[] barcodes) {
		this.barcodes = barcodes;
	}
	public String[] getImeis() {
		return imeis;
	}
	public void setImeis(String[] imeis) {
		this.imeis = imeis;
	}
	public Integer getReceiverid() {
		return receiverid;
	}
	public void setReceiverid(Integer receiverid) {
		this.receiverid = receiverid;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getTransferno() {
		return transferno;
	}
	public void setTransferno(String transferno) {
		this.transferno = transferno;
	}
	public Integer[] getQuantitys() {
		return quantitys;
	}
	public void setQuantitys(Integer[] quantitys) {
		this.quantitys = quantitys;
	}
	public String getBdate() {
		return bdate;
	}
	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	@Override
	public String getKeyword() {
		return keyword;
	}
	@Override
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Warehouse> getWarehouses() {
		return warehouses;
	}
	public void setWarehouses(List<Warehouse> warehouses) {
		this.warehouses = warehouses;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public Warehouse getWarehouse2() {
		return warehouse2;
	}
	public void setWarehouse2(Warehouse warehouse2) {
		this.warehouse2 = warehouse2;
	}
	
	
	
}
