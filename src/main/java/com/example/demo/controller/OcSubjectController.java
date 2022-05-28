package com.example.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.OcSubject;
import com.example.demo.exception.PersistenceException;
import com.example.demo.service.OcSubjectCourseService;
import com.example.demo.service.OcSubjectService;
import com.example.demo.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 专题表 前端控制器
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/oc-subject")
public class OcSubjectController {
    @Autowired
    private OcSubjectService subjectService;
    @Autowired
    private OcSubjectCourseService subjectCourseService;

    /**
     * 专题条件分页
     *
     * @param subject
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    public Result page(
            @RequestBody(required = false) OcSubject subject,
            @PathVariable(required = false) Integer pageNum,
            @PathVariable(required = false) Integer pageSize
    ) {
        Integer start = Optional.ofNullable(pageNum).orElse(1);
        Integer size = Optional.ofNullable(pageSize).orElse(10);
        IPage<OcSubject> subjects = subjectService.selectPage(subject, start, size);
        return Result.ok("专题的条件分页查询成功！", subjects);
    }

    /**
     * 批量添加subject
     *
     * @param subjects
     * @return
     */
    @PostMapping("addList")
    public Result addSubject(@RequestBody List<OcSubject> subjects) {
        Assert.notEmpty(subjects, "添加subject必须传递json body");
        saveOrUpdate(subjects);
        return Result.ok("批量插入subject成功");
    }

    /**
     * 单个添加subject
     *
     * @param subjects
     * @return
     */
    @PostMapping("addone")
    public Result addSubjectOne(@RequestBody OcSubject subjects) {
        Assert.notNull(subjects, "添加subject必须传递json body");
        saveOrUpdate(List.of(subjects));
        return Result.ok("单个插入subject成功");
    }


    /**
     * 批量或单个更新，更新必须传递subject的id
     *
     * @param subjects
     * @return
     */
    @PutMapping("update")
    public Result updateSubject(@RequestBody List<OcSubject> subjects) {
        Assert.notEmpty(subjects, "更新subject必须传递json body");
        boolean b = subjects.stream().map(OcSubject::getSubjectId).allMatch(Objects::nonNull);
        Assert.isTrue(b, "更新必须传递subject的id");
        saveOrUpdate(subjects);
        return Result.ok("批量或单个更新subject成功");
    }

    /**
     * 单个更新，更新必须传递subject的id
     *
     * @param subjects
     * @return
     */
    @PutMapping("updateOne")
    public Result updateSubjectOne(@RequestBody OcSubject subjects) {
        Assert.notNull(subjects, "更新subject必须传递json body");
        boolean b = subjects.getSubjectId() == null;
        Assert.isTrue(b, "更新必须传递subject的id");
        saveOrUpdate(List.of(subjects));
        return Result.ok("单个更新subject成功");
    }

    /**
     * saveOrUpdate
     *
     * @param subjects
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdate(List<OcSubject> subjects) {
        return subjectService.saveOrUpdateBatch(subjects);
    }

    @DeleteMapping("delete/{sid}")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteSubject(@PathVariable Integer sid) {
        boolean b = subjectService.removeById(sid);
        subjectCourseService.updateAssociation(sid);
        if (!b) {
            throw new PersistenceException("删除subject失败");
        }
        return Result.ok("delete subject success");
    }

}

