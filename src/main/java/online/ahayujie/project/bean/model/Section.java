package online.ahayujie.project.bean.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 论坛板块
 * </p>
 *
 * @author aha
 * @since 2022-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("section")
@ApiModel(value="Section对象", description="论坛板块")
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date updateTime;

    private Date createTime;

    @ApiModelProperty(value = "板块名称")
    private String name;

    @ApiModelProperty(value = "板块介绍")
    private String description;

    @ApiModelProperty(value = "板块图标")
    private String icon;


}
