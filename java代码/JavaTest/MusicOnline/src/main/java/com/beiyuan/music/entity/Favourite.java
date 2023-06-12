package com.beiyuan.music.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Favourite implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer userId;

    private Integer songId;

    private Integer songSheetId;

    private Boolean type;

    private LocalDateTime createTime;


}
