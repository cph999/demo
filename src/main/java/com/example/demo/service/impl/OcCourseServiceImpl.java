package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.OcCourse;
import com.example.demo.mapper.OcCourseMapper;
import com.example.demo.service.OcCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
@Service
public class OcCourseServiceImpl extends ServiceImpl<OcCourseMapper, OcCourse> implements OcCourseService {

    @Autowired
    OcCourseMapper ocCourseMapper;
    @Override
    public List<OcCourse> getCourseBySubjectTitle(String title) {
        return ocCourseMapper.getCourseBySubjectTitle(title);
    }
}
