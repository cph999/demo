package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.annotation.Log;
import com.example.demo.entity.OcCourse;
import com.example.demo.service.OcCourseSectionService;
import com.example.demo.service.OcCourseService;
import com.example.demo.util.RedisUtil;
import com.example.demo.vo.CommonResult;
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
    @Autowired
    OcCourseSectionService sectionService;
    @Autowired
    RedisUtil redisUtil;

    @Log
    @RequestMapping(value = "/course/get/{pageNum}/{pageSize}")
    public CommonResult getCourse(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize){
        QueryWrapper<OcCourse> w = new QueryWrapper<>();
        QueryWrapper<OcCourse> wrapper = w.isNotNull("course_id");

        Page<OcCourse> page = new Page<>(pageNum,pageSize);
        Page<OcCourse> ocCoursePage = ocCourseService.getBaseMapper().selectPage(page, wrapper);
        return new CommonResult(200,"message",ocCoursePage.getRecords());
    }

    @Log
    @RequestMapping(value = "/course/detail/{courseid}")
    public CommonResult getCourseDetail(@PathVariable("courseid") Integer courseId){
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
     * 根据subject_title查询course,每次查询之前先看redis
     * @param subjectVo
     * @return
     */
    @Log
    @PostMapping(value = "/subject/list")
    public CommonResult getSubjectCourse(@RequestBody SubjectVo subjectVo){
        if(subjectVo == null) return new CommonResult(400,"Bad Request",subjectVo);
        String title = subjectVo.getSubject_title();
        List<OcCourse> courses = (List<OcCourse>) redisUtil.get("title:" + title);
        List<OcCourse> courseList = null;
        if(courses == null){
            if(title!=null){
                System.out.println("缓存未命中");
                courseList = ocCourseService.getCourseBySubjectTitle(title);
                redisUtil.set("title:" + title,courseList);
            }
        }
        courseList = courses;
        return new CommonResult(200,"message",courseList);
    }

    /**
     * 新增课程
     * @param course
     * @return
     */
    @Log
    @RequestMapping("course/add")
    public CommonResult addCourse(@RequestBody OcCourse course){
        boolean save = ocCourseService.save(course);
        if(save){
            return new CommonResult(200,"添加成功",null);
        }
        return new CommonResult(200,"添加失败",null);
    }
    @Log
    @RequestMapping("course/delete/")
    public CommonResult deleteCourse(@RequestBody List<Integer> courseIds){
            if(courseIds != null) {
               for(Integer id:courseIds){
                   int i = ocCourseService.getBaseMapper().deleteById(id);
               }
                return new CommonResult(200,"删除成功"+courseIds.size()+"条数据",null);
            }

        return new CommonResult(403,"错误请求",null);
    }
    @Log
    @RequestMapping("course/edit")
    public CommonResult editCourse(@RequestBody OcCourse course){
        if(course == null)  return new CommonResult(400,"错误请求",null);
        boolean b = ocCourseService.updateById(course);
        if(b){
            return new CommonResult(200,"success",null);
        }
        return new CommonResult(400,"错误请求",null);
    }
}

