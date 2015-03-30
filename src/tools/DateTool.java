package tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateTool {
	
	private static String pattern1 = "yyyy-MM-dd";
	private static String pattern2 = "yyyy-MM-dd HH:mm:ss";
	private static String pattern3 = "yyyyMMddHHmmss";
	private static String pattern4 = "yyyyMMddHHmmssSSS";
	private static String year = "yyyy";
	private static String month = "yyyy-MM";
	private static DateTool instance = null;
	
	
	private DateTool(){
		super();
	}
	
	public static DateTool getInstance(){
		if(instance==null){
			synchronized (DateTool.class) {
				if(instance==null){
					instance = new DateTool();
				}
			}
		}
		return instance;
	};
	
	/**
	 * 
	 * @param date
	 * @return yyyy-MM-dd
	 */
	public String DateToPattern1(Date date){
		SimpleDateFormat dateformat = new SimpleDateFormat(pattern1);
		String reruenDate = dateformat.format(date);
		return reruenDate;
	}
	
	/**
	 * 
	 * @param date
	 * @return pattern2 yyyy-MM-dd HH:mm:ss
	 */
	public String DateToPattern2(Date date){
		SimpleDateFormat dateformat = new SimpleDateFormat(pattern2);
		String reruenDate = dateformat.format(date);
		return reruenDate;
	}
	
	/**
	 * 
	 * @param date
	 * @return yyyyMMddHHmmss
	 */
	public String DateToPattern3(Date date){
		SimpleDateFormat dateformat = new SimpleDateFormat(pattern3);
		String reruenDate = dateformat.format(date);
		return reruenDate;
	}
	
	/**
	 * 
	 * @param date
	 * @return yyyyMMddHHmmssSSS
	 */
	public String DateToPattern4(Date date){
		SimpleDateFormat dateformat = new SimpleDateFormat(pattern4);
		String reruenDate = dateformat.format(date);
		return reruenDate;
	}
	
	/**
	 * 当年第一天00:00:00 至 当年最后一天 23:59:59
	 * @param date
	 * @return
	 */
	public List<String> DateToYear(Date date){
		List<String> list = new ArrayList<String>();
		SimpleDateFormat dateformat = new SimpleDateFormat(year);
		String reruenDateBegin = dateformat.format(date)+"-01-01 00:00:00";
		String reruenDateEnd = dateformat.format(date)+ "-12-31 59:59:59";
		list.add(reruenDateBegin);
		list.add(reruenDateEnd);
		return list;
	}
	
	/**
	 * 当月第一天00:00:00 至 当月最后一天 23:59:59
	 * @param date
	 * @return 当月第一天00:00:00 至 当月最后一天 23:59:59
	 */
	public List<String> DateToMonth(Date date){
		List<String> list = new ArrayList<String>();
		SimpleDateFormat dateformat = new SimpleDateFormat(month);
		SimpleDateFormat dateformatdefault = new SimpleDateFormat(pattern2);
		SimpleDateFormat dateformatdeYear= new SimpleDateFormat(year);
		String reruenDateBegin = dateformat.format(date)+"-01 00:00:00";
		@SuppressWarnings("deprecation")
		int m = date.getMonth();
		String year = dateformatdeYear.format(new Date());
		int y = Integer.valueOf(year);
		String reruenDateEnd = dateformatdefault.format(new Date()); // 默认当前时间
		if(m==0){
			reruenDateEnd = dateformat.format(date)+ "-31 23:59:59";
		}else if(m==1){
			if((y%400==0)||((y%100!=0)&&(y%4==0))){
				reruenDateEnd = dateformat.format(date)+ "-29 23:59:59";
			}else{
				reruenDateEnd = dateformat.format(date)+ "-28 23:59:59";
			}
		}else if(m==2){
			reruenDateEnd = dateformat.format(date)+ "-31 23:59:59";
		}else if(m==3){
			reruenDateEnd = dateformat.format(date)+ "-30 23:59:59";
		}else if(m==4){
			reruenDateEnd = dateformat.format(date)+ "-31 23:59:59";
		}else if(m==5){
			reruenDateEnd = dateformat.format(date)+ "-30 23:59:59";
		}else if(m==6){
			reruenDateEnd = dateformat.format(date)+ "-31 23:59:59";
		}else if(m==7){
			reruenDateEnd = dateformat.format(date)+ "-31 23:59:59";
		}else if(m==8){
			reruenDateEnd = dateformat.format(date)+ "-30 23:59:59";
		}else if(m==9){
			reruenDateEnd = dateformat.format(date)+ "-31 23:59:59";
		}else if(m==10){
			reruenDateEnd = dateformat.format(date)+ "-30 23:59:59";
		}else if(m==11){
			reruenDateEnd = dateformat.format(date)+ "-31 23:59:59";
		}
		list.add(reruenDateBegin);
		list.add(reruenDateEnd);
		return list;
	}
	
	
	
}
