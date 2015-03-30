package service;

import java.util.List;
import pojo.InnoteList;
import pojo.Pstock;
import pojo.ReturnSaleList;
import pojo.SaleRecordList;
import pojo.TransferList;

public interface PstockService extends BaseService<Pstock> {
	
	public abstract Pstock findbyBarcodeAndWarehouse(String barcode,String warehouse);
	public abstract Pstock findbyBarcode(String barcode);
	public abstract void updateInbound(List<InnoteList> innoteLists,String warehouse);
	public abstract void updateTransfer(List<TransferList> transferLists,String warehouse); 
	public abstract void recieveTransfer(List<TransferList> transferLists,String warehouse); 
	public abstract void updateReturn(List<ReturnSaleList> returnSaleLists,String warehouse); 
	public abstract void updateSale(List<SaleRecordList> saleRecordLists,String warehouse);
	public abstract void updateChangeInbound(Pstock pstock,Integer newquantity);
	public abstract List<Object[]> listStock(Integer lowestQuantity,String warehouse);
	public abstract List<Object[]> listStock(String warehouse);
	public abstract List<Object[]> listStock();
	public abstract List<Object[]> listStockAllWarehouse(Integer lowestQuantity);
	
}
