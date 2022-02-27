package online.ahayujie.project.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import online.ahayujie.project.bean.model.Section;
import online.ahayujie.project.bean.model.SectionCate;

import java.util.List;

@Data
@ApiModel(value = "首页")
public class MainPageDTO {
    @ApiModelProperty(value = "板块分类")
    private SectionCate sectionCate;

    @ApiModelProperty(value = "板块列表")
    private List<Section> sections;
}
