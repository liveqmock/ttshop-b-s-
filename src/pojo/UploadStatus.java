package pojo;

public class UploadStatus {
	
	private long bytesread; //已上传的字节数 单位 byte B
	private long contentLength; //所有文件的字节数 单位 byte
	private Integer items; //正在上传第几个文件
	private String filename; //正在上传的文件名
	private long starttime;
	private Integer state; //记录已完成多少个文件
	private String message;
	private long totalbytes;  //总字节数 ，单位byte
	private Double precent;
	
	public long getBytesread() {
		return bytesread;
	}
	public void setBytesread(long bytesread) {
		this.bytesread = bytesread;
	}
	public long getContentLength() {
		return contentLength;
	}
	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}
	public Integer getItems() {
		return items;
	}
	public void setItems(Integer items) {
		this.items = items;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getStarttime() {
		return starttime;
	}
	public void setStarttime(long starttime) {
		this.starttime = starttime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getTotalbytes() {
		return totalbytes;
	}
	public void setTotalbytes(long totalbytes) {
		this.totalbytes = totalbytes;
	}
	public Double getPrecent() {
		return precent;
	}
	public void setPrecent(Double precent) {
		this.precent = precent;
	}
	public UploadStatus(long bytesread, long contentLength, Integer items) {
		super();
		this.bytesread = bytesread;
		this.contentLength = contentLength;
		this.items = items;
		this.starttime = System.currentTimeMillis();
	}
	public UploadStatus() {
		super();
	}
	
	
}
