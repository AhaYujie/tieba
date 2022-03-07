package online.ahayujie.project.controller;


import io.swagger.annotations.ApiOperation;
import online.ahayujie.project.bean.dto.SectionCreateParam;
import online.ahayujie.project.bean.model.Section;
import online.ahayujie.project.bean.model.SectionCate;
import online.ahayujie.project.core.Result;
import online.ahayujie.project.service.SectionCateService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 板块分类 前端控制器
 * </p>
 *
 * @author aha
 * @since 2022-02-27
 */
@RestController
@RequestMapping("/section-cate")
public class SectionCateController {
    private final SectionCateService sectionCateService;


    public SectionCateController(SectionCateService sectionCateService) {
        this.sectionCateService = sectionCateService;
    }

    @ApiOperation(value = "新增论坛板块分类")
    @PostMapping(value = "/create")
    public Result<SectionCate> create(@RequestBody SectionCate sectionCate) {
        sectionCateService.save(sectionCate);
        return Result.data(sectionCateService.getById(sectionCate.getId()));
    }

    @ApiOperation(value = "更新论坛板块分类")
    @PostMapping(value = "/update")
    public Result<SectionCate> update(@RequestBody SectionCate sectionCate) {
        sectionCateService.updateById(sectionCate);
        return Result.data(sectionCateService.getById(sectionCate.getId()));
    }

    @ApiOperation(value = "获取板块分类")
    @PostMapping(value = "/detail")
    public Result<SectionCate> detail(@RequestParam Long id) {
        return Result.data(sectionCateService.getById(id));
    }
}
