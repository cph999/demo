package com.example.demo.vo;

import lombok.Data;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
public class OnCourseVo {

    /**
     * 课程id 主键
     */
    private Integer courseId;
    /**
     * 课程标题 课程标题
     */
    private String courseTitle;

    /**
     * 是否免费
     */
    private String isFree;
    /**
     * 课程价格 课程价格
     */
    private BigDecimal coursePrice;

    /**
     * 是否打折 是否打折
     */
    private String isDiscount;
}
