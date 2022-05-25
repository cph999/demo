package com.example.demo.security;

import com.example.demo.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p></p>
 *
 * @author gqz20
 * @create 2022-05-25 10:50
 **/
public class MyEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Result result = null;
        if (authException != null) {
            result = Result.error(authException.getMessage());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().append(objectMapper.writeValueAsString(result));
    }
}
