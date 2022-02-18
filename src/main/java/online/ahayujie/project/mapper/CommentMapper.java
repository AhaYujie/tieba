package online.ahayujie.project.mapper;

import online.ahayujie.project.bean.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author aha
* @since 2022-02-18
*/
@Mapper
@Component
public interface CommentMapper extends BaseMapper<Comment> {

}
