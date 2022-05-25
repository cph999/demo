package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.OcCourseSection;
import com.example.demo.vo.OnCourseVo;

import java.util.List;

/**
 * <p>
 * 课程章节表 服务类
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
public interface OcCourseSectionService extends IService<OcCourseSection> {
    /**
     * 根据文章course_id查询其章节section
     * @param id 文章course_id
     * @return 章节
     */
    List<OcCourseSection> getSectionsByCourse(OnCourseVo courseVo);
}
