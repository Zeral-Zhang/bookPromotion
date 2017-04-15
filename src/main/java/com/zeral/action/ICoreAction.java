package com.zeral.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 核心请求处理，包括开发者授权，消息处理
 * @author ZeralZhang
 *
 */
public interface ICoreAction {

	/**
	 * 处理所有请求，包括消息处理，基本请求处理
	 * @return
	 */
	public void handle(HttpServletResponse response);
	
	/**
	 * 获取调用js凭证Ticket等配置信息
	 */
	public void getBasicConfig(HttpServletRequest request, HttpServletResponse response);
}
