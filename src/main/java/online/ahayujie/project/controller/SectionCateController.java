package online.ahayujie.project.controller;


import io.swagger.annotations.ApiOperation;
import online.ahayujie.project.bean.dto.SectionCreateParam;
import online.ahayujie.project.bean.model.Section;
import online.ahayujie.project.bean.model.SectionCate;
import online.ahayujie.project.core.Result;
import online.ahayujie.project.service.SectionCateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
}
