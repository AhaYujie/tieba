package online.ahayujie.project.service.impl;

import io.jsonwebtoken.Claims;
import online.ahayujie.project.bean.model.User;
import online.ahayujie.project.security.jwt.TokenProvider;
import online.ahayujie.project.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class CommonServiceImpl implements CommonService {
    @Value("${jwt.header}")
    private String jwtHeader;

    @Value("${jwt.header-prefix}")
    private String jwtHeaderPrefix;

    private TokenProvider tokenProvider;

    @Override
    public User getUserFromToken() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            return null;
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String bearerToken = request.getHeader(jwtHeader);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtHeaderPrefix)) {
            return getUserFromToken(bearerToken.substring(jwtHeaderPrefix.length()));
        }
        return null;
    }

    @Override
    public User getUserFromToken(String token) {
        Claims claims;
        try {
            claims = tokenProvider.getClaimsFromAccessToken(token);
        }
        catch (Exception e) {
            try {
                claims = tokenProvider.getClaimsFromRefreshToken(token);
            }
            catch (Exception e1) {
                return null;
            }
        }
        User user = new User();
        user.setId(claims.get("id", Long.class));
        user.setUsername(claims.get("username", String.class));
        return user;
    }

    @Autowired
    public void setTokenProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
}
