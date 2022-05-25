package com.example.demo.security;

import com.example.demo.entity.TbAdmin;
import com.example.demo.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gqz20
 */
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 尝试登录成功后的处理
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper objectMapper = new ObjectMapper();
        TbAdmin admin = (TbAdmin) authentication.getPrincipal();
        admin.setPassword(null);

        response.getWriter().write(objectMapper.writeValueAsString(Result.ok("登录成功", admin)));
    }

}
