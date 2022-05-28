package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.OcSubjectCourse;
import com.example.demo.mapper.OcSubjectCourseMapper;
import com.example.demo.service.OcSubjectCourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 关联关系表专题和课程 服务实现类
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
@Service
public class OcSubjectCourseServiceImpl extends ServiceImpl<OcSubjectCourseMapper, OcSubjectCourse> implements OcSubjectCourseService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateAssociation(Integer sid) {
        List<OcSubjectCourse> list = this.lambdaQuery()
                .eq(OcSubjectCourse::getSubjectId, sid)
                .list();

        //无关联表
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        //有关联表
        list.forEach(sc -> {
            boolean b = this.remove(Wrappers
                    .lambdaQuery(OcSubjectCourse.class)
                    .eq(OcSubjectCourse::getSubjectId, sc.getSubjectId()));
        });
        return true;
    }
}
