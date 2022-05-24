package com.example.demo.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class TbAdminRole implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 管理员ID
     */
    private Integer adminId;

    /**
     * 角色ID
     */
    private Integer roleId;


}
