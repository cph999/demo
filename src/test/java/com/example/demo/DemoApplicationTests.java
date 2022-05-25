package com.example.demo;

import com.example.demo.entity.OcCourseSection;
import com.example.demo.mapper.OcCourseMapper;
import com.example.demo.mapper.OcCourseSectionMapper;
import com.example.demo.util.RedisUtil;
import com.example.demo.vo.OnCourseVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    OcCourseMapper courseMapper;

    @Autowired
    OcCourseSectionMapper sectionMapper;
    @Test
    void contextLoads() {
        OnCourseVo onCourseVo = new OnCourseVo();
        onCourseVo.setCourseId(1);
        List<OcCourseSection> sectionsByCourse = sectionMapper.getSectionsByCourse(onCourseVo);
        sectionsByCourse.forEach(System.out::println);
    }

}
