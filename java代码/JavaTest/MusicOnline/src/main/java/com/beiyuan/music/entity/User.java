package com.beiyuan.music.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 普通用户
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private Boolean sex;

    private String phone;

    private String email;

    private LocalDate birthday;

    private String signature;

    private String location;

    private String avator;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
