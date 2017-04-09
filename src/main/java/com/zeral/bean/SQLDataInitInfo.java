package com.zeral.bean;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;



/**
 * 程序初始化完成之后执行的sql对象
 */
@Root
public class SQLDataInitInfo {
	
	/**
	 * 初始化标识，如oa-database-init
	 */
	@Attribute
	private String initCode;
	
	
	@ElementList(inline=true,entry="executeInfo",required=false)
	private List<SQLDataExecuteInfo> excuteInfoList = new ArrayList<SQLDataExecuteInfo>();
	
	public String getInitCode() {
		return initCode;
	}

	public void setInitCode(String initCode) {
		this.initCode = initCode;
	}

	public List<SQLDataExecuteInfo> getExcuteInfoList() {
		return excuteInfoList;
	}

	public void setExcuteInfoList(List<SQLDataExecuteInfo> excuteInfoList) {
		this.excuteInfoList = excuteInfoList;
	}


	

	
	
}
