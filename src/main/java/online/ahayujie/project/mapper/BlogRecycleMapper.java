package online.ahayujie.project.mapper;

import online.ahayujie.project.bean.model.BlogRecycle;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author aha
* @since 2022-03-20
*/
@Mapper
@Component
public interface BlogRecycleMapper extends BaseMapper<BlogRecycle> {

}
