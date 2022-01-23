package online.ahayujie.project.mapper;

import online.ahayujie.project.bean.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author aha
* @since 2022-01-21
*/
@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名查询
     * @param username 用户名
     * @return 用户
     */
    User selectByUsername(String username);
}
