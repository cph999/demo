package com.example.demo.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 关联关系表专题和课程
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OcSubjectCourse implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 专题id 专题id
     */
    private Integer subjectId;

    /**
     * 课程id 课程id
     */
    private Integer courseId;


}
