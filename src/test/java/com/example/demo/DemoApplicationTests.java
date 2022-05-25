package com.example.demo;

import com.example.demo.entity.TbAdminRole;
import com.example.demo.service.TbAdminRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private TbAdminRoleService adminRoleService;

    @Test
    void contextLoads() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String qwe = encoder.encode("qwe");
        System.out.println(qwe);
    }

    @Test
    void admin() {
        List<TbAdminRole> list = adminRoleService.lambdaQuery()
                .eq(TbAdminRole::getAdminId, 1)
                .list();
        list.forEach(System.out::println);
    }

}
