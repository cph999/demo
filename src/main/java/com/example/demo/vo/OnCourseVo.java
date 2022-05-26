package com.example.demo.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
public class OnCourseVo {

    private Integer  pageNum;
    private Integer pageSize;
    private String special;
    /**
     * 课程id 主键
     */
    private Integer courseId;

    /**
     * 课程标题 课程标题
     */
    private String courseTitle;

    /**
     * 课程副标题 课程副标题
     */
    private String courseSubtitle;

    /**
     * 课程描述 课程描述
     */
    private String courseDesc;

    /**
     * 详情 课程详情
     */
    private String courseDetail;

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

    /**
     * 打折表述 打折描述
     */
    private String discountDesc;

    /**
     * 折后价 折后价
     */
    private BigDecimal discountPrice;

    /**
     * 课程难度 课程难度，关联数据字典
     */
    private String courseLevel;

    /**
     * 参与人数 参与人数
     */
    private Integer participationsCount;

    /**
     * 课程封面 列表界面课程的展示，和文件表关联
     */
    private Integer courseCover;

    /**
     * 课程轮播图 banner图
     */
    private Integer courseBanner;

    /**
     * 是否轮播 是否进行轮播
     */
    private String isCarousel;

    /**
     * 是否精品推荐 是否精品推荐
     */
    private String isRecommend;

    /**
     * 排序字段 排序字段
     */
    private Integer orderBy;

    /**
     * 课程状态 课程状态
     */
    private String courseStatus;

    /**
     * 是否展示图片详情
     */
    private String showDetailImg;

    /**
     * 图片详情id
     */
    private Integer detailImg;

    /**
     * 是否启用 是否启用
     */
    private String enable;

    /**
     * 是否删除 是否删除
     */
    private String delFlag;

    /**
     * remark 备注说明
     */
    private String remark;
}
