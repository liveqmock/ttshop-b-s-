package action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.apache.struts2.ServletActionContext;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import pojo.Page;
import pojo.Picture;
import pojo.PictureCatalog;
import pojo.ProductInfo;
import pojo.UploadStatus;
import pojo.User;
import tools.DateTool;
import tools.PictureTool;
import tools.UploadFile;

@SuppressWarnings("serial")
public class FileUploadAction extends BaseAction {
	
	private File image;
	private String imageFileName;
	private List<File> images;
	private List<String> imagesContentType;
	private List<String> imagesFileName;
	private List<String> paths;
	private Picture picture;
	private List<Picture> pictures;
	private String catalog;
	private List<PictureCatalog> catalogs;
	private Integer catalogid;
	private PictureCatalog pictureCatalog ;
	private List<Integer> ids;
	private String barcode;
	private List<ProductInfo> infos; 
	private Page page;
	private ProductInfo info;
	/**
	 * 上传文件html5
	 * @return
	 * @throws Exception
	 */
	public String uploadFile() throws Exception{
		List<Picture> pictures = new ArrayList<Picture>();
		//寻找upload文件夹
		String path = ServletActionContext.getServletContext().getRealPath(FILESEPARATOR+"upload"+FILESEPARATOR+"normal");
		String tempath = ServletActionContext.getServletContext().getRealPath(FILESEPARATOR+"upload"+FILESEPARATOR+"small");
		for(int i = 0; i<images.size() ;i++){
			File image = images.get(i);
			if(image.getName()!=null && !image.getName().equals("")){
					FileInputStream fileInputStream = new FileInputStream(image);
					long size = fileInputStream.available();// 等价于image.length
					if(size>2*1024*1024){
						this.setMessage("单个上传文件过大,上传文件不能大于2MB"+imagesFileName.get(i));
						return INPUT;
					}
					String type = imagesFileName.get(i).substring(imagesFileName.get(i).indexOf('.'), imagesFileName.get(i).length());
					if(type.equalsIgnoreCase(".jpg") || type.equalsIgnoreCase(".png") || type.equalsIgnoreCase(".bmp") || type.equalsIgnoreCase(".gif")){
						String newfilename = UploadFile.getInstance().generateFilename(type);
						//String smallfilename = newfilename.substring(0, newfilename.indexOf('.'))+".jpg"; //压缩成 jpg 图片
						//PictureTool.getInstance().resize(image, 500, 0, true, tempath,smallfilename); //创建压缩图到 tmp 文件夹 pic宽500px
						//PictureTool.getInstance().uploadImage(image, path, newfilename);  //这个方法可以压缩图片，不过需考虑jvm内存溢出问题
						UploadFile.getInstance().upload(image, newfilename, path);
						//删除临时文件
						if(image!=null){
							image.delete();
						}
						//获取上传文件的绝对路径
						String outpath = request.getContextPath()+FILESEPARATOR+"upload"+FILESEPARATOR+"normal"+FILESEPARATOR;
						//String tmpoutpath = request.getContextPath()+FILESEPARATOR+"upload"+FILESEPARATOR+"small"+FILESEPARATOR;
						String url = outpath+newfilename;
						//把图片资料保存在数据库，方便调用
						Picture picture = new Picture("",newfilename, 0, url);
						picture.setSize((int)size/1024);
						//picture.setSmall(tmpoutpath+smallfilename);
						picture.setSmall(outpath+newfilename);
						pictures.add(picture);
						if(catalogid!=null){
							picture.setCatalogid(catalogid);
						}else{
							catalogid = 0;
							picture.setCatalogid(catalogid);
						}
				}else{
					this.setMessage("没有找到上传文件");
					request.getSession().removeAttribute("uploadstatus");
					return INPUT;
				}
			}
			
		}
		//上传完成后清楚uploadstatus
		request.getSession().removeAttribute("uploadstatus");
		this.pictureService.addManyObjects(pictures);
		this.setMessage(SUCCMESSAGE);
		return SUCCESS;
	}
	
