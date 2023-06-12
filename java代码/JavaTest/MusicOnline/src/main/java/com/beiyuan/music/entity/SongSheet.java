package com.beiyuan.music.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 歌单
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SongSheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String cover;

    private String introduction;

    private String style;

    private Integer viewCount;

}
