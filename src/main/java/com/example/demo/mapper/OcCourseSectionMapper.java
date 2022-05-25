package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.OcCourseSection;
import com.example.demo.vo.OnCourseVo;

import java.util.List;

/**
 * <p>
 * 课程章节表 Mapper 接口
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
public interface OcCourseSectionMapper extends BaseMapper<OcCourseSection> {
    /**
     * 根据文章course_id查询其章节section
     * @param courseVo 文章course_id
     * @return 章节
     */
    List<OcCourseSection> getSectionsByCourse(OnCourseVo courseVo);
}