	/**
	 * 上传文件html5 Ajax
	 * @return
	 * @throws Exception
	 */
	public void uploadFileAjax() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		List<Picture> pictures = new ArrayList<Picture>();
		//寻找upload文件夹
		String path = ServletActionContext.getServletContext().getRealPath(FILESEPARATOR+"upload"+FILESEPARATOR+"normal");
		String tempath = ServletActionContext.getServletContext().getRealPath(FILESEPARATOR+"upload"+FILESEPARATOR+"small");
		for(int i = 0; i<images.size() ;i++){
			File image = images.get(i);
			if(image.getName()!=null && !image.getName().equals("")){
					FileInputStream fileInputStream = new FileInputStream(image);
					long size = fileInputStream.available();// 等价于image.length
					if(size>2*1024*1024){
						request.getSession().removeAttribute("uploadstatus");
						printWriter.write("error"+ERRORMESSAGE);
						printWriter.flush();
						printWriter.close();
						return;
					}
					String type = imagesFileName.get(i).substring(imagesFileName.get(i).indexOf('.'), imagesFileName.get(i).length());
					if(type.equalsIgnoreCase(".jpg") || type.equalsIgnoreCase(".png") || type.equalsIgnoreCase(".bmp") || type.equalsIgnoreCase(".gif")){
						String newfilename = UploadFile.getInstance().generateFilename(type);
						//String smallfilename = newfilename.substring(0, newfilename.indexOf('.'))+".jpg"; //压缩成 jpg 图片
						//PictureTool.getInstance().resize(image, 500, 0, true, tempath,smallfilename); //创建压缩图到 tmp 文件夹 pic宽500px
						//PictureTool.getInstance().uploadImage(image, path, newfilename);  //这个方法可以压缩图片，不过需考虑jvm内存溢出问题
						UploadFile.getInstance().upload(image, newfilename, path);
						//删除临时文件
						if(image!=null){
							image.delete();
						}
						//获取上传文件的绝对路径
						String outpath = request.getContextPath()+FILESEPARATOR+"upload"+FILESEPARATOR+"normal"+FILESEPARATOR;
						String tmpoutpath = request.getContextPath()+FILESEPARATOR+"upload"+FILESEPARATOR+"small"+FILESEPARATOR;
						String url = outpath+newfilename;
						//把图片资料保存在数据库，方便调用
						Picture picture = new Picture("",newfilename, 0, url);
						picture.setSize((int)size/1024);
						//picture.setSmall(tmpoutpath+smallfilename);
						picture.setSmall(outpath+newfilename);
						pictures.add(picture);
						if(catalogid!=null){
							picture.setCatalogid(catalogid);
						}else{
							catalogid = 0;
							picture.setCatalogid(catalogid);
						}
				}else{
					request.getSession().removeAttribute("uploadstatus");
					printWriter.write("error:没有找到上传文件");
					printWriter.flush();
					printWriter.close();
					return;
				}
			}
			
		}
		//上传完成后清楚uploadstatus
		request.getSession().removeAttribute("uploadstatus");
		this.pictureService.addManyObjects(pictures);
		printWriter.write(SUCCMESSAGE);
		printWriter.flush();
		printWriter.close();
		return;
	}
	
	/**
	 * 上传文件html5 Ajax
	 * @return
	 * @throws Exception
	 */
	public void uploadFileAjaxhaveBarcode() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if(barcode==null || barcode.trim().equals("")){
			printWriter.write("error:上传失败,请联系管理员!");
			printWriter.flush();
			printWriter.close();
			return;
		}
		List<Picture> pictures = new ArrayList<Picture>();
		//寻找upload文件夹
		String path = ServletActionContext.getServletContext().getRealPath(FILESEPARATOR+"upload"+FILESEPARATOR+"normal");
		String tempath = ServletActionContext.getServletContext().getRealPath(FILESEPARATOR+"upload"+FILESEPARATOR+"small");
		for(int i = 0; i<images.size() ;i++){
			File image = images.get(i);
			if(image.getName()!=null && !image.getName().equals("")){
				FileInputStream fileInputStream = new FileInputStream(image);
				long size = fileInputStream.available();// 等价于image.length
				if(size>2*1024*1024){
					request.getSession().removeAttribute("uploadstatus");
					printWriter.write("error"+ERRORMESSAGE);
					printWriter.flush();
					printWriter.close();
					return;
				}
				String type = imagesFileName.get(i).substring(imagesFileName.get(i).indexOf('.'), imagesFileName.get(i).length());
				if(type.equalsIgnoreCase(".jpg") || type.equalsIgnoreCase(".png") || type.equalsIgnoreCase(".bmp") || type.equalsIgnoreCase(".gif")){
					String newfilename = UploadFile.getInstance().generateFilename(type);
					//String smallfilename = newfilename.substring(0, newfilename.indexOf('.'))+".jpg"; //压缩成 jpg 图片
					
					//PictureTool.getInstance().resize(image, 500, 0, true, tempath,smallfilename); //创建压缩图到 tmp 文件夹 pic宽500px
					//PictureTool.getInstance().uploadImage(image, path, newfilename);  //这个方法可以压缩图片，不过需考虑jvm内存溢出问题
					UploadFile.getInstance().upload(image, newfilename, path);
					//删除临时文件
					if(image!=null){
						image.delete();
					}
					//获取上传文件的绝对路径
					String outpath = request.getContextPath()+FILESEPARATOR+"upload"+FILESEPARATOR+"normal"+FILESEPARATOR;
					String tmpoutpath = request.getContextPath()+FILESEPARATOR+"upload"+FILESEPARATOR+"small"+FILESEPARATOR;
					String url = outpath+newfilename;
					//把图片资料保存在数据库，方便调用
					Picture picture = new Picture("",newfilename, 0, url);
					picture.setSize((int)size/1024);
					//picture.setSmall(tmpoutpath+smallfilename);
					picture.setSmall(url);
					picture.setBarcode(barcode);
					pictures.add(picture);
					if(catalogid!=null){
						picture.setCatalogid(catalogid);
					}else{
						catalogid = 0;
						picture.setCatalogid(catalogid);
					}
				}else{
					request.getSession().removeAttribute("uploadstatus");
					printWriter.write("error:没有找到上传文件");
					printWriter.flush();
					printWriter.close();
					return;
				}
			}
			
		}
		//上传完成后清楚uploadstatus
		request.getSession().removeAttribute("uploadstatus");
		this.pictureService.addManyObjects(pictures);
		printWriter.write(SUCCMESSAGE);
		printWriter.flush();
		printWriter.close();
		return;
	}
	
	
	/**
	 * 上传文件html5 到 OSS 服务器
	 * @return
	 * @throws Exception
	 */
	public String uploadFileByOSS() throws Exception{
		//初始化OSSCLIENT
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
		String tempath = ServletActionContext.getServletContext().getRealPath(FILESEPARATOR+"upload"+FILESEPARATOR+"small");
		boolean exists = ossClient.doesBucketExist(BUCKETNAME);
		if(!exists){
			ossClient.createBucket(BUCKETNAME);
		}
		List<Picture> pictures = new ArrayList<Picture>();
		UploadStatus status = new UploadStatus(0l, 0l, 0);
		status.setMessage("");
		for(int i = 0; i<images.size() ;i++){
			File image = images.get(i);
			//因为无法用 progresslistener 所以手动设置上传信息
			status.setPrecent(99.0);
			status.setMessage(status.getMessage()+"<p>文件"+(i+1)+":"+imagesFileName.get(i)+"正在上传...</p>");
			request.getSession().setAttribute("uploadstatus", status);
			if(image.getName()!=null && !image.getName().equals("")){
					FileInputStream fileInputStream = new FileInputStream(image);
					long size = fileInputStream.available();// 等价于image.length
					System.out.println(status.getMessage());
					if(size>2*1024*1024){
						this.setMessage("单个上传文件过大,上传文件不能大于2MB"+imagesFileName.get(i));
						return INPUT;
					}
					String type = imagesFileName.get(i).substring(imagesFileName.get(i).indexOf('.'), imagesFileName.get(i).length());
					if(type.equalsIgnoreCase(".jpg") || type.equalsIgnoreCase(".png") || type.equalsIgnoreCase(".bmp") || type.equalsIgnoreCase(".gif")){
						//随机数，组件文件名
						Random random = new Random(); 
						String suijishu = random.nextInt()+"";
						suijishu = suijishu.substring(suijishu.length()-4, suijishu.length());
						String newfilename = DateTool.getInstance().DateToPattern3(new Date())+suijishu+type;
						String smallfilename = newfilename.substring(0, newfilename.indexOf('.'))+".jpg";
						InputStream inputStream = new FileInputStream(image);
						ObjectMetadata metadata = new ObjectMetadata();
						metadata.setContentLength(image.length());
						ossClient.putObject(BUCKETNAME, newfilename, inputStream, metadata);
						
						status.setPrecent(100.0);
						status.setMessage(status.getMessage()+"<p>文件"+(i+1)+":"+imagesFileName.get(i)+"上传成功!</p>");
						request.getSession().setAttribute("uploadstatus", status);
						
						//开始上传略缩图 pic宽500px
						PictureTool.getInstance().resize(image, 500, 0, true, tempath, smallfilename);
						if(inputStream!=null){
							inputStream.close();
						}
						//获取上传文件的绝对路径
						//String outpath = request.getContextPath()+FILESEPARATOR+"upload"+FILESEPARATOR+"nromal"+FILESEPARATOR;
						String smalloutpath = request.getContextPath()+FILESEPARATOR+"upload"+FILESEPARATOR+"small"+FILESEPARATOR;
						//String url = outpath+newfilename;
						//把图片资料保存在数据库，方便调用
						//String url = file
						Picture picture = new Picture("",newfilename, 0, newfilename);
						picture.setSize((int)size/1024);
						pictures.add(picture);
						picture.setSmall(smalloutpath+smallfilename);
						if(catalogid!=null){
							picture.setCatalogid(catalogid);
						}else{
							catalogid=0;
							picture.setCatalogid(catalogid);
						}
						//删除临时文件
						if(image!=null){
							image.delete();
						}
				}else{
					this.setMessage("没有找到上传文件");
					request.getSession().removeAttribute("uploadstatus");
					return INPUT;
				}
			}
			
		}
		request.getSession().removeAttribute("uploadstatus");
		this.pictureService.addManyObjects(pictures);
		this.setMessage(SUCCMESSAGE);
		return SUCCESS;
	}
	
	/**
	 * 上传文件html5 到 OSS 服务器 ajax
	 * @return
	 * @throws Exception
	 */
	public void uploadFileByOSSAjax() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		//初始化OSSCLIENT
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
		String tempath = ServletActionContext.getServletContext().getRealPath(FILESEPARATOR+"upload"+FILESEPARATOR+"small");
		boolean exists = ossClient.doesBucketExist(BUCKETNAME);
		if(!exists){
			ossClient.createBucket(BUCKETNAME);
		}
		List<Picture> pictures = new ArrayList<Picture>();
		for(int i = 0; i<images.size() ;i++){
			File image = images.get(i);
			if(image.getName()!=null && !image.getName().equals("")){
				FileInputStream fileInputStream = new FileInputStream(image);
				long size = fileInputStream.available();// 等价于image.length
				if(size>2*1024*1024){
					printWriter.write("单个上传文件过大,上传文件不能大于2MB");
					printWriter.flush();
					printWriter.close();
					return;
				}
				String type = imagesFileName.get(i).substring(imagesFileName.get(i).indexOf('.'), imagesFileName.get(i).length());
				if(type.equalsIgnoreCase(".jpg") || type.equalsIgnoreCase(".png") || type.equalsIgnoreCase(".bmp") || type.equalsIgnoreCase(".gif")){
					//随机数，组件文件名
					Random random = new Random(); 
					String suijishu = random.nextInt()+"";
					suijishu = suijishu.substring(suijishu.length()-4, suijishu.length());
					String newfilename = DateTool.getInstance().DateToPattern3(new Date())+suijishu+type;
					String smallfilename = newfilename.substring(0, newfilename.indexOf('.'))+".jpg";
					
					//将request 得来的文件写入服务器中
					InputStream inputStream = new FileInputStream(image);
					ObjectMetadata metadata = new ObjectMetadata();
					metadata.setContentLength(image.length());
					ossClient.putObject(BUCKETNAME, newfilename, inputStream, metadata);
					
					//开始上传略缩图 pic宽500px
					PictureTool.getInstance().resize(image, 500, 0, true, tempath, smallfilename);
					if(inputStream!=null){
						inputStream.close();
					}
					//获取上传文件的绝对路径
					String smalloutpath = request.getContextPath()+FILESEPARATOR+"upload"+FILESEPARATOR+"small"+FILESEPARATOR;
					//把图片资料保存在数据库，方便调用
					Picture picture = new Picture("",newfilename, 0, newfilename);
					picture.setSize((int)size/1024);
					pictures.add(picture);
					picture.setSmall(smalloutpath+smallfilename);
					if(catalogid!=null){
						picture.setCatalogid(catalogid);
					}else{
						catalogid=0;
						picture.setCatalogid(catalogid);
					}
					//删除临时文件
					if(image!=null){
						image.delete();
					}
				}else{
					request.getSession().removeAttribute("uploadstatus");
					printWriter.write("不是图片格式,可选格式jpg,png,gif,bmp");
					printWriter.flush();
					printWriter.close();
					return;
				}
			}
			
		}
		request.getSession().removeAttribute("uploadstatus");
		this.pictureService.addManyObjects(pictures);
		printWriter.write(SUCCMESSAGE);
		printWriter.flush();
		printWriter.close();
		return;
	}
	
	/**
	 * 进度条
	 * @throws Exception
	 */
	public void progress() throws Exception{
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragrma", "no-store");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		if(request.getSession().getAttribute("uploadstatus")!=null){
			UploadStatus uploadStatus = (UploadStatus) request.getSession().getAttribute("uploadstatus");
			if(uploadStatus==null){
				writer.write("没有任何上传信息");
				writer.flush();
				writer.close();
				return;
			}else{
				long starttime = uploadStatus.getStarttime();
				long currenttime = System.currentTimeMillis();
				long usetime = (currenttime-starttime)/1000 + 1; //单位为s
				float speed = uploadStatus.getBytesread()/usetime/1000; //单位 kb/s
				double totallength = (double)uploadStatus.getTotalbytes()/1024/1024; // 单位为mb
				String json = "{";
				json+="\"precent\":";
				json+="\""+uploadStatus.getPrecent()+"\",";
				json+="\"speed\":";
				json+="\""+speed+"\",";
				json+="\"filename\":";
				json+="\""+uploadStatus.getFilename()+"\",";
				json+="item:";
				json+="\""+uploadStatus.getItems()+"\",";
				json+="\"totallength\":";
				json+="\""+Math.round(totallength)+"\",";
				json+="\"message\":";
				json+="\""+uploadStatus.getMessage()+"\"";
				json+="}";
				writer.write(json);
				writer.flush();
				writer.close();
				return;
			}
		}else{
			writer.write("没有任何上传信息");
			writer.flush();
			writer.close();
			return;
		}
	}
	
	/**
	 * 使用uploadify插件上传
	 * @throws Exception
	 */
	public void uploadimage() throws Exception{
		User user = (User) request.getSession().getAttribute("user");
		System.out.println("开始");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		String path = ServletActionContext.getServletContext().getRealPath("upload");
		List<Picture> pictures = new ArrayList<Picture>();
		if(images!=null && images.size()>0){
			//System.out.println(images.size());
			for (int i = 0; i < images.size(); i++) {
				File temfile = images.get(i);
				long fn = System.currentTimeMillis();
				String filename = fn+".jpg";
				File file = new File(path, filename);
				//System.out.println(filename);
				FileInputStream inputStream = new FileInputStream(temfile);
				FileOutputStream outputStream = new FileOutputStream(file);
				int len = 0;
				byte[] b = new byte[1024];
				while((len=inputStream.read(b))!=-1){
					outputStream.write(b,0,len);
				}
				//outputStream.flush();
				if(outputStream!=null){
					outputStream.close();
				}
				if(inputStream!=null){
					inputStream.close();
				}
				Picture picture = new Picture(user.getUserid(), filename, 0, request.getContextPath()+FILESEPARATOR+"upload"+FILESEPARATOR+filename);
				if(catalogid!=null && catalogid!=-1){
					pictureCatalog = this.pictureCatalogService.get(PictureCatalog.class, catalogid);
					picture.setCatalogid(catalogid);
				}else{
					picture.setCatalogid(0);
				}
				pictures.add(picture);
			}
			this.pictureService.addManyObjects(pictures);
			printWriter.write("上传成功!");
			printWriter.flush();
			printWriter.close();
			return;
		}
		//System.out.println("失败");
		printWriter.write("上传失败!");
		printWriter.flush();
		printWriter.close();
		return;
	}
	
	/**
	 * 转到美图秀秀修改页面
	 * @return
	 * @throws Exception
	 */
	public String toMeituEditor() throws Exception{
		if(picture!=null && picture.getId()!=null){
			picture = this.pictureService.get(Picture.class, picture.getId());
			return SUCCESS;
		}else{
			this.setMessage(ERRORMESSAGE);
			return INPUT;
		}
	}
	
	/**
	 * 使用美图秀秀插件修改保存图片
	 * @throws Exception
	 */
	public void meituEditor() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		String path = ServletActionContext.getServletContext().getRealPath(FILESEPARATOR+"upload"+FILESEPARATOR+"normal");
		//String tempath = ServletActionContext.getServletContext().getRealPath(FILESEPARATOR+"upload"+FILESEPARATOR+"small");
		if(image!=null){
			UploadFile.getInstance().upload(image, imageFileName, path);
			//PictureTool.getInstance().resize(image, 500, 500, true, tempath, imageFileName);
			printWriter.write(SUCCMESSAGE);
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
	 * 图片浏览
	 * @return
	 * @throws Exception
	 */
	public String listPicture() throws Exception{
		if(catalogid!=null && !catalogid.equals("")){
			int allrows = this.pictureService.getAllrows(catalogid);
			if(page==null){
				page = new Page(1, 6, allrows);
			}else{
				page.setMaxResult(6);
				page.setAllRows(allrows);
			}
			pictureCatalog = this.pictureCatalogService.get(PictureCatalog.class, catalogid);
			pictures = this.pictureService.listAllPicture(catalogid,page.getFirstResult(),page.getMaxResult());
		}else{
//			pictures = this.pictureService.listAllPicture(0);
			int allrows = this.pictureService.getAllrows(0);
			if(page==null){
				page = new Page(1, 6, allrows);
			}else{
				page.setMaxResult(6);
				page.setAllRows(allrows);
			}
			pictures = this.pictureService.listAllPicture(0,page.getFirstResult(),page.getMaxResult());
		}
		infos = this.productinfoService.findByKeyword("");
		catalogs = this.pictureCatalogService.listAllCatalogs();
		return SUCCESS;
	}
	/**
	 * 图片浏览 by OSS
	 * @return
	 * @throws Exception
	 */
	public String listPictureByOSS() throws Exception{
		if(catalogid!=null && !catalogid.equals("")){
			int allrows = this.pictureService.getAllrows(catalogid);
			if(page==null){
				page = new Page(1, 6, allrows);
			}else{
				page.setMaxResult(6);
				page.setAllRows(allrows);
			}
			pictureCatalog = this.pictureCatalogService.get(PictureCatalog.class, catalogid);
			pictures = this.pictureService.listAllPicture(catalogid,page.getFirstResult(),page.getMaxResult());
		}else{
//			pictures = this.pictureService.listAllPicture(0);
			int allrows = this.pictureService.getAllrows(0);
			if(page==null){
				page = new Page(1, 6, allrows);
			}else{
				page.setMaxResult(6);
				page.setAllRows(allrows);
			}
			pictures = this.pictureService.listAllPicture(0,page.getFirstResult(),page.getMaxResult());
		}
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
		Date expiration = new Date(new Date().getTime() + 3600 * 1000);
		for (Picture picture : pictures) {
			String key = picture.getFilename();
			URL url = ossClient.generatePresignedUrl(BUCKETNAME, key, expiration);
			picture.setUrl(url.toString());
			//picture.setSmall(url.toString()); //smallpic 保存在服务器
		}
		infos = this.productinfoService.findByKeyword("");
		catalogs = this.pictureCatalogService.listAllCatalogs();
		return SUCCESS;
	}
	
	/**
	 * 添加图片目录
	 * @throws Exception
	 */
	public void addCatalog() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		String catalog = request.getParameter("catalog");
		if(catalog!=null && !catalog.trim().equals("")){
			PictureCatalog pictureCatalog = new PictureCatalog(catalog);
			this.pictureCatalogService.add(pictureCatalog);
			printWriter.write("操作成功！");
			printWriter.flush();
			printWriter.close();
			return;
		}else{
			printWriter.write("请输入目录名称");
			printWriter.flush();
			printWriter.close();
			return;
		}
	}
	
	/**
	 * 移动图片至目录
	 * @throws Exception
	 */
	public void changeCatalog() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		if(catalogid!=null){
			List<Picture> pictures = new ArrayList<Picture>();
			for (int i = 0; i < ids.size(); i++) {
				Picture picture = this.pictureService.get(Picture.class, ids.get(i));
				picture.setCatalogid(catalogid);
				pictures.add(picture);
			}
			this.pictureService.updateManyObjects(pictures);
			writer.write("操作成功!");
			writer.flush();
			writer.close();
			return;
		}
		writer.write("error01:请选择目录!");
		writer.flush();
		writer.close();
		return;
	}
	
	/**
	 * 设置 barcode 
	 * @throws Exception
	 */
	public void changeBarcode() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		if(barcode!=null && ids.size()>0){
			List<Picture> pictures = new ArrayList<Picture>();
			for (int i = 0; i < ids.size(); i++) {
				Picture picture = this.pictureService.get(Picture.class, ids.get(i));
				picture.setBarcode(barcode);
				pictures.add(picture);
			}
			this.pictureService.updateManyObjects(pictures);
			writer.write("操作成功!");
			writer.flush();
			writer.close();
			return;
		}
		writer.write("error01:请选择目录!");
		writer.flush();
		writer.close();
		return;
	}
	
	
	/**
	 * 删除图片
	 * @return
	 * @throws Exception
	 */
	public void deleteImage() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		String msg = "";
		if(ids.size()>0){
			List<Picture> pictures = new ArrayList<Picture>(); 
			for (int i = 0; i < ids.size(); i++) {
				Picture picture = this.pictureService.get(Picture.class, ids.get(i));
				String path = ServletActionContext.getServletContext().getRealPath(FILESEPARATOR+"upload"+FILESEPARATOR+"normal"+FILESEPARATOR);	
				//String smallpath = ServletActionContext.getServletContext().getRealPath(FILESEPARATOR+"upload"+FILESEPARATOR+"small"+FILESEPARATOR);	
				String filepath = picture.getFilename();
				//String smallfilename = picture.getFilename().substring(0, picture.getFilename().indexOf('.'))+".jpg";
				File file = new File(path,filepath); //读取 file
			//	File smallfile = new File(smallpath,smallfilename); //读取 smallfile 
//				if(file.exists() && file.isFile() && smallfile.exists()){
				if(file.exists() && file.isFile()){
					if(file.delete()){
						//smallfile.delete();
						pictures.add(picture);
					}else{
						msg+="操作失败:"+picture.getFilename();
					}
				}
				//pictures.add(picture);
			}
			this.pictureService.deleteManyObjects(pictures);
			writer.write("操作成功");
			writer.flush();
			writer.close();
			return;
		}
		writer.write("error:"+msg);
		writer.flush();
		writer.close();
		return;
	}
	
	/**
	 * 删除分类文件夹
	 * @throws Exception
	 */
	public void deleteFloder() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		if(catalogid!=null && catalogid!=0){
			pictureCatalog = this.pictureCatalogService.get(PictureCatalog.class, catalogid);
			if(pictureCatalog!=null){
				pictureCatalog.setStatus(0);
				this.pictureCatalogService.update(pictureCatalog);
				printWriter.write(SUCCMESSAGE);
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
	 * 删除图片 by OSS
	 * @return
	 * @throws Exception
	 */
	public void deleteImageByOSS() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		String msg = "";
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
		if(ids.size()>0){
			List<Picture> pictures = new ArrayList<Picture>(); 
			for (int i = 0; i < ids.size(); i++) {
				Picture picture = this.pictureService.get(Picture.class, ids.get(i));
				String smallpath = ServletActionContext.getServletContext().getRealPath(FILESEPARATOR+"upload"+FILESEPARATOR+"small"+FILESEPARATOR);	
				String smallfilename = picture.getFilename().substring(0, picture.getFilename().indexOf('.'))+".jpg";
				File smallfile = new File(smallpath,smallfilename);
				System.out.println(smallfile.exists());
				try {
					ossClient.deleteObject(BUCKETNAME, picture.getFilename());
					if(smallfile.exists()){
						smallfile.delete();
					}
				} catch (Exception e) {
					writer.write("error:操作失败"+picture.getFilename());
					writer.flush();
					writer.close();
					return;
				}
				pictures.add(picture);
			}
			this.pictureService.deleteManyObjects(pictures);
			writer.write("操作成功");
			writer.flush();
			writer.close();
			return;
		}
		writer.write("error:"+msg);
		writer.flush();
		writer.close();
		return;
	}
	
	/**
	 * 为某产品设置主图片
	 * @return
	 * @throws Exception
	 */
	public String toChangeMainPicture() throws Exception{
		if(barcode!=null){
			this.setReturnurl(request.getContextPath()+FILESEPARATOR+"productinfo"+FILESEPARATOR+"toupdproductinfo.action?barcode="+barcode);
			pictures = this.pictureService.listAllPicture(barcode, 0, 20); //限制20张图片
			info = this.productinfoService.findByBaecode(barcode);
			if(info==null){
				return INPUT;
			}
//			/**
//			 * 使用 OSS
//			 */
//			OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
//			Date expiration = new Date(new Date().getTime() + 3600 * 1000);
//			for (Picture picture : pictures) {
//				String key = picture.getFilename();
//				URL url = ossClient.generatePresignedUrl(BUCKETNAME, key, expiration);
//				picture.setUrl(url.toString());
//			}
			//用 ecs 直接返回 pictures 即可
			return SUCCESS;
		}else{
			this.setReturnurl(request.getContextPath()+FILESEPARATOR+"productinfo"+FILESEPARATOR+"listproductinfo.action");
			this.setMessage("此产品不存在");
			return INPUT;
		}
	}
	
	/**
	 * 为某产品设置主图片
	 * @throws Exception
	 */
	public void changeMainPicture() throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		if(barcode!=null && !barcode.trim().equals("") && picture!=null && picture.getId()!=null){
			ProductInfo productInfo = this.productinfoService.findByBaecode(barcode);
			productInfo.setPicture(picture.getId());
			this.productinfoService.update(productInfo);
			writer.write("操作成功");
			writer.flush();
			writer.close();
			return;
		}
		writer.write("操作失败");
		writer.flush();
		writer.close();
		return;
	}
	
	
	
	
	public List<File> getImages() {
		return images;
	}

	public void setImages(List<File> images) {
		this.images = images;
	}

	public List<String> getImagesContentType() {
		return imagesContentType;
	}

	public void setImagesContentType(List<String> imagesContentType) {
		this.imagesContentType = imagesContentType;
	}
	public List<String> getImagesFileName() {
		return imagesFileName;
	}
	public void setImagesFileName(List<String> imagesFileName) {
		this.imagesFileName = imagesFileName;
	}
	public List<String> getPaths() {
		return paths;
	}
	public void setPaths(List<String> paths) {
		this.paths = paths;
	}
	public List<Picture> getPictures() {
		return pictures;
	}
	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	public List<PictureCatalog> getCatalogs() {
		return catalogs;
	}
	public void setCatalogs(List<PictureCatalog> catalogs) {
		this.catalogs = catalogs;
	}
	public Integer getCatalogid() {
		return catalogid;
	}
	public void setCatalogid(Integer catalogid) {
		this.catalogid = catalogid;
	}
	public PictureCatalog getPictureCatalog() {
		return pictureCatalog;
	}
	public void setPictureCatalog(PictureCatalog pictureCatalog) {
		this.pictureCatalog = pictureCatalog;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public List<ProductInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<ProductInfo> infos) {
		this.infos = infos;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public Picture getPicture() {
		return picture;
	}
	public void setPicture(Picture picture) {
		this.picture = picture;
	}
	public ProductInfo getInfo() {
		return info;
	}
	public void setInfo(ProductInfo info) {
		this.info = info;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	
	

	
	
	
	

	
	
	
	
	
	
}	
