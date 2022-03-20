package online.ahayujie.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.ahayujie.project.bean.dto.UserLoginDTO;
import online.ahayujie.project.bean.dto.UserLoginParam;
import online.ahayujie.project.bean.dto.UserRegisterParam;
import online.ahayujie.project.bean.model.Admin;
import online.ahayujie.project.bean.model.Blog;
import online.ahayujie.project.bean.model.User;
import online.ahayujie.project.exception.DuplicateUsernameException;
import online.ahayujie.project.mapper.AdminMapper;
import online.ahayujie.project.mapper.UserMapper;
import online.ahayujie.project.security.jwt.TokenProvider;
import online.ahayujie.project.service.CommonService;
import online.ahayujie.project.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author aha
 * @since 2022-01-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final TokenProvider tokenProvider;
    private final CommonService commonService;
    private final AdminMapper adminMapper;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserMapper userMapper, TokenProvider tokenProvider, CommonService commonService, AdminMapper adminMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.tokenProvider = tokenProvider;
        this.commonService = commonService;
        this.adminMapper = adminMapper;
    }

    @Override
    public void register(UserRegisterParam param) throws DuplicateUsernameException {
        User user = new User();
        BeanUtils.copyProperties(param, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(new Date());
        try {
            userMapper.insert(user);
        } catch (DuplicateKeyException e) {
            throw new DuplicateUsernameException(e);
        }
    }

    @Override
    public UserLoginDTO login(UserLoginParam param) throws UsernameNotFoundException, BadCredentialsException {
        User user = userMapper.selectByUsername(param.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        if (!passwordEncoder.matches(param.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("auth", "");
        String accessToken = tokenProvider.createAccessToken(user.getUsername(), claims);
        String refreshToken = tokenProvider.createRefreshToken(user.getUsername(), claims);
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new UserLoginDTO(accessToken, refreshToken, tokenProvider.getAccessTokenValidityInSeconds());
    }

    @Override
    public Boolean isAdmin() {
        User user = commonService.getUserFromToken();
        Wrapper<Admin> wrapper = new QueryWrapper<Admin>().eq("user_id", user.getId());
        Admin admin = adminMapper.selectOne(wrapper);
        return admin != null && admin.getIsAdmin() == 1;
    }

    @Override
    public void registerAdmin(UserRegisterParam param) throws DuplicateUsernameException {
        User user = new User();
        BeanUtils.copyProperties(param, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateTime(new Date());
        try {
            userMapper.insert(user);
        } catch (DuplicateKeyException e) {
            throw new DuplicateUsernameException(e);
        }
        Admin admin = new Admin();
        admin.setCreateTime(new Date());
        admin.setUserId(user.getId());
        admin.setUsername(user.getUsername());
        admin.setIsAdmin(1);
        adminMapper.insert(admin);
    }

    @Override
    public UserLoginDTO loginAdmin(UserLoginParam param) throws UsernameNotFoundException, BadCredentialsException {
        User user = userMapper.selectByUsername(param.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        Wrapper<Admin> wrapper = new QueryWrapper<Admin>().eq("user_id", user.getId());
        Admin admin = adminMapper.selectOne(wrapper);
        if (admin == null || admin.getIsAdmin() != 1) {
            throw new UsernameNotFoundException("管理员不存在");
        }
        return login(param);
    }
}
