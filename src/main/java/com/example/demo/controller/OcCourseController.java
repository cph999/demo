package com.example.demo.controller;


import com.example.demo.constant.CommonResult;
import com.example.demo.entity.OcCourse;
import com.example.demo.service.OcCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/oc-course")
public class OcCourseController {
    @Autowired
    OcCourseService ocCourseService;
    @RequestMapping("/test")
    public CommonResult getCourse(){
        OcCourse ocCourse = ocCourseService.getBaseMapper().selectById(1);
        return new CommonResult(200,"message",ocCourse);
    }
}

