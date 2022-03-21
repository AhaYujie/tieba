package online.ahayujie.project.service;

import online.ahayujie.project.bean.model.*;
import com.baomidou.mybatisplus.extension.service.IService;
import online.ahayujie.project.core.Page;

import java.util.List;

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

    /**
     * 获取帖子列表
     * @param sectionId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Blog> listBlog(Long sectionId, Long pageNum, Long pageSize);

    /**
     * 获取我的帖子列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Blog> listBlogByUser(Long pageNum, Long pageSize);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 搜索
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    Page<EsBlog> searchBlog(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 获取帖子榜单, sort=1为新帖榜单，sort=2为热帖榜单
     * @param sort
     * @return
     */
    List<Blog> getRank(Integer sort);

    /**
     * 获取帖子内容
     * @param id
     * @return
     */
    Blog getBlogDetail(Long id);

    /**
     * 获取相似推荐
     * @param pageNum
     * @param pageSize
     * @param id
     * @return
     */
    Page<EsBlog> getSimilarRecommend(Integer pageNum, Integer pageSize, Long id);

    /**
     * 获取全部帖子列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Blog> listAll(Long pageNum, Long pageSize);

    /**
     * 移动帖子到回收站
     * @param id
     */
    void recycleBlog(Long id);

    /**
     * 获取回收站的帖子
     * @return
     */
    Page<BlogRecycle> getRecycleBlog(Long pageNum, Long pageSize);

    /**
     * 重新上架帖子
     * @param id
     * @return
     */
    Blog repostBlog(Long id);

    /**
     * 编辑回收站的帖子
     * @param blogRecycle
     * @return
     */
    BlogRecycle updateRecycleBlog(BlogRecycle blogRecycle);
}
