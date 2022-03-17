package online.ahayujie.project.controller;


import io.swagger.annotations.ApiOperation;
import online.ahayujie.project.bean.dto.UserLoginDTO;
import online.ahayujie.project.bean.dto.UserLoginParam;
import online.ahayujie.project.bean.dto.UserRegisterParam;
import online.ahayujie.project.core.Result;
import online.ahayujie.project.exception.DuplicateUsernameException;
import online.ahayujie.project.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author aha
 * @since 2022-01-21
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public Result<Object> register(@Valid @RequestBody UserRegisterParam param) {
        try {
            userService.register(param);
            return Result.success();
        } catch (DuplicateUsernameException e) {
            return Result.fail("用户名已存在");
        }
    }

    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    public Result<UserLoginDTO> login(@Valid @RequestBody UserLoginParam param) {
        try {
            return Result.data(userService.login(param));
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            return Result.fail("用户名或密码错误");
        }
    }

    @GetMapping(value = "/hello")
    public Result<String> hello() {
        return Result.success("hello world");
    }

    @ApiOperation(value = "是否是管理员")
    @PostMapping(value = "/is-admin")
    public Result<Boolean> isAdmin() {
        return Result.data(userService.isAdmin());
    }

}
