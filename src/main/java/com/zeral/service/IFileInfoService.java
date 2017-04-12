package com.zeral.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.zeral.po.FileInfo;

public interface IFileInfoService extends IBaseService<FileInfo> {

	public abstract File uploadFile(MultipartFile paramFile, String paramString2);

	public abstract File uploadAndSaveFile(String paramString, MultipartFile paramFile, FileInfo paramFileInfo,
			boolean paramBoolean);

	public abstract File uploadAndSaveFile(String paramString, MultipartFile paramFile, FileInfo paramFileInfo);

	public abstract void delFile(String fileInfoId);
}
