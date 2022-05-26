package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.OcSubject;
import com.example.demo.mapper.OcSubjectMapper;
import com.example.demo.service.OcSubjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 专题表 服务实现类
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
@Service
public class OcSubjectServiceImpl extends ServiceImpl<OcSubjectMapper, OcSubject> implements OcSubjectService {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public IPage<OcSubject> selectPage(OcSubject subject, Integer start, Integer size) {
        OcSubject orElse = Optional.ofNullable(subject).orElse(new OcSubject());
        QueryWrapper<OcSubject> wrapper = Wrappers.query(orElse);

        Page<OcSubject> page = this.page(new Page<>(start, size), wrapper);

        return page;
    }
}
