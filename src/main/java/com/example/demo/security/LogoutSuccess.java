package com.example.demo.security;


import com.example.demo.entity.TbAdmin;
import com.example.demo.entity.Token;
import com.example.demo.service.TokenService;
import com.example.demo.util.RedisUtil;
import com.example.demo.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * <p>退出登录成功处理器</p>
 *
 * @author gqz20
 * @create 2021-12-08 09:16
 **/
@Component
@Slf4j
public class LogoutSuccess implements LogoutSuccessHandler {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = request.getHeader("token");
        TbAdmin tbAdmin = (TbAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional.ofNullable(token).orElseThrow(()->new RuntimeException("未登录，不可注销"));
        boolean b = tokenService
                .lambdaUpdate()
                .eq(Token::getToken, token)
                .remove();
        redisUtil.set("token:"+tbAdmin.getLoginName(),null);
        if (b) {
            throw new RuntimeException("删除token失败");
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(Result.ok("退出成功")));
    }
}
