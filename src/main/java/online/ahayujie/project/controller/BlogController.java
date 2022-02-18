package online.ahayujie.project.controller;


import io.swagger.annotations.ApiOperation;
import online.ahayujie.project.bean.model.Blog;
import online.ahayujie.project.bean.model.Comment;
import online.ahayujie.project.core.Page;
import online.ahayujie.project.core.Result;
import online.ahayujie.project.service.BlogService;
import org.springframework.web.bind.annotation.*;

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

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @ApiOperation(value = "获取帖子内容")
    @PostMapping(value = "/detail")
    public Result<Blog> detail(@RequestParam Long id) {
        return Result.data(blogService.getById(id));
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
        blogService.removeById(id);
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
}
