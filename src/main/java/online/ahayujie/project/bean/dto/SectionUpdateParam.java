package online.ahayujie.project.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author aha
 * @since 2022/1/22
 */
@Data
@ApiModel(value = "论坛板块更新参数")
public class SectionUpdateParam {
    @ApiModelProperty(value = "板块id", required = true)
    @NotNull(message = "板块id不能为空")
    private Long id;

    @ApiModelProperty(value = "板块名称", required = true)
    @NotEmpty(message = "板块名称不能为空")
    private String name;

    @ApiModelProperty(value = "板块介绍")
    private String description;

    @ApiModelProperty(value = "板块图标")
    private String icon;
}
