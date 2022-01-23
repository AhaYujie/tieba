package online.ahayujie.project.service;

import online.ahayujie.project.bean.dto.UserLoginDTO;
import online.ahayujie.project.bean.dto.UserLoginParam;
import online.ahayujie.project.bean.dto.UserRegisterParam;
import online.ahayujie.project.bean.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import online.ahayujie.project.exception.DuplicateUsernameException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author aha
 * @since 2022-01-21
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册。
     * @param param 用户注册参数
     * @throws DuplicateUsernameException 重复用户名
     */
    void register(UserRegisterParam param) throws DuplicateUsernameException;

    /**
     * 用户登录
     * @param param 用户登录参数
     * @return 登录结果
     * @throws UsernameNotFoundException 用户不存在
     * @throws BadCredentialsException 密码错误
     */
    UserLoginDTO login(UserLoginParam param) throws UsernameNotFoundException, BadCredentialsException;

}
