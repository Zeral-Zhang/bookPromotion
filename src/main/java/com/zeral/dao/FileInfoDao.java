package com.zeral.dao;

import org.springframework.stereotype.Repository;

import com.zeral.po.FileInfo;

@Repository("FileInfoDao")
public class FileInfoDao extends BaseDao<FileInfo, String> {
}