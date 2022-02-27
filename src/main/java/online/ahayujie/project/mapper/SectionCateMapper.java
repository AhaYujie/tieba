package online.ahayujie.project.mapper;

import online.ahayujie.project.bean.model.SectionCate;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author aha
* @since 2022-02-27
*/
@Mapper
@Component
public interface SectionCateMapper extends BaseMapper<SectionCate> {

}
