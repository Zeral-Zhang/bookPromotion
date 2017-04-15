package com.zeral.action.impl;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.zeral.po.FileInfo;
import com.zeral.service.IFileInfoService;
import com.zeral.util.WebUtil;

/**
 * 处理文件action
 * 
 * @author Zeral
 *
 */
@Controller
public class FileAction extends BaseAction {
	
	@Autowired
	private IFileInfoService fileInfoService ;

	@RequestMapping(value = "/uploadFile")
	public void uploadFile(String uploadFileName, FileInfo fileInfo, MultipartFile upload, HttpServletResponse response) {
		try {
			System.out.println("=========开始上传文件======================================");
			fileInfoService.uploadAndSaveFile(uploadFileName, upload, fileInfo);
			
			String result = fileInfo.getId() + ":" + fileInfo.getName() + ":" + fileInfo.getPath();
			  
			WebUtil.sendResponse(result, response);
			System.out.println("==========文件上传完毕======================================");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}
	
	@RequestMapping(value = "/delFile/{fileId}", method = RequestMethod.GET)
	public void delFile(@PathVariable String fileId, HttpServletResponse response) {
		try {
			if(StringUtils.isNotBlank(fileId)) {
				fileInfoService.delFile(fileId);
			}
			WebUtil.sendSuccessMsg("刪除成功", response);
		} catch (Exception e) {
			WebUtil.sendErrorMsg("删除失败", response);
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}
}
