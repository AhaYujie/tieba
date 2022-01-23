package online.ahayujie.project.service.impl;

import online.ahayujie.project.bean.dto.SectionCreateParam;
import online.ahayujie.project.bean.dto.SectionUpdateParam;
import online.ahayujie.project.bean.model.Section;
import online.ahayujie.project.mapper.SectionMapper;
import online.ahayujie.project.service.SectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 论坛板块 服务实现类
 * </p>
 *
 * @author aha
 * @since 2022-01-22
 */
@Service
public class SectionServiceImpl extends ServiceImpl<SectionMapper, Section> implements SectionService {
    private final SectionMapper sectionMapper;

    public SectionServiceImpl(SectionMapper sectionMapper) {
        this.sectionMapper = sectionMapper;
    }

    @Override
    public Section create(SectionCreateParam param) {
        Section section = new Section();
        BeanUtils.copyProperties(param, section);
        section.setCreateTime(new Date());
        sectionMapper.insert(section);
        return section;
    }

    @Override
    public Section update(SectionUpdateParam param) {
        Section section = sectionMapper.selectById(param.getId());
        if (section == null)
            return null;
        BeanUtils.copyProperties(param, section);
        sectionMapper.updateById(section);
        return section;
    }
}
