package serviceImpl;

import java.util.ArrayList;
import java.util.List;
import pojo.Picture;
import pojo.ProductInfo;
import service.ProductinfoService;

public class ProductinfoServiceImpl extends BaseServiceImpl<ProductInfo> implements
		ProductinfoService {

	public List<ProductInfo> findByKeyword(String keyword) {
		String hql = "FROM ProductInfo as p WHERE (p.barcode LIKE ? OR p.ptype LIKE ? OR p.pdesc LIKE ?" +
				"OR p.pbrand LIKE ? OR p.pcolor LIKE ?) AND p.status = 1 ORDER BY p.pdesc DESC";
		List<ProductInfo> list = null;
		list = this.findByHQL(hql, "%"+keyword+"%","%"+keyword+"%","%"+keyword+"%","%"+keyword+"%","%"+keyword+"%");
		return list;
	}

	public ProductInfo findByBaecode(String barcode) {
		String hql = "FROM ProductInfo as p WHERE p.barcode = ? AND p.status = 1";
		ProductInfo productInfo = null;
		List<ProductInfo> infos = this.findByHQL(hql, barcode);
		if(infos.size()>0 && infos.size()==1){
			productInfo = infos.get(0);
		}
		return productInfo;
	}

	public List<ProductInfo> findByBarcodeOrDesc(String keyword,Integer itype) {
		String hql = "FROM ProductInfo as p WHERE p.barcode LIKE ? AND p.itype = ? AND p.status = 1";
		List<ProductInfo> list = this.findByHQL(hql,"%"+keyword+"%",itype);
		return list;
	}

	public List<ProductInfo> findWebShopList(String keyword) {
		List<ProductInfo> productInfos = new ArrayList<ProductInfo>();
		String sql1 = "SELECT p.id,p.ptype,p.itype,p.barcode,p.pdesc,p.introduction,p.pbrand,p.pcolor,pic.id,pic.url,pic.small,p.sprice,p.mprice,p.updatetime,p.status,pic.filename FROM productinfo as p LEFT OUTER JOIN picture AS pic ON(p.picture=pic.id) WHERE p.status= 1 and p.saleornot = 1 ORDER BY p.pdesc";
		String sql2 = "SELECT p.id,p.ptype,p.itype,p.barcode,p.pdesc,p.introduction,p.pbrand,p.pcolor,pic.id,pic.url,pic.small,p.sprice,p.mprice,p.updatetime,p.status,pic.filename FROM productinfo as p LEFT OUTER JOIN picture AS pic ON(p.picture=pic.id) WHERE p.status= 1 and p.saleornot = 1 and p.pdesc like '%"+keyword+"%' ORDER BY p.pdesc";
		List<Object[]> objects = null;
		if(keyword!=null && !keyword.trim().equals("")){
			objects = this.createSqlQuery(sql2);
		}else{
			objects = this.createSqlQuery(sql1);
		}
		for (Object[] objects2 : objects) {
			Integer id = Integer.valueOf(objects2[0].toString());
			String ptype = objects2[1].toString();
			Integer itype = Integer.valueOf(objects2[2].toString()); //0-非库存类型 1-库存类型 2-库存有IMEI
			//Ptype ptype2 = new Ptype(ptype, itype); //转换
			String barcode = objects2[3].toString();
			String pdesc = objects2[4].toString();
			String introduction = objects2[5]==null?null:objects2[5].toString();
			String pbrand  = objects2[6]==null?null:objects2[6].toString();
			String pcolor  = objects2[7]==null?null:objects2[7].toString();
			Integer picture = objects2[8]==null?null:Integer.valueOf(objects2[8].toString()); //保存时候只保存
			String url = objects2[9]==null?null:objects2[9].toString(); 
			String small = objects2[10]==null?null:objects2[10].toString(); 
			Double sprice = objects2[11]==null?null:Double.valueOf(objects2[11].toString());
			Double mprice = objects2[12]==null?null:Double.valueOf(objects2[12].toString());
			String updateTime = objects2[13].toString();
			Integer status = Integer.valueOf(objects2[14].toString());
			String filename = objects2[15]==null?null:objects2[15].toString();
			Picture image = new Picture();
			image.setId(picture);
			image.setUrl(url);
			image.setSmall(small);
			image.setFilename(filename);
//			ProductInfo productInfo = new ProductInfo(ptype2, barcode, pdesc);
			ProductInfo productInfo = new ProductInfo(ptype,itype, barcode, pdesc);
			productInfo.setId(id);
			productInfo.setIntroduction(introduction);
			productInfo.setImage(image);
			productInfo.setPicture(picture);
			productInfo.setUpdateTime(updateTime);
			productInfo.setStatus(status);
			productInfo.setSprice(sprice);
			productInfo.setMprice(mprice);
			productInfo.setPbrand(pbrand);
			productInfo.setPcolor(pcolor);
			productInfos.add(productInfo);
		}
		return productInfos;
	}
	
	public List<ProductInfo> findWebShopList(String keyword,int first,int max) {
		List<ProductInfo> productInfos = new ArrayList<ProductInfo>();
		String sql1 = "SELECT p.id,p.ptype,p.itype,p.barcode,p.pdesc,p.introduction,p.pbrand,p.pcolor,pic.id,pic.url,pic.small,p.sprice,p.mprice,p.updatetime,p.status,pic.filename FROM productinfo as p LEFT OUTER JOIN picture AS pic ON(p.picture=pic.id) WHERE p.status= 1 and p.saleornot = 1 ORDER BY p.pdesc LIMIT "+first+","+max+" ";
		String sql2 = "SELECT p.id,p.ptype,p.itype,p.barcode,p.pdesc,p.introduction,p.pbrand,p.pcolor,pic.id,pic.url,pic.small,p.sprice,p.mprice,p.updatetime,p.status,pic.filename FROM productinfo as p LEFT OUTER JOIN picture AS pic ON(p.picture=pic.id) WHERE p.status= 1 and p.saleornot = 1 and p.pdesc like '%"+keyword+"%' ORDER BY p.pdesc LIMIT "+first+","+max+" ";
		List<Object[]> objects = null;
		if(keyword!=null && !keyword.trim().equals("")){
			objects = this.createSqlQuery(sql2);
		}else{
			objects = this.createSqlQuery(sql1);
		}
		for (Object[] objects2 : objects) {
			Integer id = Integer.valueOf(objects2[0].toString());
			String ptype = objects2[1].toString();
			Integer itype = Integer.valueOf(objects2[2].toString()); //0-非库存类型 1-库存类型 2-库存有IMEI
			//Ptype ptype2 = new Ptype(ptype, itype);//临时转换
			String barcode = objects2[3].toString();
			String pdesc = objects2[4].toString();
			String introduction = objects2[5]==null?null:objects2[5].toString();
			String pbrand  = objects2[6]==null?null:objects2[6].toString();
			String pcolor  = objects2[7]==null?null:objects2[7].toString();
			Integer picture = objects2[8]==null?null:Integer.valueOf(objects2[8].toString()); //保存时候只保存
			String url = objects2[9]==null?null:objects2[9].toString(); 
			String small = objects2[10]==null?null:objects2[10].toString(); 
			Double sprice = objects2[11]==null?null:Double.valueOf(objects2[11].toString());
			Double mprice = objects2[12]==null?null:Double.valueOf(objects2[12].toString());
			String updateTime = objects2[13].toString();
			Integer status = Integer.valueOf(objects2[14].toString());
			String filename = objects2[15]==null?null:objects2[15].toString();
			Picture image = new Picture();
			image.setId(picture);
			image.setUrl(url);
			image.setSmall(small);
			image.setFilename(filename);
			//ProductInfo productInfo = new ProductInfo(ptype2, barcode, pdesc);
			ProductInfo productInfo = new ProductInfo(ptype,itype, barcode, pdesc);
			productInfo.setId(id);
			productInfo.setIntroduction(introduction);
			productInfo.setImage(image);
			productInfo.setPicture(picture);
			productInfo.setUpdateTime(updateTime);
			productInfo.setStatus(status);
			productInfo.setSprice(sprice);
			productInfo.setMprice(mprice);
			productInfo.setPbrand(pbrand);
			productInfo.setPcolor(pcolor);
			productInfos.add(productInfo);
		}
		return productInfos;
	}

	

}
