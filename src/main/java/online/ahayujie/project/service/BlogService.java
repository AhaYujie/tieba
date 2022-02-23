package online.ahayujie.project.service;

import online.ahayujie.project.bean.model.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import online.ahayujie.project.bean.model.BlogReply;
import online.ahayujie.project.bean.model.Comment;
import online.ahayujie.project.core.Page;

/**
 * <p>
 * 帖子 服务类
 * </p>
 *
 * @author aha
 * @since 2022-02-18
 */
public interface BlogService extends IService<Blog> {
    /**
     * 发表帖子
     * @param blog
     * @return
     */
    Blog create(Blog blog);

    /**
     * 更新帖子
     * @param blog
     * @return
     */
    Blog update(Blog blog);

    /**
     * 评论
     * @param comment
     * @return
     */
    Comment postComment(Comment comment);

    /**
     * 获取评论
     * @param blogId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Comment> listComment(Long blogId, Long pageNum, Long pageSize);

    /**
     * 回复评论
     * @param reply
     * @return
     */
    BlogReply replyComment(BlogReply reply);

    /**
     * 获取回复
     * @param commentId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<BlogReply> listReply(Long commentId, Long pageNum, Long pageSize);
}
