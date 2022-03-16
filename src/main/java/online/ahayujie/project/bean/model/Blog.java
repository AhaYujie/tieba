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
 * 帖子
 * </p>
 *
 * @author aha
 * @since 2022-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("blog")
@ApiModel(value="Blog对象", description="帖子")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date updateTime;

    private Date createTime;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "发帖人id")
    private Long userId;

    @ApiModelProperty(value = "发帖人用户名")
    private String username;

    @ApiModelProperty(value = "板块id")
    private Long sectionId;

    @ApiModelProperty(value = "板块名称")
    private String sectionName;

    @ApiModelProperty(value = "标签")
    private String tag;

    @ApiModelProperty(value = "浏览数")
    private Long views;

}
