package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.OcCourse;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
public interface OcCourseMapper extends BaseMapper<OcCourse> {
    /**
     * 通过subject标题查询课程信息
     * @param title subject标题
     * @return course集合
     */
    List<OcCourse> getCourseBySubjectTitle(String title);
}
