package online.ahayujie.project.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import online.ahayujie.project.core.Result;
import online.ahayujie.project.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author aha
 * @since 2020/11/19
 */
@RestController
@RequestMapping("file")
@Api(tags = "文件上传")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @ApiOperation(value = "上传文件")
    @PostMapping("upload")
    public Result<String> upload(@RequestParam MultipartFile file) {
        try {
            return Result.data(fileService.upload(file));
        } catch (IOException e) {
            return Result.fail(e.getMessage());
        }
    }
}
