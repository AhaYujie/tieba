package online.ahayujie.project.service;

import lombok.extern.slf4j.Slf4j;
import online.ahayujie.project.bean.model.*;
import online.ahayujie.project.mapper.BlogMapper;
import online.ahayujie.project.mapper.CommentMapper;
import online.ahayujie.project.mapper.SectionMapper;
import online.ahayujie.project.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class BlogServiceTest {
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Test
    void create() {
        User user = new User();
        user.setUsername("user for test");
        user.setPassword("password for test");
        userMapper.insert(user);
        Section section = new Section();
        section.setName("section for test");
        sectionMapper.insert(section);
        Blog blog = new Blog();
        blog.setTitle("title");
        blog.setContent("content");
        blog.setUserId(user.getId());
        blog.setSectionId(section.getId());
        blog.setTag("test,aha");
        blogService.create(blog);
    }

    @Test
    void update() {
        Blog blog = new Blog();
        blog.setTitle("test");
        blogMapper.insert(blog);
        User user = new User();
        user.setUsername("user for test");
        user.setPassword("password for test");
        userMapper.insert(user);
        Section section = new Section();
        section.setName("section for test");
        sectionMapper.insert(section);
        blog.setTitle("title");
        blog.setContent("content");
        blog.setUserId(user.getId());
        blog.setSectionId(section.getId());
        blog.setTag("test,aha");
        blogService.update(blog);
    }

    @Test
    void postComment() {
        Blog blog = new Blog();
        blog.setTitle("test");
        blogMapper.insert(blog);
        User user = new User();
        user.setUsername("user for test");
        user.setPassword("password for test");
        userMapper.insert(user);
        Comment comment = new Comment();
        comment.setBlogId(blog.getId());
        comment.setUserId(user.getId());
        comment.setContent("content");
        blogService.postComment(comment);
    }

    @Test
    void listComment() {
        blogService.listComment(-1L, 1L, 10L);
        Blog blog = new Blog();
        blog.setTitle("test");
        blogMapper.insert(blog);
        Comment comment = new Comment();
        comment.setBlogId(blog.getId());
        commentMapper.insert(comment);
        blogService.listComment(blog.getId(), 1L, 10L);
    }

    @Test
    void replyComment() {
        Blog blog = new Blog();
        blog.setTitle("test");
        blogMapper.insert(blog);
        User user = new User();
        user.setUsername("user for test");
        user.setPassword("password for test");
        userMapper.insert(user);
        Comment comment = new Comment();
        comment.setBlogId(blog.getId());
        commentMapper.insert(comment);
        BlogReply reply = new BlogReply();
        reply.setBlogId(blog.getId());
        reply.setUserId(user.getId());
        reply.setCommentId(comment.getId());
        reply.setContent("reply content");
        blogService.replyComment(reply);
    }
}