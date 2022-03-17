package online.ahayujie.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.ahayujie.project.bean.model.Section;
import online.ahayujie.project.bean.model.SectionCate;
import online.ahayujie.project.mapper.SectionCateMapper;
import online.ahayujie.project.service.SectionCateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 板块分类 服务实现类
 * </p>
 *
 * @author aha
 * @since 2022-02-27
 */
@Service
public class SectionCateServiceImpl extends ServiceImpl<SectionCateMapper, SectionCate> implements SectionCateService {
    @Override
    public List<SectionCate> search(String keyword) {
        Wrapper<SectionCate> wrapper = new QueryWrapper<SectionCate>().like("name", keyword);
        return baseMapper.selectList(wrapper);
    }
}
