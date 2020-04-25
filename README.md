## 简介
spring-boot-seed-project 是一个基于Spring Boot & MyBatis-plus
的种子项目，通过代码生成快速构建RESTful API项目。
![项目结构][1]

## 特征&提供
- 基于Mybatis-plus的代码生成器根据表名生成对应的Model、Mapper、MapperXML、
Service、ServiceImpl、Controller等基础代码
- 最佳实践的项目结构、配置文件、精简的POM
- 统一响应结果封装及生成工具
- 统一异常处理
- 使用Spring Security框架实现jwt认证
- 使用Druid Spring Boot Starter 集成Druid数据库连接池与监控
- 集成MyBatis-plus，实现单表业务零SQL
 
## 快速开始
1. 克隆项目
2. 对```test```包内的代码生成器```CodeGenerator```进行配置，主要是JDBC，因为要根据表名来生成代码
3. 输入表名，运行```CodeGenerator.main()```方法，生成基础代码
4. 根据业务在基础代码上进行扩展
5. 对开发环境配置文件```application-dev.yml```进行配置，启动项目.
 
## 开发建议
- 开发规范建议遵循阿里巴巴Java开发手册（[最新版下载](https://github.com/alibaba/p3c))
- 不需要jwt认证可以直接删除`security`包和`pom.xml`文件里的`spring-boot-starter-security`依赖
- 使用jwt认证需要实现`security/JwtUserDetailService`接口, 
然后删除`JwtUserDetailServiceDefaultImpl`这个bean类
(实现可参考`security/JwtUserDetailServiceDefaultImpl`类)
- jwt认证获取token代码例子：
```
@RestController
@RequestMapping("/test/user")
public class UserController {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("get-token")
    public Result<Object> getToken(@RequestParam String username, @RequestParam String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map<String, Object> claims = new HashMap<>();
        claims.put("auth", "admin");
        String accessToken = tokenProvider.createAccessToken(username, claims);
        String refreshToken = tokenProvider.createRefreshToken(username, claims);
        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        return Result.data(response);
    }

}
```


  [1]: https://aha-yujie.oss-cn-shenzhen.aliyuncs.com/project-structure.jpg
