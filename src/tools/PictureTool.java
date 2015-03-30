package tools;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class PictureTool {
	
	private static PictureTool instance;
	
	private PictureTool(){
		super();
	}
	
	public static PictureTool getInstance(){
		if(instance==null){
			synchronized (PictureTool.class) {
				if(instance==null){
					instance = new PictureTool();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 传回的是file,压缩后的文件
	 * @param file
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	public File resize(File inputfile,int width,int height,boolean proportion,String outputdir,String filename) throws Exception{
		System.gc();
		if(!inputfile.exists()){
			return null;
		}
		Image image = ImageIO.read(inputfile);
		if(image.getWidth(null)==-1){
			//System.out.println("不是图片格式");
			return null;
		}
		if(width>image.getWidth(null)){
			width = image.getWidth(null);
		}
		if(proportion==true){
			double rate = (double)image.getWidth(null)/(double)width;
	        height = (int) (image.getHeight(null)/rate);
		}
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(image,0, 0,width,height,null);
        File file2 = new File(outputdir,filename);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
        JPEGEncodeParam encodeParam = JPEGCodec.getDefaultJPEGEncodeParam(bufferedImage);
        /*压缩质量*/
        encodeParam.setQuality(0.9f, true);
        /*加上压缩质量条件*/
        encoder.encode(bufferedImage,encodeParam);
        inputfile = null;
        image = null;
        file2 = null;
        bufferedImage = null;
        encodeParam = null;
        encoder = null;
        fileOutputStream.close();
        Runtime.getRuntime().gc();
        System.out.println("全部回收后:"+Runtime.getRuntime().totalMemory()/1024/1024+"M");
		return file2;
	}
	
	/**
	 * 上传图片,并压缩
	 * @param file
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	public File uploadImage(File inputfile,String outputdir,String filename) throws Exception{
		Runtime.getRuntime().gc();
		if(!inputfile.exists()){
			return null;
		}
		int width = 600;
		int height = 600;
		Image image = ImageIO.read(inputfile);
		if(image.getWidth(null)==-1){
			return null;
		}
		//图片宽度设置为原图
		width = image.getWidth(null);
		double rate = (double)image.getWidth(null)/(double)width;
		height = (int) (image.getHeight(null)/rate);
		
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bufferedImage.getGraphics().drawImage(image,0, 0,width,height,null);
		File file2 = new File(outputdir,filename);
		FileOutputStream fileOutputStream = new FileOutputStream(file2);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
		JPEGEncodeParam encodeParam = JPEGCodec.getDefaultJPEGEncodeParam(bufferedImage);
		/*压缩质量*/
		encodeParam.setQuality(0.9f, true);
		/*加上压缩质量条件*/
		encoder.encode(bufferedImage,encodeParam);
		fileOutputStream.close();
		return file2;
	}
	
	/**
	 * 上传图片,生成压缩图和原图
	 * @param file
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	public void creteImages(File inputfile,String outputdir,String smalfiledir,String filename) throws Exception{
		if(!inputfile.exists()){
			return;
		}
		int width = 800;//800是无用的,初始化用
		int height = 800;//
		System.out.println("total_memory=" + Runtime.getRuntime().totalMemory()/1024/1024 + "M");    
		System.out.println("max_memory=" + Runtime.getRuntime().maxMemory()/1024/1024 + "M");
		Image image = ImageIO.read(inputfile);
		this.resize(inputfile, 500, 500, true, smalfiledir, filename);
		inputfile = null; //强制回收
		if(image.getWidth(null)==-1){
			return;
		}
		//图片宽度设置为原图
		width = image.getWidth(null);
		double rate = (double)image.getWidth(null)/(double)width;
		height = (int) (image.getHeight(null)/rate);
		
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bufferedImage.getGraphics().drawImage(image,0, 0,width,height,null);
		image = null;
		File file2 = new File(outputdir,filename);
		FileOutputStream fileOutputStream = new FileOutputStream(file2);
		file2 = null;
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
		JPEGEncodeParam encodeParam = JPEGCodec.getDefaultJPEGEncodeParam(bufferedImage);
		/*压缩质量*/
		encodeParam.setQuality(0.8f, true);
		/*加上压缩质量条件*/
		encoder.encode(bufferedImage,encodeParam);
		bufferedImage=null;
		fileOutputStream.close();
		Runtime.getRuntime().gc();
		System.out.println("成功");
		System.out.println("total_memory=" + Runtime.getRuntime().totalMemory()/1024/1024 + "M");   
		return;
	}
	
	
}
