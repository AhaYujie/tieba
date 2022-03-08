package online.ahayujie.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import online.ahayujie.project.bean.model.*;
import online.ahayujie.project.core.Page;
import online.ahayujie.project.exception.ApiException;
import online.ahayujie.project.mapper.*;
import online.ahayujie.project.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.ahayujie.project.service.CommonService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 帖子 服务实现类
 * </p>
 *
 * @author aha
 * @since 2022-02-18
 */
@Slf4j
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    private final UserMapper userMapper;
    private final SectionMapper sectionMapper;
    private final CommentMapper commentMapper;
    private final BlogReplyMapper blogReplyMapper;
    private final CommonService commonService;

    public BlogServiceImpl(UserMapper userMapper, SectionMapper sectionMapper, CommentMapper commentMapper, BlogReplyMapper blogReplyMapper, CommonService commonService) {
        this.userMapper = userMapper;
        this.sectionMapper = sectionMapper;
        this.commentMapper = commentMapper;
        this.blogReplyMapper = blogReplyMapper;
        this.commonService = commonService;
    }

    @Override
    public Blog create(Blog blog) {
        User user = commonService.getUserFromToken();
        blog.setUserId(user.getId());
        blog.setUsername(user.getUsername());
        checkBlog(blog);
        blog.setCreateTime(new Date());
        this.baseMapper.insert(blog);
        return this.baseMapper.selectById(blog.getId());
    }

    @Override
    public Blog update(Blog blog) {
        if (this.baseMapper.selectById(blog.getId()) == null) {
            throw new ApiException("帖子不存在");
        }
        checkBlog(blog);
        this.baseMapper.updateById(blog);
        return this.baseMapper.selectById(blog.getId());
    }

    private void checkBlog(Blog blog) {
        User user = userMapper.selectById(blog.getUserId());
        if (user == null) {
            throw new ApiException("用户不存在");
        }
        Section section = sectionMapper.selectById(blog.getSectionId());
        if (section == null) {
            throw new ApiException("板块不存在");
        }
        blog.setUsername(user.getUsername());
        blog.setSectionName(section.getName());
    }

    @Override
    public Comment postComment(Comment comment) {
        User user = commonService.getUserFromToken();
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        Blog blog = baseMapper.selectById(comment.getBlogId());
        if (blog == null) {
            throw new ApiException("帖子不存在");
        }
        comment.setBlogTitile(blog.getTitle());
        comment.setUsername(user.getUsername());
        comment.setCreateTime(new Date());
        commentMapper.insert(comment);
        return commentMapper.selectById(comment.getId());
    }

    @Override
    public Page<Comment> listComment(Long blogId, Long pageNum, Long pageSize) {
        Wrapper<Comment> queryWrapper = new QueryWrapper<Comment>().eq("blog_id", blogId).orderByDesc("create_time");
        IPage<Comment> commentPage = commentMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNum, pageSize), queryWrapper);
        return new Page<>(commentPage);
    }

    @Override
    public BlogReply replyComment(BlogReply reply) {
        User user = commonService.getUserFromToken();
        reply.setUserId(user.getId());
        reply.setUsername(user.getUsername());
        Blog blog = baseMapper.selectById(reply.getBlogId());
        if (blog == null) {
            throw new ApiException("帖子不存在");
        }
        Comment comment = commentMapper.selectById(reply.getCommentId());
        if (comment == null) {
            throw new ApiException("评论不存在");
        }
        reply.setBlogTitle(blog.getTitle());
        reply.setUsername(user.getUsername());
        reply.setCreateTime(new Date());
        blogReplyMapper.insert(reply);
        return blogReplyMapper.selectById(reply.getId());
    }

    @Override
    public Page<BlogReply> listReply(Long commentId, Long pageNum, Long pageSize) {
        Wrapper<BlogReply> wrapper = new QueryWrapper<BlogReply>().eq("comment_id", commentId).orderByDesc("create_time");
        IPage<BlogReply> replyIPage = blogReplyMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNum, pageSize), wrapper);
        return new Page<>(replyIPage);
    }

    @Override
    public Page<Blog> listBlog(Long sectionId, Long pageNum, Long pageSize) {
        Wrapper<Blog> wrapper = new QueryWrapper<Blog>().eq("section_id", sectionId).orderByDesc("update_time");
        IPage<Blog> blogIPage = baseMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNum, pageSize), wrapper);
        return new Page<>(blogIPage);
    }
}
