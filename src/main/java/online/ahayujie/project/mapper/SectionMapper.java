package online.ahayujie.project.mapper;

import online.ahayujie.project.bean.model.Section;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author aha
* @since 2022-01-22
*/
@Mapper
@Component
public interface SectionMapper extends BaseMapper<Section> {

}
