package online.ahayujie.project.security;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import online.ahayujie.project.mapper.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 默认实现例子
 * @author aha
 * @date 2020/4/22
 */
@Slf4j
@Service
public class JwtUserDetailServiceDefaultImpl implements JwtUserDetailService {
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public JwtUserDetailServiceDefaultImpl(PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities(Claims claims) {
        return new ArrayList<>();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        online.ahayujie.project.bean.model.User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<GrantedAuthority> authorityList = new ArrayList<>();
        return new User(username, user.getPassword(), authorityList);
    }

}
