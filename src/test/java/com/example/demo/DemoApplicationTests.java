package com.example.demo;

import com.example.demo.mapper.OcCourseSectionMapper;
import com.example.demo.service.OcCourseService;
import com.example.demo.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    OcCourseService courseService;


    @Autowired
    OcCourseSectionMapper sectionMapper;
    @Test
    void contextLoads() {
//        OnCourseVo onCourseVo = new OnCourseVo();
//        onCourseVo.setCourseId(1);
//        sectionMapper.getSectionsByCourse(new OnCourseVo(),1,10);
    }

}
