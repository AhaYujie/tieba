package online.ahayujie.project.controller;


import io.swagger.annotations.ApiOperation;
import online.ahayujie.project.bean.dto.MainPageDTO;
import online.ahayujie.project.bean.dto.SectionCreateParam;
import online.ahayujie.project.bean.dto.SectionUpdateParam;
import online.ahayujie.project.bean.model.Section;
import online.ahayujie.project.core.Page;
import online.ahayujie.project.core.Result;
import online.ahayujie.project.service.SectionService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 论坛板块 前端控制器
 * </p>
 *
 * @author aha
 * @since 2022-01-22
 */
@RestController
@RequestMapping("/section")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @ApiOperation(value = "新增论坛板块")
    @PostMapping(value = "/create")
    public Result<Section> create(@Valid @RequestBody SectionCreateParam param) {
        Section section = sectionService.create(param);
        return Result.data(section);
    }

    @ApiOperation(value = "更新论坛板块")
    @PostMapping(value = "/update")
    public Result<Section> update(@Valid @RequestBody SectionUpdateParam param) {
        Section section = sectionService.update(param);
        return Result.data(section);
    }

    @ApiOperation(value = "删除论坛板块")
    @PostMapping(value = "/delete")
    public Result<Object> delete(@Valid @RequestParam Long id) {
        sectionService.removeById(id);
        return Result.success();
    }

    @ApiOperation(value = "获取论坛板块")
    @PostMapping(value = "/detail")
    public Result<Section> detail(@Valid @RequestParam Long id) {
        return Result.data(sectionService.getById(id));
    }

    @ApiOperation(value = "获取首页列表")
    @PostMapping(value = "/main-page")
    public Result<Page<MainPageDTO>> mainPage(@RequestParam Long pageNum, @RequestParam Long pageSize) {
        return Result.data(sectionService.getMainPage(pageNum, pageSize));
    }

    @ApiOperation(value = "获取分类下的板块")
    @PostMapping(value = "/cate/list")
    public Result<Page<Section>> getSectionByCate(@RequestParam Long cateId, @RequestParam Long pageNum,
                                                  @RequestParam Long pageSize) {
        return Result.data(sectionService.getSectionByCate(cateId, pageNum, pageSize));
    }

    @ApiOperation(value = "搜索板块")
    @PostMapping(value = "/search")
    public Result<List<Section>> searchSection(@RequestParam(defaultValue = "") String keyword) {
        return Result.data(sectionService.searchSection(keyword));
    }

    @ApiOperation(value = "分页获取板块")
    @PostMapping(value = "/page")
    public Result<Page<Section>> getSectionPage(@RequestParam Long pageNum, @RequestParam Long pageSize) {
        return Result.data(sectionService.getSectionPage(pageNum, pageSize));
    }
}
