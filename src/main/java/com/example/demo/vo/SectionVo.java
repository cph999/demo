package com.example.demo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionVo {
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
}
