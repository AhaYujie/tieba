package online.ahayujie.project.service;

import online.ahayujie.project.bean.dto.MainPageDTO;
import online.ahayujie.project.bean.dto.SectionCreateParam;
import online.ahayujie.project.bean.dto.SectionUpdateParam;
import online.ahayujie.project.bean.model.Section;
import com.baomidou.mybatisplus.extension.service.IService;
import online.ahayujie.project.core.Page;

/**
 * <p>
 * 论坛板块 服务类
 * </p>
 *
 * @author aha
 * @since 2022-01-22
 */
public interface SectionService extends IService<Section> {

    /**
     * 创建论坛板块
     * @param param 参数
     * @return 论坛
     */
    Section create(SectionCreateParam param);

    /**
     * 更新论坛板块
     * @param param 参数
     * @return 论坛
     */
    Section update(SectionUpdateParam param);

    /**
     * 获取首页数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<MainPageDTO> getMainPage(Long pageNum, Long pageSize);

}
