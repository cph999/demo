package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.OcCourse;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
public interface OcCourseService extends IService<OcCourse> {
    /**
     * 通过subject标题查询课程信息
     * @param title subject标题
     * @return course集合
     */
    List<OcCourse> getCourseBySubjectTitle(String title);
}
