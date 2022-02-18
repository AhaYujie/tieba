package online.ahayujie.project.service.impl;

import online.ahayujie.project.bean.model.Comment;
import online.ahayujie.project.mapper.CommentMapper;
import online.ahayujie.project.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 帖子评论 服务实现类
 * </p>
 *
 * @author aha
 * @since 2022-02-18
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
