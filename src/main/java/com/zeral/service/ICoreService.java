package com.zeral.service;

import javax.servlet.http.HttpServletResponse;

public interface ICoreService {
	/**
	 * 开发者Token验证
	 *
	 */
	public void checkSignature(HttpServletResponse response);
	
	/**
	 * 请求处理
	 * 
	 */
	public void handleRequest(HttpServletResponse response);
}
