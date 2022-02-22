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
 * 帖子回复
 * </p>
 *
 * @author aha
 * @since 2022-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("blog_reply")
@ApiModel(value="BlogReply对象", description="帖子回复")
public class BlogReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date updateTime;

    private Date createTime;

    @ApiModelProperty(value = "帖子id")
    private Long blogId;

    @ApiModelProperty(value = "帖子标题")
    private String blogTitle;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "评论id")
    private Long commentId;


}
