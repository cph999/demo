package com.example.demo.controller;


import com.example.demo.constant.CommonResult;
import com.example.demo.entity.OcCourse;
import com.example.demo.service.OcCourseService;
import com.example.demo.vo.OnCourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/api/pc")
public class OcCourseController {
    @Autowired
    OcCourseService ocCourseService;
    @RequestMapping(value = "/course/detail")
    public CommonResult getCourse(@RequestBody OnCourseVo onCourseVo){
        if(onCourseVo == null) return new CommonResult(400,"Bad Request",onCourseVo);

        OcCourse ocCourse = ocCourseService.getBaseMapper().selectById(onCourseVo.getCourseId());
        System.out.println(ocCourse);
        return new CommonResult(200,"message",ocCourse);
    }
}

