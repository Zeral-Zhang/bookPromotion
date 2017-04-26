package com.zeral.action.impl;

import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zeral.action.ICoreAction;
import com.zeral.service.IBasicConfigService;
import com.zeral.service.ICoreService;
import com.zeral.util.PropertiesConfigUtil;
import com.zeral.util.SignUtil;
import com.zeral.util.WebUtil;

import net.sf.json.JSONObject;

@Controller
public class CoreAction implements ICoreAction {
	@Autowired
	private ICoreService coreService;
	@Autowired
	private IBasicConfigService basicConfigService;

	@RequestMapping(value = "/handleCore")
	public void handle(HttpServletResponse response) {
		String method = WebUtil.getRequest().getMethod();
		try {
			if ("POST".equalsIgnoreCase(method)) {
				coreService.handleRequest(response);
			} else {
				coreService.checkSignature(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@RequestMapping(value = "/getBasicConfig")
	public void getBasicConfig(HttpServletRequest request, HttpServletResponse response) {
		try {
			Properties prop = PropertiesConfigUtil.getProperties("account.properties");
			String url = request.getParameter("url");
			String nonce = UUID.randomUUID().toString();
			String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
			String signature = SignUtil.generateSign(basicConfigService.getValidJsapiTicket().getToken(), timeStamp, nonce, url);
			JSONObject object = new JSONObject();
			object.put("nonceStr", nonce);
			object.put("timestamp", timeStamp);
			object.put("signature", signature);
			object.put("appid", prop.getProperty("appid"));
			WebUtil.sendJSONObjectResponse(object, response);
		} catch (Exception e) {
			WebUtil.sendErrorMsg("获取配置错误", response);
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "")
	public String toIndex() {
		return "redirect:/index.jsp";
	}
}
