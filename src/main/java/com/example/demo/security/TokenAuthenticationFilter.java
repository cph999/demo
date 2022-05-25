package com.example.demo.security;

import com.example.demo.entity.TbAdmin;
import com.example.demo.entity.TbAdminRole;
import com.example.demo.entity.TbRole;
import com.example.demo.entity.Token;
import com.example.demo.service.TbAdminRoleService;
import com.example.demo.service.TbAdminService;
import com.example.demo.service.TbRoleService;
import com.example.demo.service.TokenService;
import com.example.demo.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author gqz20
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private TbAdminService adminService;
    @Autowired
    private TbRoleService roleService;
    @Autowired
    private TbAdminRoleService adminRoleService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String tokenStr = request.getHeader("token");
        String loginName = (String) request.getAttribute("loginName");
        if (StringUtils.isNotBlank(tokenStr)) {
            Token tokenDb = tokenService
                    .lambdaQuery()
                    .eq(Token::getToken, tokenStr)
                    .one();

            if (tokenDb != null && tokenDb.getUserId() != null) {
                TbAdmin user = adminService.lambdaQuery().eq(TbAdmin::getId, tokenDb.getUserId()).one();
                Optional.ofNullable(user).orElseThrow(() -> new UsernameNotFoundException("token已失效"));

                List<TbAdminRole> adminRoles = adminRoleService
                        .lambdaQuery()
                        .eq(TbAdminRole::getAdminId, user.getId())
                        .list();

                if (!CollectionUtils.isEmpty(adminRoles)) {
                    Set<GrantedAuthority> authorities = adminRoles.stream()
                            .map(adminRole -> roleService
                                    .lambdaQuery()
                                    .eq(TbRole::getId, adminRole.getRoleId())
                                    .one())
                            .map(role -> new SimpleGrantedAuthority(role.getKeyword()))
                            .collect(Collectors.toSet());

                    user.setRoles(authorities);
                }

                user.setToken(tokenStr);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info(String.format("Authenticated user %s, setting security context", user.getUsername()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

}
