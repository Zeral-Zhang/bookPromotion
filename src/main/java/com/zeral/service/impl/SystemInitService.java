package com.zeral.service.impl;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zeral.bean.DatabaseInitInfo;
import com.zeral.bean.SQLDataInitInfo;
import com.zeral.service.IBasicConfigService;
import com.zeral.service.IDatabaseInitService;
import com.zeral.util.XMLUtil;

/**
 * 系统初始化Service
 */
@Service
public abstract class SystemInitService {

	private static final Logger log = Logger.getLogger(SystemInitService.class);
	
	@Autowired
	protected IBasicConfigService basicConfigService;
	
	@Autowired
	protected IDatabaseInitService databaseInitService;

	/**
	 * 初始化方法
	 * 
	 * @param beanName
	 * @param context
	 */
	public static final void initialized(final String beanName, final ServletContext context) {
		try {
			final WebApplicationContext applicationContext = WebApplicationContextUtils
					.getWebApplicationContext(context);
			final SystemInitService initService = (SystemInitService) applicationContext
					.getBean(beanName, SystemInitService.class);
			initService.init(context);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 初始化数据库
	 * 
	 */
	private void initDatabase() {
		final DatabaseInitInfo databaseInitInfo = XMLUtil.getXMLRoot(
				DatabaseInitInfo.class, "sql/DatabaseInitInfo.xml");
		databaseInitService.initDatabase(databaseInitInfo);
	}

	/**
	 * 初始化数据
	 * 
	 */
	protected abstract void initData();

	
	private void initSQLData() {
		SQLDataInitInfo sqlDataInit;
		try {
			sqlDataInit = XMLUtil.getXMLRoot(
					SQLDataInitInfo.class, "sql/SQLDataInitInfo.xml");
		} catch (Exception e) {
			return;
		}
		databaseInitService.initSQLData(sqlDataInit);
	}

	/**
	 * 初始化
	 * 
	 */
	private void init(final ServletContext context) {
		initDatabase();
		initData();
		/**
		 * 程序初始化完成之后执行的sql
		 */
		initSQLData();
		// setSystemProperties(context);
	}

/*	*//**
	 * 设置系统属性
	 * 
	 *//*
	protected void setSystemProperties(final ServletContext context) {
		setTheme(context);
		setSyFileWebRoot(context);
		setCasServerUrlPrefix(context);
		setBaseUrl(context);		
	}

	*//**
	 * 设置系统UI主题
	 *//*
	private void setTheme(final ServletContext context) {
		context.setAttribute(CommonConstants.THEME, "standard");
	}

	*//**
	 * 设置附件服务器web root
	 * @param context
	 *//*
	private void setSyFileWebRoot(final ServletContext context) {
		final String syFileUrl = systemConfigService.getSyFileWebRoot();
		context.setAttribute(CommonConstants.FILE_URL, syFileUrl);
	}

	*//**
	 * 设置CAS Server url
	 *//*
	private void setCasServerUrlPrefix(final ServletContext context) {
		final String casServerUrlPrefix = basicConfigService.findValueById(
				CommonConstants.CAS_SERVER_URL_PREFIX_KEY);
		context.setAttribute(CommonConstants.CAS_SERVER_URL_PREFIX_KEY,
				casServerUrlPrefix);
	}

	*//**
	 * 设置基础平台URL
	 *//*
	private void setBaseUrl(final ServletContext context) {
		context.setAttribute(CommonConstants.BASE_SERVER_NAME, systemConfigService
				.getBaseServerName());
		context.setAttribute(CommonConstants.BASE_URL, systemConfigService
				.getBaseWebRoot());
	}*/

}
