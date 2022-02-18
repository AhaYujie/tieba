package online.ahayujie.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import online.ahayujie.project.bean.model.Blog;
import online.ahayujie.project.bean.model.Comment;
import online.ahayujie.project.bean.model.Section;
import online.ahayujie.project.bean.model.User;
import online.ahayujie.project.core.Page;
import online.ahayujie.project.exception.ApiException;
import online.ahayujie.project.mapper.BlogMapper;
import online.ahayujie.project.mapper.CommentMapper;
import online.ahayujie.project.mapper.SectionMapper;
import online.ahayujie.project.mapper.UserMapper;
import online.ahayujie.project.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    private final UserMapper userMapper;
    private final SectionMapper sectionMapper;
    private final CommentMapper commentMapper;

    public BlogServiceImpl(UserMapper userMapper, SectionMapper sectionMapper, CommentMapper commentMapper) {
        this.userMapper = userMapper;
        this.sectionMapper = sectionMapper;
        this.commentMapper = commentMapper;
    }

    @Override
    public Blog create(Blog blog) {
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
        Blog blog = baseMapper.selectById(comment.getBlogId());
        if (blog == null) {
            throw new ApiException("帖子不存在");
        }
        User user = userMapper.selectById(comment.getUserId());
        if (user == null) {
            throw new ApiException("用户不存在");
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
}
