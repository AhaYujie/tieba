package online.ahayujie.project.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author aha
 * @since 2022/1/22
 */
@Data
@ApiModel(value = "论坛板块创建参数")
public class SectionCreateParam {
    @ApiModelProperty(value = "板块名称", required = true)
    private String name;

    @ApiModelProperty(value = "板块介绍")
    private String description;

    @ApiModelProperty(value = "板块图标")
    private String icon;

    @ApiModelProperty(value = "分类id", required = true)
    private Long cateId;
}
