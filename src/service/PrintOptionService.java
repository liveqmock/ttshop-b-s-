package service;

import java.util.List;

import pojo.PrintOption;

public interface PrintOptionService extends BaseService<PrintOption> {
	
	public abstract List<PrintOption> findOptionList(String keywords) throws Exception;
	public abstract PrintOption findOption() throws Exception;
	
}
