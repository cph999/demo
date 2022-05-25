package com.example.demo.security;

import com.example.demo.entity.TbAdmin;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * <p>用户认证类</p>
 *
 * @author gqz20
 * @create 2022-05-25 09:24
 **/
@Component
public class MyUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private TbAdminService adminService;
    @Autowired
    private TbRoleService roleService;
    @Autowired
    private TbMenuService menuService;
    @Autowired
    private TbAdminRoleService adminRoleService;
    @Autowired
    private TbRoleMenuService roleMenuService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.notNull(username,"用户名称不能为空");
        TbAdmin adminInDb = adminService
                .lambdaQuery()
                .eq(TbAdmin::getUsername, username)
                .one();
        Optional.ofNullable(adminInDb).orElseThrow(()->new UsernameNotFoundException("用户名不存在！"));

        return null;
    }
}
