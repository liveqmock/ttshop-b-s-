package tools;

import java.util.ArrayList;
import java.util.List;

public class SortTool {
	
	private static SortTool instance;
	
	private SortTool() {
		super();
	}
	
	public static SortTool getInstance(){
		if(instance==null){
			synchronized (SortTool.class) {
				if(instance==null){
					instance = new SortTool();
				}
			}
		}
		return instance;
	}
	
	public List<String> sortString(String[] strs){
		List<String> list = new ArrayList<String>();
		for(int i=0;i<strs.length;i++){
			if(!list.contains(strs[i]) && !strs[i].trim().equals("")){
				list.add(strs[i]);
			}
		}
		return list;
	}
	
	public List<String> stringToList(String string){
		List<String> strings = new ArrayList<String>();
		string = string.replaceAll("\\[", "");
		string = string.replaceAll("\\]", "");
		string = string.replaceAll(" ", "");
		int endindex = 0;
		while (string.indexOf(",")>=0) {
			endindex = string.indexOf(",");
			String bar = string.substring(0, endindex);
			string = string.substring(endindex+1);
			strings.add(bar);
		}
		if(!string.trim().equals("")){
			strings.add(string);
		}
		return strings;
	}
	
	public String listToString(List<String> strings){
		String string = strings.toString();
		string = string.replaceAll("\\[", "");
		string = string.replaceAll("\\]", "");
		return string;
	}
}
