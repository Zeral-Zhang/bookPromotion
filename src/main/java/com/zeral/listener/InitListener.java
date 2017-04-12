package com.zeral.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.zeral.service.impl.SystemInitService;


public class InitListener implements ServletContextListener {
	private final Logger log = Logger.getLogger(this.getClass());
	
	public InitListener() {
	}

	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
	}

	public void contextInitialized(ServletContextEvent event) {
		try {
			SystemInitService.initialized("SystemInitService", event.getServletContext());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("初始化失败");
		}
	}
	
}
