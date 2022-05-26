package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.annotation.Log;
import com.example.demo.entity.OcCourse;
import com.example.demo.entity.OcSubjectCourse;
import com.example.demo.service.OcCourseSectionService;
import com.example.demo.service.OcCourseService;
import com.example.demo.service.OcSubjectCourseService;
import com.example.demo.util.RedisUtil;
import com.example.demo.vo.CommonResult;
import com.example.demo.vo.OnCourseVo;
import com.example.demo.vo.SubjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @Autowired
    OcSubjectCourseService subjectCourseService;

    @Log
    @RequestMapping(value = "/course/get")
    public CommonResult getCourse(@RequestBody OnCourseVo course){
        if(course == null) return new CommonResult(400,"msg",null);
        if(course.getPageNum() == null) course.setPageNum(1);
        if(course.getPageSize() == null) course.setPageSize(10);

        QueryWrapper<OcCourse> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("course_id")
                .like("course_title",course.getCourseTitle());
        List<OcCourse> lists = ocCourseService.getCourseBySubjectTitle(course.getSpecial());
        System.out.println("list:"+lists.size());
        lists.forEach(System.out::println);
        Page<OcCourse> page = new Page<>(course.getPageNum(), course.getPageSize());
        Page<OcCourse> pages = ocCourseService.getBaseMapper().selectPage(page, wrapper);
        List<OcCourse> result= new ArrayList<>();
        for(OcCourse course1:lists){
            if(pages.getRecords().contains(course1))result.add(course1);
        }
        pages.setRecords(result);
        return new CommonResult(200,"message",pages);
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
        String title = subjectVo.getSubjectTitle();
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
    @RequestMapping("/course/add/{sid}")
    public CommonResult addCourse(@RequestBody OcCourse course,@PathVariable("sid") Integer sid){
        boolean save = ocCourseService.save(course);
        subjectCourseService.save(new OcSubjectCourse(sid,course.getCourseId()));
        if(save){
            return new CommonResult(200,"添加成功",null);
        }
        return new CommonResult(200,"添加失败",null);
    }
    @Log
    @RequestMapping("course/delete")
    public CommonResult deleteCourse(@RequestBody List<Integer> courseIds){
            if(courseIds != null) {
               for(Integer id:courseIds){
                   QueryWrapper<OcSubjectCourse> wrapper = new QueryWrapper<OcSubjectCourse>().eq("course_id",id);
                   subjectCourseService.getBaseMapper().delete(wrapper);
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

