package com.example.demo.security;

import com.example.demo.entity.TbAdmin;
import com.example.demo.entity.TbAdminRole;
import com.example.demo.entity.TbRole;
import com.example.demo.service.TbAdminRoleService;
import com.example.demo.service.TbAdminService;
import com.example.demo.service.TbRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private TbAdminRoleService adminRoleService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.notNull(username, "用户名称不能为空");
        TbAdmin adminInDb = adminService
                .lambdaQuery()
                .eq(TbAdmin::getLoginName, username)
                .one();
        Optional.ofNullable(adminInDb).orElseThrow(() -> new UsernameNotFoundException("用户名不存在！"));
        List<TbAdminRole> adminRoles = adminRoleService
                .lambdaQuery()
                .eq(TbAdminRole::getAdminId, adminInDb.getId())
                .list();


        Set<GrantedAuthority> authorities = adminRoles.stream()
                .map(adminRole -> roleService
                        .lambdaQuery()
                        .eq(TbRole::getId, adminRole.getRoleId())
                        .one())
                .map(role -> new SimpleGrantedAuthority(role.getKeyword()))
                .collect(Collectors.toSet());

        adminInDb.setRoles(authorities);

        return adminInDb;
    }
}
