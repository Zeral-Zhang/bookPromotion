package com.zeral.service;

import java.io.File;

import com.zeral.po.FileInfo;

public interface IFileInfoService extends IBaseService<FileInfo> {

	public abstract File uploadFile(File paramFile, String paramString2);

	public abstract File uploadAndSaveFile(String paramString, File paramFile, FileInfo paramFileInfo,
			boolean paramBoolean);

	public abstract File uploadAndSaveFile(String paramString, File paramFile, FileInfo paramFileInfo);

	public abstract void delFile(String fileInfoId);
}
