package com.example.demo.controller;


import com.example.demo.annotation.Log;
import com.example.demo.entity.OcCourseSection;
import com.example.demo.service.OcCourseSectionService;
import com.example.demo.vo.CommonResult;
import com.example.demo.vo.OnCourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 课程章节表 前端控制器
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
@RestController
public class OcCourseSectionController {
    @Autowired
    OcCourseSectionService sectionService;
    @Log
    @RequestMapping(value = "/course/section")
    public CommonResult getSectionsByCourseId(@RequestBody OnCourseVo courseVo){
        if(courseVo == null) return new CommonResult(400,"Bad Request", null);
        List<OcCourseSection> sectionsByCourse = sectionService.getSectionsByCourse(courseVo);
        return new CommonResult(200,"message",sectionsByCourse);
    }

    @Log
    @RequestMapping(value = "/course/section/add")
    public CommonResult addSections(@RequestBody List<OcCourseSection> sections){
        if(sections == null) return new CommonResult(400,"Bad Request", null);
        for (OcCourseSection section : sections) {
            sectionService.save(section);
        }
        return new CommonResult(200,"message",sections);
    }
    @Log
    @RequestMapping(value = "/course/section/delete")
    public CommonResult deleteSections(@RequestBody List<Integer> sectionIds){
        if(sectionIds == null) return new CommonResult(400,"Bad Request", null);
        for(Integer id:sectionIds){
            sectionService.getBaseMapper().deleteById(id);
        }
        return new CommonResult(200,"success",null);
    }

    @Log
    @RequestMapping(value = "/course/section/edit")
    public CommonResult editSection(@RequestBody OcCourseSection section){
        if(section == null) return new CommonResult(400,"Bad Request", null);
        boolean b = sectionService.updateById(section);
        if(b){
            return new CommonResult(200,"success",null);
        }
        return new CommonResult(400,"Bad Request",null);
    }
}

