package online.ahayujie.project.service;

import online.ahayujie.project.bean.model.User;

public interface CommonService {
    /**
     * 从token中获取用户信息
     * @return
     */
    User getUserFromToken();

    /**
     * 从token中获取用户信息
     * @param token
     * @return
     */
    User getUserFromToken(String token);
}
