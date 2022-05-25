package com.example.demo;

import com.example.demo.entity.OcCourse;
import com.example.demo.mapper.OcCourseMapper;
import com.example.demo.mapper.TbMenuMapper;
import com.example.demo.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    TbMenuMapper menuMapper;

    @Autowired
    OcCourseMapper courseMapper;
    @Test
    void contextLoads() {
        List<OcCourse> l = courseMapper.getCourseBySubjectTitle("2");
        System.out.println(l);
    }

}
