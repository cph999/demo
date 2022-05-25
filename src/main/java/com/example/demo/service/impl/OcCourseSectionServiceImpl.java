package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.OcCourseSection;
import com.example.demo.mapper.OcCourseSectionMapper;
import com.example.demo.service.OcCourseSectionService;
import com.example.demo.vo.OnCourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程章节表 服务实现类
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
@Service
public class OcCourseSectionServiceImpl extends ServiceImpl<OcCourseSectionMapper, OcCourseSection> implements OcCourseSectionService {

    @Autowired
    OcCourseSectionMapper sectionMapper;
    @Override
    public List<OcCourseSection> getSectionsByCourse(OnCourseVo courseVo) {
        return sectionMapper.getSectionsByCourse(courseVo);
    }

}
