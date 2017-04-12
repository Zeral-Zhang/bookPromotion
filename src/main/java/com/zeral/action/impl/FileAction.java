package com.zeral.action.impl;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	private static final long serialVersionUID = 1L;
	
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
	
	public void delFile(FileInfo fileInfo) {
		try {
			fileInfoService.delFile(fileInfo.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}
}
