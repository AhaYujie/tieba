package online.ahayujie.project.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import online.ahayujie.project.bean.dto.UserLoginDTO;
import online.ahayujie.project.bean.dto.UserLoginParam;
import online.ahayujie.project.bean.model.*;
import online.ahayujie.project.mapper.*;
import online.ahayujie.project.repository.BlogEsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.io.Serializable;
import java.util.Date;

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
    @Autowired
    private BlogReplyMapper blogReplyMapper;
    @Autowired
    private UserService userService;
    @Value("${jwt.header}")
    private String JWT_HEADER;
    @Value("${jwt.header-prefix}")
    private String JWT_HEADER_PREFIX;
    @Autowired
    private BlogEsRepository blogEsRepository;
    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;
    @Autowired
    private BlogRecycleMapper blogRecycleMapper;

    @BeforeEach
    void login() {
        UserLoginParam param = new UserLoginParam();
        param.setUsername("aha");
        param.setPassword("123456");
        UserLoginDTO loginDTO = userService.login(param);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assertNotNull(servletRequestAttributes);
        HttpServletRequest request = servletRequestAttributes.getRequest();
        MockHttpServletRequest mockHttpServletRequest = (MockHttpServletRequest) request;
        mockHttpServletRequest.addHeader(JWT_HEADER, JWT_HEADER_PREFIX + loginDTO.getAccessToken());
    }

    @Test
    void create() {
        UserLoginParam param = new UserLoginParam();
        param.setUsername("aha");
        param.setPassword("123456");
        userService.login(param);
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
        UserLoginParam param = new UserLoginParam();
        param.setUsername("aha");
        param.setPassword("123456");
        userService.login(param);
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
        UserLoginParam param = new UserLoginParam();
        param.setUsername("aha");
        param.setPassword("123456");
        userService.login(param);
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

    @Test
    void listReply() {
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
        blogReplyMapper.insert(reply);
        blogService.listReply(comment.getId(), 1L, 10L);
    }

    @Test
    void listBlog() {
        blogService.listBlog(1L, 1L, 10L);
    }

    @Test
    void getRank() {
        blogService.getRank(1);
        blogService.getRank(2);
    }

    @Test
    void testEs() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("test", "aha");
        jsonObject.addProperty("id", 1L);
        jsonObject.addProperty("time", new Date().toString());
        System.out.println(jsonObject.toString());
        redisTemplate.opsForValue().set("test", jsonObject.toString());
        String value = (String) redisTemplate.opsForValue().get("test");
        Gson gson = new Gson();
        JsonObject jsonObject1 = gson.fromJson(value, JsonObject.class);
        System.out.println(value);
        System.out.println(jsonObject1.toString());
        System.out.println(redisTemplate.opsForValue().get("no"));
        System.out.println(jsonObject1.get("id").getAsLong());
        System.out.println(jsonObject1.get("test").getAsString());
        System.out.println(jsonObject1.get("time"));
        System.out.println(Date.parse(jsonObject1.get("time").getAsString()));
    }

    @Test
    void listAll() {
        blogService.listAll(1L, 10L);
    }

    @Test
    void recycleBlog() {
        Blog blog = new Blog();
        blog.setTitle("for test");
        blogMapper.insert(blog);
        blogService.recycleBlog(blog.getId());
    }

    @Test
    void repostBlog() {
        UserLoginParam param = new UserLoginParam();
        param.setUsername("aha");
        param.setPassword("123456");
        userService.login(param);
        User user = new User();
        user.setUsername("user for test");
        user.setPassword("password for test");
        userMapper.insert(user);
        Section section = new Section();
        section.setName("section for test");
        sectionMapper.insert(section);
        BlogRecycle blogRecycle = new BlogRecycle();
        blogRecycle.setTitle("for test");
        blogRecycle.setTitle("title");
        blogRecycle.setContent("content");
        blogRecycle.setUserId(user.getId());
        blogRecycle.setSectionId(section.getId());
        blogRecycle.setTag("test,aha");
        blogRecycleMapper.insert(blogRecycle);
        blogService.repostBlog(blogRecycle.getId());
    }

    @Test
    void updateRecycleBlog() {
        BlogRecycle blogRecycle = new BlogRecycle();
        blogRecycle.setTitle("for tgest");
        blogRecycleMapper.insert(blogRecycle);
        blogRecycle.setContent("update once");
        blogService.updateRecycleBlog(blogRecycle);
    }
}