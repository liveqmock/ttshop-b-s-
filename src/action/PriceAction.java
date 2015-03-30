package action;

import java.util.List;

import pojo.Pprice;

@SuppressWarnings("serial")
public class PriceAction extends BaseAction {
	
	private Pprice pprice;
	private List<Pprice> list;
	private List<Object> outlist;
	
	public String addprice() throws RuntimeException{
		
		
		
		return INPUT;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Pprice getPprice() {
		return pprice;
	}
	public void setPprice(Pprice pprice) {
		this.pprice = pprice;
	}
	public List<Pprice> getList() {
		return list;
	}
	public void setList(List<Pprice> list) {
		this.list = list;
	}
	public List<Object> getOutlist() {
		return outlist;
	}
	public void setOutlist(List<Object> outlist) {
		this.outlist = outlist;
	}
	
	
	
	
}
