package com.example.demo.security;

import com.example.demo.entity.TbAdmin;
import com.example.demo.entity.Token;
import com.example.demo.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;


/**
 * @author gqz20
 */
public class JsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ObjectMapper objectMapper;


    public JsonAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        try {
            TbAdmin user = objectMapper.readValue(request.getInputStream(), TbAdmin.class);
            UsernamePasswordAuthenticationToken up = new UsernamePasswordAuthenticationToken(
                    user.getLoginName(), user.getPassword());
            //调用父类方法
            Authentication authenticate = getAuthenticationManager().authenticate(up);

            if (authenticate.isAuthenticated()) {
                TbAdmin loginUser = (TbAdmin) authenticate.getPrincipal();
                Token loginToken = new Token();
                loginToken.setToken(UUID.randomUUID().toString());
                loginToken.setUserId(loginUser.getId());
                tokenService.save(loginToken);
                loginUser.setToken(loginToken.getToken());
            }
            return authenticate;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
