package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 专题表
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OcSubject implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键 专题主键
     */
    @TableId(value = "subject_id", type = IdType.AUTO)
    private Integer subjectId;

    /**
     * 专题名称 专题名称
     */
    private String subjectTitle;

    /**
     * 专题描述 专题描述
     */
    private String subjectDesc;

    /**
     * 专题轮播 专题的banner图
     */
    private String subjectBanner;

    /**
     * 排序字段 排序字段
     */
    private Integer orderBy;

    /**
     * 状态 专题状态
     */
    private String subjectStatus;

    /**
     * 删除标志 删除标志
     */
    private String delFlag;

    /**
     * remark 备注说名
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
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    @TableField("UPDATED_BY")
    private String updatedBy;

    /**
     * 更新时间
     */
    @TableField("UPDATED_TIME")
    private LocalDateTime updatedTime;

    /**
     * 是否启用

     */
    private Integer enable;


}
