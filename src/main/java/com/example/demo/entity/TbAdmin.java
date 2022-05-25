package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * <p>
 * 
 * </p>
 *
 * @author alex wong
 * @since 2022-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TbAdmin implements Serializable , UserDetails {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态
     */
    private String status;

    /**
     * roles列表
     * @return
     */
    @TableField(exist = false)
    private Set<GrantedAuthority> roles;

    @TableField(exist = false)
    private String token;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return loginName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
