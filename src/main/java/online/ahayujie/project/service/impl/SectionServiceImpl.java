package online.ahayujie.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import online.ahayujie.project.bean.dto.MainPageDTO;
import online.ahayujie.project.bean.dto.SectionCreateParam;
import online.ahayujie.project.bean.dto.SectionUpdateParam;
import online.ahayujie.project.bean.model.Section;
import online.ahayujie.project.bean.model.SectionCate;
import online.ahayujie.project.core.Page;
import online.ahayujie.project.mapper.SectionCateMapper;
import online.ahayujie.project.mapper.SectionMapper;
import online.ahayujie.project.service.SectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private final SectionCateMapper sectionCateMapper;

    public SectionServiceImpl(SectionMapper sectionMapper, SectionCateMapper sectionCateMapper) {
        this.sectionMapper = sectionMapper;
        this.sectionCateMapper = sectionCateMapper;
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

    @Override
    public Page<MainPageDTO> getMainPage(Long pageNum, Long pageSize) {
        IPage<SectionCate> page = sectionCateMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNum, pageSize), new QueryWrapper<>());
        List<SectionCate> cates = page.getRecords();
        List<MainPageDTO> result = new ArrayList<>();
        for (SectionCate cate : cates) {
            Wrapper<Section> wrapper = new QueryWrapper<Section>().eq("cate_id", cate.getId());
            IPage<Section> sectionIPage = sectionMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(1, 5), wrapper);
            MainPageDTO item = new MainPageDTO();
            item.setSectionCate(cate);
            item.setSections(sectionIPage.getRecords());
            result.add(item);
        }
        return new Page<>(pageNum, pageSize, page.getPages(), page.getTotal(), result);
    }

    @Override
    public Page<Section> getSectionByCate(Long cateId, Long pageNum, Long pageSize) {
        Wrapper<Section> wrapper = new QueryWrapper<Section>().eq("cate_id", cateId);
        IPage<Section> page = sectionMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNum, pageSize), wrapper);
        return new Page<>(page);
    }

    @Override
    public List<Section> searchSection(String keyword) {
        Wrapper<Section> wrapper = new QueryWrapper<Section>().like("name", keyword);
        return sectionMapper.selectList(wrapper);
    }

    @Override
    public Page<Section> getSectionPage(Long pageNum, Long pageSize) {
        Wrapper<Section> wrapper = new QueryWrapper<>();
        IPage<Section> page = sectionMapper.selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNum, pageSize), wrapper);
        return new Page<>(page);
    }
}
