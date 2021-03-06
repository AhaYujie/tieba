package online.ahayujie.project.controller;


import io.swagger.annotations.ApiOperation;
import online.ahayujie.project.bean.model.*;
import online.ahayujie.project.core.Page;
import online.ahayujie.project.core.Result;
import online.ahayujie.project.service.BlogRecycleService;
import online.ahayujie.project.service.BlogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 帖子 前端控制器
 * </p>
 *
 * @author aha
 * @since 2022-02-18
 */
@RestController
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;
    private final BlogRecycleService blogRecycleService;

    public BlogController(BlogService blogService, BlogRecycleService blogRecycleService) {
        this.blogService = blogService;
        this.blogRecycleService = blogRecycleService;
    }

    @ApiOperation(value = "获取帖子内容")
    @PostMapping(value = "/detail")
    public Result<Blog> detail(@RequestParam Long id) {
        return Result.data(blogService.getBlogDetail(id));
    }

    @ApiOperation(value = "发表帖子")
    @PostMapping(value = "/create")
    public Result<Blog> create(@RequestBody Blog blog) {
        return Result.data(blogService.create(blog));
    }

    @ApiOperation(value = "编辑帖子")
    @PostMapping(value = "/update")
    public Result<Blog> update(@RequestBody Blog blog) {
        return Result.data(blogService.update(blog));
    }

    @ApiOperation(value = "删除帖子")
    @PostMapping(value = "/delete")
    public Result<Object> delete(@RequestParam Long id) {
        blogService.delete(id);
        return Result.success();
    }

    @ApiOperation(value = "评论")
    @PostMapping(value = "/comment")
    public Result<Comment> postComment(@RequestBody Comment comment) {
        return Result.data(blogService.postComment(comment));
    }

    @ApiOperation(value = "获取评论")
    @PostMapping(value = "/comment/list")
    public Result<Page<Comment>> listComment(@RequestParam Long blogId, @RequestParam Long pageNum,
                                             @RequestParam Long pageSize) {
        return Result.data(blogService.listComment(blogId, pageNum, pageSize));
    }

    @ApiOperation(value = "回复评论")
    @PostMapping(value = "/comment/reply")
    public Result<BlogReply> replyComment(@RequestBody BlogReply reply) {
        return Result.data(blogService.replyComment(reply));
    }

    @ApiOperation(value = "获取回复")
    @PostMapping(value = "/comment/reply/list")
    public Result<Page<BlogReply>> listReply(@RequestParam Long commentId, @RequestParam Long pageNum,
                                             @RequestParam Long pageSize) {
        return Result.data(blogService.listReply(commentId, pageNum, pageSize));
    }

    @ApiOperation(value = "获取帖子列表")
    @PostMapping(value = "/list")
    public Result<Page<Blog>> listBlog(@RequestParam Long sectionId, @RequestParam Long pageNum,
                                       @RequestParam Long pageSize) {
        return Result.data(blogService.listBlog(sectionId, pageNum, pageSize));
    }

    @ApiOperation(value = "获取我的帖子列表")
    @PostMapping(value = "/list/user")
    public Result<Page<Blog>> listBlogByUser(@RequestParam Long pageNum, @RequestParam Long pageSize) {
        return Result.data(blogService.listBlogByUser(pageNum, pageSize));
    }

    @ApiOperation(value = "搜索帖子")
    @PostMapping(value = "search")
    public Result<Page<EsBlog>> searchBlog(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                           @RequestParam String keyword) {
        return Result.data(blogService.searchBlog(pageNum, pageSize, keyword));
    }

    @ApiOperation(value = "获取帖子榜单, sort=1为新帖榜单，sort=2为热帖榜单")
    @PostMapping(value = "rank")
    public Result<List<Blog>> getRank(@RequestParam Integer sort) {
        return Result.data(blogService.getRank(sort));
    }

    @ApiOperation(value = "获取相似推荐")
    @PostMapping(value = "/similar-recommend")
    public Result<Page<EsBlog>> getSimilarRecommend(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                                    @RequestParam Long id) {
        return Result.data(blogService.getSimilarRecommend(pageNum, pageSize, id));
    }

    @ApiOperation(value = "获取全部帖子列表")
    @PostMapping(value = "/list/all")
    public Result<Page<Blog>> listAll(@RequestParam Long pageNum, @RequestParam Long pageSize) {
        return Result.data(blogService.listAll(pageNum, pageSize));
    }

    @ApiOperation(value = "移动帖子到回收站")
    @PostMapping(value = "/recycle")
    public Result<Object> recycleBlog(@RequestParam Long id) {
        blogService.recycleBlog(id);
        return Result.success();
    }

    @ApiOperation(value = "获取回收站的帖子")
    @PostMapping(value = "/recycle/list")
    public Result<Page<BlogRecycle>> getRecycleBlog(@RequestParam Long pageNum, @RequestParam Long pageSize) {
        return Result.data(blogService.getRecycleBlog(pageNum, pageSize));
    }

    @ApiOperation(value = "重新上架帖子")
    @PostMapping(value = "/recycle/repost")
    public Result<Blog> repostBlog(@RequestParam Long id) {
        return Result.data(blogService.repostBlog(id));
    }

    @ApiOperation(value = "编辑回收站的帖子")
    @PostMapping(value = "/recycle/update")
    public Result<BlogRecycle> updateRecycleBlog(@RequestBody BlogRecycle blogRecycle) {
        return Result.data(blogService.updateRecycleBlog(blogRecycle));
    }

    @ApiOperation(value = "删除回收站的帖子")
    @PostMapping(value = "/recycle/delete")
    public Result<Object> deleteRecycleBlog(@RequestParam Long id) {
        blogRecycleService.removeById(id);
        return Result.success();
    }

    @ApiOperation(value = "获取回收站的帖子详情")
    @PostMapping(value = "/recycle/detail")
    public Result<BlogRecycle> getRecycleBlogDetail(@RequestParam Long id) {
        return Result.data(blogRecycleService.getById(id));
    }
}
