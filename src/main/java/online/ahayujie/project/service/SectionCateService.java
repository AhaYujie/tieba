package online.ahayujie.project.service;

import online.ahayujie.project.bean.model.SectionCate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 板块分类 服务类
 * </p>
 *
 * @author aha
 * @since 2022-02-27
 */
public interface SectionCateService extends IService<SectionCate> {
    /**
     * 搜索板块分类
     * @param keyword
     * @return
     */
    List<SectionCate> search(String keyword);
}
