package com.zeral.action.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zeral.action.IRegionAction;
import com.zeral.exception.BaseException;
import com.zeral.po.Regions;
import com.zeral.service.IRegionService;
import com.zeral.util.WebUtil;

@Controller
public class ReginAction implements IRegionAction {
	@Autowired
	private IRegionService regionService;

	@Override
	@RequestMapping(value="/initProvince", method = RequestMethod.GET)
	public void initProvince(HttpServletResponse response) {
		try {
			List<Regions> provincelst = regionService.findProvince();
			WebUtil.sendJSONArrayResponse(provincelst, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@RequestMapping(value="/loadCitys", method = RequestMethod.GET)
	public void loadCitys(HttpServletResponse response) {
		try {
			String fcode = WebUtil.getRequest().getParameter("code");
			if (!"".equals(fcode)) {
				Integer fcodeInt = 0;
				try {
					fcodeInt = Integer.parseInt(fcode);
				} catch (NumberFormatException e) {
					fcodeInt = 1;
				}
				List<Regions> citylst = regionService.findCitys(fcodeInt);
				WebUtil.sendJSONArrayResponse(citylst, response);
			}
		} catch (Exception e) {
			throw new BaseException("加载城市信息失败");
		}
	}

}
