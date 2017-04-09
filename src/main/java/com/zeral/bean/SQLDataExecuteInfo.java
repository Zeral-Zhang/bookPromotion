package com.zeral.bean;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

/**
 * 程序初始化完成之后执行的sql
 */
public class SQLDataExecuteInfo {
	
	
	/**建表脚本classpath*/
	@SuppressWarnings("serial")
	@ElementList(inline=true,entry="sqlPath")
	private List<String> sqlPaths = new ArrayList<String>(){

		public boolean add(String e) {
			e = e.trim();
			return super.add(e);
		}
	};
	
	/**存储过程脚本classpath*/
	@ElementList(inline=true,entry="procPath",required=false)
	private List<String> procedurePaths = new ArrayList<String>();
	
	/**更新版本*/
	@Attribute
	private String updateVersion;
	
	public static final SQLDataExecuteInfo getInstance(String sqlPath,String updateVersion){
		SQLDataExecuteInfo updateInfo = new SQLDataExecuteInfo();
		updateInfo.getSqlPaths().add(sqlPath);
		updateInfo.setUpdateVersion(updateVersion);
		return updateInfo;
	}
	
	public static final SQLDataExecuteInfo getInstance(List<String> sqlPaths,String updateVersion){
		SQLDataExecuteInfo updateInfo = new SQLDataExecuteInfo();
		updateInfo.setSqlPaths(sqlPaths);
		updateInfo.setUpdateVersion(updateVersion);
		return updateInfo;
	}

	public List<String> getSqlPaths() {
		return sqlPaths;
	}

	public void setSqlPaths(List<String> sqlPaths) {
		this.sqlPaths = sqlPaths;
	}
	
	public List<String> getProcedurePaths() {
		return procedurePaths;
	}

	public void setProcedurePaths(List<String> procedurePaths) {
		this.procedurePaths = procedurePaths;
	}

	public String getUpdateVersion() {
		return updateVersion;
	}

	public void setUpdateVersion(String updateVersion) {
		this.updateVersion = updateVersion;
	}

	

	
}
