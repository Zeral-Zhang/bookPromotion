package com.zeral.action.impl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.opensymphony.xwork2.ActionSupport;
import com.zeral.exception.BaseException;
import com.zeral.po.UserInfo;
/**
 * 基础通用Action 
 * 
 * @author Zeral
 * @date 2016-10-17
 *
 */
public class BaseAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	protected final Logger log = Logger.getLogger(this.getClass());
	protected UserInfo user;
	
	protected HttpSession getSession() {
		HttpSession session = null;
		try {
			session = getRequest().getSession();
		} catch (Exception e) {
			throw new BaseException("获取session失败");
		}
		return session;
	}

	protected HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest();
	}
	
	protected ServletContext getServletContext() {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();   

        ServletContext servletContext = webApplicationContext.getServletContext();
		return servletContext;
	}


	public UserInfo getLoginUser() {
		return (UserInfo) getSession().getAttribute("userInfo");
	}

	public void setLoginUser(UserInfo user) {
		getSession().setAttribute("userInfo", user);
	}
}
