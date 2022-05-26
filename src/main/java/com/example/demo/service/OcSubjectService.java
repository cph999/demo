package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.OcSubject;

/**
 * <p>
 * 专题表 服务类
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
public interface OcSubjectService extends IService<OcSubject> {
    /**
     * 分页条件查询
     * @param subject
     * @param start
     * @param size
     * @return
     */
    IPage<OcSubject> selectPage(OcSubject subject, Integer start, Integer size);
}
