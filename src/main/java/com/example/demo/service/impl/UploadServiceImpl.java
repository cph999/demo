package com.example.demo.service.impl;

import com.example.demo.config.FastDfsConstant;
import com.example.demo.exception.PersistenceException;
import com.example.demo.service.UploadService;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * <p></p>
 *
 * @author gqz20
 * @create 2022-04-25 14:18
 **/
@Service
public class UploadServiceImpl implements UploadService {
    private final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);
    private final List<String> CONTENT_TYPES = Arrays.asList("image/jpeg", "image/gif", "image/png", "image/jpg");
    /**
     * 远程fastdfs客户端
     */
    private FastFileStorageClient storageClient;
    private FastDfsConstant fastDfsConstant;

    @Autowired
    public UploadServiceImpl(FastFileStorageClient storageClient, FastDfsConstant fastDfsConstant) {
        this.storageClient = storageClient;
        this.fastDfsConstant = fastDfsConstant;
    }


    @Override
    public String upload(MultipartFile file) {
        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();
        if (!CONTENT_TYPES.contains(contentType)) {
            logger.info("{}类型不合法，类型{}", originalFilename, contentType);
            throw new PersistenceException(String.format("%s类型不合法，类型%s", originalFilename, contentType));
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                logger.info("文件不合法：{}", originalFilename);
                throw new PersistenceException(String.format("文件不合法：%s", originalFilename));
            }

            String ext = StringUtils.substringAfterLast(originalFilename, ".");
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);
            String domain = fastDfsConstant.getDomain();
            return domain + "/" + storePath.getFullPath();

        } catch (IOException e) {
            logger.error("internal server error:{}", originalFilename);
            e.printStackTrace();
        }
        return null;
    }
}
