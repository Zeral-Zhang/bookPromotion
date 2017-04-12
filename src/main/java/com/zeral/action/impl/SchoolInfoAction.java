package com.zeral.action.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zeral.action.ISchoolInfoAction;
import com.zeral.bean.PageBean;
import com.zeral.po.ProductInfo;
import com.zeral.po.SchoolInfo;
import com.zeral.service.IProductInfoService;
import com.zeral.service.ISchoolInfoService;
import com.zeral.util.WebUtil;

/**
 * 院系信息action
 * 
 * @author ZeralZhang
 *
 */
@Controller
public class SchoolInfoAction implements ISchoolInfoAction {
	
	@Autowired
	private ISchoolInfoService schoolInfoService;
	@Autowired
	private IProductInfoService productInfoService;
	
	@Override
	@RequestMapping(value="/loadDepartments/{code}", method = RequestMethod.GET)
	public void loadDepartments(@PathVariable String code, HttpServletResponse response) {
		try {
			List<SchoolInfo> schoolInfolst = schoolInfoService.findByCollegeId(code);
			WebUtil.sendJSONArrayResponse(schoolInfolst, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@RequestMapping(value="/toDiscovery", method = RequestMethod.GET)
	public String toDiscovery(Model model) {
		try {
			List<SchoolInfo> schoolInfolst = schoolInfoService.findColleges();
			model.addAttribute("schoolInfolst", schoolInfolst);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "discovery";
	}
	
	@Override
	@RequestMapping(value="/toDiscovery/{schoolInfoId}", method = RequestMethod.GET)
	public String toSchoolInfoProduct(PageBean pageBean, String search, @PathVariable String schoolInfoId, Model model) {
		try {
			List<ProductInfo> lsemp = null;
			if(null != search) {
				/*lsemp = productInfoService.findByUserSchoolInfoIdAndNameLike(pageBean, search);*/
			} else {
				// 获取当前页的记录集合
				lsemp = productInfoService.findByUserSchoolInfoId(pageBean, schoolInfoId);
			}
			// 封装数据到PageBean
			pageBean.setPagelist(lsemp);
			model.addAttribute(pageBean);
			return "collegeProductList";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
