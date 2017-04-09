package com.zeral.service;

import com.zeral.bean.DatabaseInitInfo;
import com.zeral.bean.SQLDataInitInfo;

/**
 * 数据库的初始化
 */
public interface IDatabaseInitService {
	
	/**
	 * 初始化数据库，如建表和视图，更新数据库表和视图等
	 * @param initInfo
	 */
	void initDatabase(DatabaseInitInfo initInfo);
	
	/**
	 * 在程序初始化完成后执行sql初始化
	 * 需在sql/SQLDataInitInfo.xml中配置
	 * @param sqlDataInitInfo
	 */
	void initSQLData(SQLDataInitInfo sqlDataInitInfo);
}
