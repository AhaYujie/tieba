package online.ahayujie.project.security;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import online.ahayujie.project.security.jwt.TokenProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 默认实现例子
 * @author aha
 * @date 2020/4/22
 */
@Slf4j
@Service
public class JwtUserDetailServiceDefaultImpl implements JwtUserDetailService {

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    public JwtUserDetailServiceDefaultImpl(TokenProvider tokenProvider, PasswordEncoder passwordEncoder) {
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        String accessToken = "your accessToken";
        Claims claims = tokenProvider.getClaimsFromAccessToken(accessToken);
        return Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!"aha".equals(username)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new User(username, passwordEncoder.encode("123456"), new ArrayList<>());
    }

}
