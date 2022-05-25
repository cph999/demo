package com.example.demo.controller;


import com.example.demo.annotation.Log;
import com.example.demo.constant.CommonResult;
import com.example.demo.entity.OcCourse;
import com.example.demo.service.OcCourseService;
import com.example.demo.vo.SubjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
@RestController
//@RequestMapping("/api/pc")
public class OcCourseController {
    @Autowired
    OcCourseService ocCourseService;

    @Log
    @RequestMapping(value = "/course/detail/{courseid}")
    public CommonResult getCourse(@PathVariable("courseid") Integer courseId){
        if(courseId == null) return new CommonResult(400,"Bad Request",courseId);

        OcCourse ocCourse = ocCourseService.getBaseMapper().selectById(courseId);
        System.out.println(ocCourse);
        return new CommonResult(200,"message",ocCourse);
    }

    /**
     * 根据课程id查询评论
     * @param courseId
     * @param pageNum
     * @param size
     * @return
     */

    @Log
    @RequestMapping(value = "/comment/commentList/course/{courseId}")
    public CommonResult getCourseComment(@PathVariable("courseId") Integer courseId,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer size){
        if(courseId == null) return new CommonResult(400,"Bad Request",courseId);

        OcCourse ocCourse = ocCourseService.getBaseMapper().selectById(courseId);
        System.out.println(ocCourse);
        return new CommonResult(200,"message",ocCourse);
    }

    /**
     * 根据subject_title查询course
     * @param subjectVo
     * @return
     */
    @Log
    @PostMapping(value = "/subject/list")
    public CommonResult getSubjectCourse(@RequestBody SubjectVo subjectVo){
        if(subjectVo == null) return new CommonResult(400,"Bad Request",subjectVo);
        String title = subjectVo.getSubject_title();
        List<OcCourse> courseList = null;
        if(title!=null){
            courseList = ocCourseService.getCourseBySubjectTitle(title);
        }
        return new CommonResult(200,"message",courseList);
    }
}

