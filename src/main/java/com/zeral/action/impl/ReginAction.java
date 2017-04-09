package com.zeral.action.impl;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
	public void initProvince() {
		try {
			List<Regions> provincelst = regionService.findProvince();
			WebUtil.sendJSONArrayResponse(provincelst);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadCitys() {
		try {
			String fcode = ServletActionContext.getRequest().getParameter("code");
			if (!"".equals(fcode)) {
				Integer fcodeInt = 0;
				try {
					fcodeInt = Integer.parseInt(fcode);
				} catch (NumberFormatException e) {
					fcodeInt = 1;
				}
				List<Regions> citylst = regionService.findCitys(fcodeInt);
				WebUtil.sendJSONArrayResponse(citylst);
			}
		} catch (Exception e) {
			throw new BaseException("加载城市信息失败");
		}
	}

}
