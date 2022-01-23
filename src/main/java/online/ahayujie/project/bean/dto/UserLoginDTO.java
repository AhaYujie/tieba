package online.ahayujie.project.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author aha
 * @date 2020/6/8
 */
@Data
@ApiModel(value = "用户登录响应结果")
public class UserLoginDTO {
    @ApiModelProperty(value = "accessToken")
    private String accessToken;

    @ApiModelProperty(value = "refreshToken")
    private String refreshToken;

    @ApiModelProperty(value = "accessToken过期时间")
    private Long expireIn;

    public UserLoginDTO(String accessToken, String refreshToken, Long expireIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireIn = expireIn;
    }
}
