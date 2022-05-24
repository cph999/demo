package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程章节表
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OcCourseSection implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 章节id 章节id
     */
    @TableId(value = "section_id", type = IdType.AUTO)
    private Integer sectionId;

    /**
     * 课程id 课程
     */
    private Integer courseId;

    /**
     * 章节名称 章节名称
     */
    private String sectionName;

    /**
     * 章节类型 章节类型0父章节1字章节
     */
    private String sectionType;

    /**
     * 章节视频 章节视频关联文件表
     */
    private Integer sectionVideo;

    /**
     * 试看，0不可以1可以默认0
     */
    private String canTry;

    /**
     * 章节序列 章节序列
     */
    private Integer orderBy;

    /**
     * 父章节id 父章节id
     */
    private Integer parentId;

    /**
     * 备注 备注
     */
    private String remark;

    /**
     * 删除标志 删除标志
     */
    private String delFlag;

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


}
