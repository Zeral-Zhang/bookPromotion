package com.zeral.action.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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

	private File upload;
	private String uploadFileName;
	private FileInfo fileInfo;

	public void uploadFile() {
		try {
			log.info("=========开始上传文件======================================");
			fileInfoService.uploadAndSaveFile(this.uploadFileName, this.upload, this.fileInfo);
			
			String result = this.fileInfo.getId() + ":" + this.fileInfo.getName() + ":" + this.fileInfo.getPath();
			  
			WebUtil.sendResponse(result);
			log.info("==========文件上传完毕======================================");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}
	
	public void delFile() {
		try {
			fileInfoService.delFile(fileInfo.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

}
