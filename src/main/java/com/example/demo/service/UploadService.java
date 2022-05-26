package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p></p>
 *
 * @author gqz20
 * @create 2022-04-25 14:16
 **/
public interface UploadService {
    /**
     * Upload interface.
     *
     * @param file the file
     * @return the string
     */
    String upload(MultipartFile file);
}
