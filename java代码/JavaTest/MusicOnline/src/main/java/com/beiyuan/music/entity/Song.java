package com.beiyuan.music.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 歌曲
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer singerId;

    private String name;

    private String album;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String cover;

    private String lyric;

    private String url;

    @TableField(exist = false)
    private String singerName;

}
