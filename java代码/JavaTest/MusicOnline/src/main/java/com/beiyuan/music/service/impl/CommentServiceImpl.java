package com.beiyuan.music.service.impl;

import com.beiyuan.music.entity.Comment;
import com.beiyuan.music.mapper.CommentMapper;
import com.beiyuan.music.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
