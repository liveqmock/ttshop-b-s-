package tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckTool {
	
	private static CheckTool instance;
	
	public static CheckTool getInstance(){
		if(instance==null){
			synchronized (CheckTool.class) {
				if(instance==null){
					instance = new CheckTool();
				}
			}
		}
		return instance;
	}
	
	private CheckTool(){
		super();
	}
	
	/**
	 * 验证一列字符串是否有空
	 * @param strings
	 * @return
	 */
	public boolean checkNull(String...strings){
		boolean b = false;
		if(strings.equals(null)){
			return b;
		}
		for(int i=0;i< strings.length;i++){
			if(strings[i]!=null){
				if(!strings[i].trim().equals("")){
					b = true;
				}else{
					b = false;
					return b;
				}
			}else{
				return b;
			}
		}
		return b;
	}
	
	/**
	 * 验证是否数字格式
	 * @param object
	 * @return
	 */
	public boolean checkNumber(Object object){
		boolean b = false;
		if(object.equals(null)){
			return b;
		}
		if(object.toString().trim().equals("")){
			return b;
		}
		String regex = "-?[0-9]*|[0-9]*\\.[0-9]*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(object.toString());
		if(matcher.matches()){
			b = true;
		}
		return b;
	}
	
	/**
	 * 验证是否数字格式
	 * @param objects
	 * @return
	 */
	public boolean checkNumbers(Object...objects){
		boolean b = false;
		if(objects.equals(null)){
			return b;
		}
		for (Object object : objects) {
			if(object.toString().trim().equals("")){
				return b;
			}
			String regex = "-?[0-9]*|[0-9]*\\.[0-9]*";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(object.toString());
			if(matcher.matches()){
				b = true;
			}
		}
		return b;
	}
	
	
	
}
