package online.ahayujie.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import online.ahayujie.project.bean.model.*;
import online.ahayujie.project.core.Page;
import online.ahayujie.project.exception.ApiException;
import online.ahayujie.project.mapper.*;
import online.ahayujie.project.repository.BlogEsRepository;
import online.ahayujie.project.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.ahayujie.project.service.CommonService;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private final BlogEsRepository blogEsRepository;
    private final RedisTemplate<String, Serializable> redisTemplate;
    private final BlogRecycleMapper blogRecycleMapper;

    private static final String BLOG_REDIS_KEY = "blog.detail:%s:string";

    public BlogServiceImpl(UserMapper userMapper, SectionMapper sectionMapper, CommentMapper commentMapper, BlogReplyMapper blogReplyMapper, CommonService commonService, BlogEsRepository blogEsRepository, RedisTemplate<String, Serializable> redisTemplate, BlogRecycleMapper blogRecycleMapper) {
        this.userMapper = userMapper;
        this.sectionMapper = sectionMapper;
        this.commentMapper = commentMapper;
        this.blogReplyMapper = blogReplyMapper;
        this.commonService = commonService;
        this.blogEsRepository = blogEsRepository;
        this.redisTemplate = redisTemplate;
        this.blogRecycleMapper = blogRecycleMapper;
    }

    @Override
    public Blog create(Blog blog) {
        User user = commonService.getUserFromToken();
        blog.setUserId(user.getId());
        blog.setUsername(user.getUsername());
        checkBlog(blog);
        blog.setCreateTime(new Date());
        this.baseMapper.insert(blog);
        blog = baseMapper.selectById(blog.getId());
        blogEsRepository.save(getEsBlog(blog));
        return blog;
    }

    @Override
    public Blog update(Blog blog) {
        Blog blogOld = baseMapper.selectById(blog.getId());
        if (blogOld == null) {
            throw new ApiException("帖子不存在");
        }
        blog.setUserId(blogOld.getUserId());
        blog.setUsername(blogOld.getUsername());
        checkBlog(blog);
        blog.setViews(baseMapper.selectById(blog.getId()).getViews());
        this.baseMapper.updateById(blog);
        blog = baseMapper.selectById(blog.getId());
        blogEsRepository.save(getEsBlog(blog));
        String key = String.format(BLOG_REDIS_KEY, blog.getId());
        redisTemplate.delete(key);
        return blog;
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

    @Override
    public Page<Blog> listBlogByUser(Long pageNum, Long pageSize) {
        User user = commonService.getUserFromToken();
        Wrapper<Blog> wrapper = new QueryWrapper<Blog>().eq("user_id", user.getId()).orderByDesc("update_time");
        IPage<Blog> blogIPage = baseMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNum, pageSize), wrapper);
        return new Page<>(blogIPage);
    }

    @Override
    public void delete(Long id) {
        removeById(id);
        blogEsRepository.deleteById(id);
    }

    @Override
    public Page<EsBlog> searchBlog(Integer pageNum, Integer pageSize, String keyword) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withPageable(PageRequest.of(pageNum - 1, pageSize));
        if (!StringUtils.isEmpty(keyword)) {
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> builders = new ArrayList<>();
            builders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("title", keyword), ScoreFunctionBuilders.weightFactorFunction(50)));
            builders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("content", keyword), ScoreFunctionBuilders.weightFactorFunction(25)));
            builders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("tag", keyword), ScoreFunctionBuilders.weightFactorFunction(25)));
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builderArray = new FunctionScoreQueryBuilder
                    .FilterFunctionBuilder[builders.size()];
            builders.toArray(builderArray);
            queryBuilder.withQuery(QueryBuilders.functionScoreQuery(builderArray)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                    .setMinScore(2));
        }
        queryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        org.springframework.data.domain.Page<EsBlog> page = blogEsRepository.search(queryBuilder.build());
        return new Page<>((long) page.getNumber() + 1, (long) page.getSize(), (long) page.getTotalPages(),
                page.getTotalElements(), page.getContent());
    }

    @Override
    public List<Blog> getRank(Integer sort) {
        Wrapper<Blog> wrapper;
        if (sort == 1) {
            wrapper = new QueryWrapper<Blog>().orderByDesc("create_time");
        } else if (sort == 2) {
            wrapper = new QueryWrapper<Blog>().orderByDesc("views");
        } else {
            return null;
        }
        return baseMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(1, 20), wrapper).getRecords();
    }

    @Override
    public Blog getBlogDetail(Long id) {
        String key = String.format(BLOG_REDIS_KEY, id);
        Serializable blogCache = redisTemplate.opsForValue().get(key);
        Blog blog;
        if (blogCache == null) {
            blog = baseMapper.selectById(id);
            if (blog == null) {
                return null;
            }
            String blogJson = getBlogJson(blog).toString();
            redisTemplate.opsForValue().set(key, blogJson);
        } else {
            log.info("getBlogDetail use cache");
            blog = getBlogFromJson((String) blogCache);
        }
        baseMapper.updateView(id);
        return blog;
    }

    @Override
    public Page<EsBlog> getSimilarRecommend(Integer pageNum, Integer pageSize, Long id) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withPageable(PageRequest.of(pageNum - 1, pageSize));
        EsBlog esBlog = blogEsRepository.findById(id).orElse(null);
        if (esBlog == null) {
            return null;
        }
        List<FunctionScoreQueryBuilder.FilterFunctionBuilder> builders = new ArrayList<>();
        if (esBlog.getTitle() != null) {
            builders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("title", esBlog.getTitle()), ScoreFunctionBuilders.weightFactorFunction(50)));
        }
        if (esBlog.getContent() != null) {
            builders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("content", esBlog.getContent()), ScoreFunctionBuilders.weightFactorFunction(25)));
        }
        if (esBlog.getTag() != null) {
            builders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    QueryBuilders.matchQuery("tag", esBlog.getTag()), ScoreFunctionBuilders.weightFactorFunction(50)));
        }
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] builderArray = new FunctionScoreQueryBuilder
                .FilterFunctionBuilder[builders.size()];
        builders.toArray(builderArray);
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builderArray)
                .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                .setMinScore(2);
        queryBuilder.withQuery(functionScoreQueryBuilder);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.mustNot(QueryBuilders.termQuery("id", esBlog.getId()));
        queryBuilder.withFilter(boolQueryBuilder);
        queryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        org.springframework.data.domain.Page<EsBlog> page = blogEsRepository.search(queryBuilder.build());
        return new Page<>((long) page.getNumber() + 1, (long) page.getSize(), (long) page.getTotalPages(),
                page.getTotalElements(), page.getContent());
    }

    @Override
    public Page<Blog> listAll(Long pageNum, Long pageSize) {
        Wrapper<Blog> wrapper = new QueryWrapper<Blog>().orderByDesc("update_time");
        IPage<Blog> blogIPage = baseMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNum, pageSize), wrapper);
        return new Page<>(blogIPage);
    }

    @Override
    public void recycleBlog(Long id) {
        Blog blog = baseMapper.selectById(id);
        removeById(id);
        blogEsRepository.deleteById(id);
        BlogRecycle blogRecycle = new BlogRecycle();
        BeanUtils.copyProperties(blog, blogRecycle);
        blogRecycle.setId(null);
        blogRecycleMapper.insert(blogRecycle);
    }

    private EsBlog getEsBlog(Blog blog) {
        EsBlog esBlog = new EsBlog();
        BeanUtils.copyProperties(blog, esBlog);
        return esBlog;
    }

    private JsonObject getBlogJson(Blog blog) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", blog.getId());
        jsonObject.addProperty("updateTime", blog.getUpdateTime().toString());
        jsonObject.addProperty("createTime", blog.getCreateTime().toString());
        jsonObject.addProperty("title", blog.getTitle());
        jsonObject.addProperty("content", blog.getContent());
        jsonObject.addProperty("userId", blog.getUserId());
        jsonObject.addProperty("username", blog.getUsername());
        jsonObject.addProperty("sectionId", blog.getSectionId());
        jsonObject.addProperty("sectionName", blog.getSectionName());
        jsonObject.addProperty("tag", blog.getTag());
        jsonObject.addProperty("views", blog.getViews());
        return jsonObject;
    }

    private Blog getBlogFromJson(String json) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        Blog blog = new Blog();
        blog.setId(jsonObject.get("id").getAsLong());
        blog.setUpdateTime( new Date(jsonObject.get("updateTime").getAsString()));
        blog.setCreateTime( new Date(jsonObject.get("createTime").getAsString()));
        blog.setTitle(jsonObject.get("title").getAsString());
        blog.setContent(jsonObject.get("content").getAsString());
        blog.setUserId(jsonObject.get("userId").getAsLong());
        blog.setUsername(jsonObject.get("username").getAsString());
        blog.setSectionId(jsonObject.get("sectionId").getAsLong());
        blog.setSectionName(jsonObject.get("sectionName").getAsString());
        blog.setTag(jsonObject.get("tag").getAsString());
        blog.setViews(jsonObject.get("views").getAsLong());
        return blog;
    }
}
