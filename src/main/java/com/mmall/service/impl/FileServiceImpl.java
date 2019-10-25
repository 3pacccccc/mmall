package com.mmall.service.impl;

import com.mmall.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileServiceImpl implements IFileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);   // 获取文件名的后缀名，例如abc.jpg， fileName.lastIndexOf(".")可以得到.jpg, +1可以得到jpg
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName; // uuid + 后缀名
        logger.info("开始上传文件，上传文件名：{}，上传路径名：{}，新文件名：{}。", fileName, path, uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();        // dirs可以创建多级文件/a/b/c
        }

        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
        }catch (IOException e){
            logger.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }
}
