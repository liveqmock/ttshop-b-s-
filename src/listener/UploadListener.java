package listener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import pojo.UploadStatus;

public class UploadListener implements ProgressListener{
	
	private UploadStatus status;
	private HttpSession session;
	
	public UploadListener(HttpServletRequest request) {
		System.out.println("ÊµÀý»¯uploadlistener");
		session = request.getSession();
		status = new UploadStatus(0l, 0l, 0);
		session.setAttribute("uploadstatus", status);
	}
	
	public UploadListener() {
		super();
	}
	
	public void update(long readedBytes, long totalBytes, int currentItem) {
		//System.out.println("update:" + readedBytes + ";" + totalBytes + ";" + (int)(((float)readedBytes/(float)totalBytes)*100) + ";" + currentItem);  
    	status = (UploadStatus) session.getAttribute("uploadstatus");
    	status.setBytesread(readedBytes);
    	status.setTotalbytes(totalBytes);
    	status.setItems(currentItem);
    	double precent = (int)(((float)readedBytes/(float)totalBytes)*100);
    	status.setPrecent(precent);
	}
	
	
	
	
}
