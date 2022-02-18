package online.ahayujie.project.service;

import lombok.extern.slf4j.Slf4j;
import online.ahayujie.project.bean.model.Blog;
import online.ahayujie.project.bean.model.Section;
import online.ahayujie.project.bean.model.User;
import online.ahayujie.project.mapper.BlogMapper;
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
}