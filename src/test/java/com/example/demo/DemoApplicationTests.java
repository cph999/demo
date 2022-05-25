package com.example.demo;

import com.example.demo.entity.TbMenu;
import com.example.demo.mapper.TbMenuMapper;
import com.example.demo.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    TbMenuMapper menuMapper;
    @Test
    void contextLoads() {

        TbMenu menu = menuMapper.selectById(1);
        boolean set = redisUtil.set("menu" , menu);
        if(set){
            System.out.println("获取到数据：" + redisUtil.get("menu"));
        }
        System.out.println("获取到数据：" + redisUtil.get("menu"));
    }

}
