package com.zeral.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zeral.constant.BookPromotionConstant;
import com.zeral.dao.BaseDao;
import com.zeral.dao.FileInfoDao;
import com.zeral.po.FileInfo;
import com.zeral.service.IFileInfoService;
import com.zeral.util.FileUtil;

@Service
public class FileInfoServiceImpl extends BaseServiceImpl<FileInfo> implements IFileInfoService {
	
	private static final Logger log = Logger.getLogger(FileInfoServiceImpl.class);
	
	@Autowired
	private FileInfoDao fileInfoDao;

	public void save(FileInfo fileInfo) {
		try {
			fileInfo.setPublishTime(new Date(System.currentTimeMillis()));
			this.fileInfoDao.save(fileInfo);
			log.info("保存文件id:" + fileInfo.getId() + "name:" + fileInfo.getName() + "成功");
		} catch (Exception e) {
			log.error("保存文件失败", e);
			throw e;
		}
	}
	
	public void delFile(String fileInfoId) {
		try {
			this.fileInfoDao.delete(fileInfoId);
			log.info("删除文件id:" + fileInfoId + "成功");
		} catch (Exception e) {
			log.error("删除文件失败", e);
			throw e;
		}
	}

	public File uploadFile(MultipartFile source, String fileInfoName) {
		try {
			File destFile = FileUtil.getDestFile(fileInfoName);
			FileUtils.copyInputStreamToFile(source.getInputStream(), destFile);
			return destFile;
		} catch (IOException e) {
			log.error("上传文件失败", e);
			return null;
		}
	}

	public File uploadAndSaveFile(String uploadFileName, MultipartFile source, FileInfo fileInfo, boolean isVideo) {
		String id = UUID.randomUUID().toString();
		fileInfo.setId(id);
		String suffix = FileUtil.getFileSuffix(uploadFileName);
		File destFile = uploadFile(source, id + suffix);
		if (isVideo) {
			suffix = ".flv";
		}
		String relativeFilePath = FileUtil.getRelativeFilePath(id + suffix);
		fileInfo.setName(uploadFileName);
		fileInfo.setPath(BookPromotionConstant.UPLOAD_URL + relativeFilePath);
		fileInfo.setStatus(Integer.valueOf(0));
		fileInfo.setFileSize(Double.valueOf(source.getSize() / 1024L));
		fileInfo.setOriginalFormat(FileUtil.rtnFileType(uploadFileName));
		save(fileInfo);
		return destFile;
	}

	public File uploadAndSaveFile(String uploadFileName, MultipartFile source, FileInfo fileInfo) {
		return uploadAndSaveFile(uploadFileName, source, fileInfo, false);
	}

	@Override
	public BaseDao<FileInfo, String> getDao() {
		return fileInfoDao;
	}

}
