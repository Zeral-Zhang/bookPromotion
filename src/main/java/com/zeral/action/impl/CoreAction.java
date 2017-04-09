package com.zeral.action.impl;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.zeral.action.ICoreAction;
import com.zeral.service.ICoreService;

@Controller
public class CoreAction implements ICoreAction {
	@Autowired
	private ICoreService coreService;

	public void handle() {
		String method = ServletActionContext.getRequest().getMethod();
		try {
			if ("POST".equalsIgnoreCase(method)) {
				coreService.handleRequest();
			} else {
				coreService.checkSignature();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
