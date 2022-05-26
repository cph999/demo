package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 课程
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OcCourse implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 课程id 主键
     */
    @TableId(value = "course_id", type = IdType.AUTO)
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
    private String courseBanner;

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

    /**
     * 乐观锁
     */
    @TableField("REVISION")
    private Integer revision;

    /**
     * 创建人
     */
    @TableField("CREATED_BY")
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField("CREATED_TIME")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    @TableField("UPDATED_BY")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private String updatedBy;

    /**
     * 更新时间
     */
    @TableField("UPDATED_TIME")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedTime;


}
