package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p></p>
 *
 * @author gqz20
 * @create 2022-04-26 15:33
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "fastdfs")
public class FastDfsConstant {
    private String domain;
}
