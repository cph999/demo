package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName tb_token
 */
@TableName(value ="tb_token")
@Data
public class Token implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "token")
    private String token;

    /**
     * 
     */
    @TableField(value = "user_id")
    private Integer userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}