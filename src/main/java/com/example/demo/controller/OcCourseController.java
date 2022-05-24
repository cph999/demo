package com.example.demo.controller;


import com.example.demo.constant.CommonResult;
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
    @RequestMapping("/test")
    public CommonResult getCourse(){
        return new CommonResult(200,"message",new String("1"));
    }
}

