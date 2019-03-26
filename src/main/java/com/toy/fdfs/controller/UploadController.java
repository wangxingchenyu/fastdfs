package com.toy.fdfs.controller;

import com.toy.fdfs.fdfs.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UploadController {
    @Value("${fastdfs.base.url}")
    private String FASTDFS_BASE_URL;

    @Autowired
    private StorageService storageService;

    /**
     * 文件上传
     *
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String upload(MultipartFile file) {
        String s = writeFile(file);
        return s;
    }

    /**
     * 将图片写入指定目录
     *
     * @param multipartFile
     * @return 返回文件完整路径
     */
    private String writeFile(MultipartFile multipartFile) {
        // 获取文件后缀
        String oName = multipartFile.getOriginalFilename();
        String extName = oName.substring(oName.lastIndexOf(".") + 1);

        // 文件存放路径
        String url = null;
        try {
            String uploadUrl = storageService.upload(multipartFile.getBytes(), extName);
            url = FASTDFS_BASE_URL + uploadUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 返回文件完整路径
        return url;
    }
}
