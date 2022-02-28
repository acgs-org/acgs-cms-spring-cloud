package io.github.acgs.cms.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 *     返回结果封装类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/2
 * </p>
 */
@Data
@Builder
@ApiModel(value = "返回结果封装类")
public class ResponseVO<T> {

    /** 状态码 */
    @ApiModelProperty(value = "状态码")
    private Integer code;

    /** 响应信息 */
    @ApiModelProperty(value = "响应信息")
    private String message;

    /** 响应结果 */
    @ApiModelProperty(value = "返回结果")
    private T result;

    /** 响应状态 */
    @ApiModelProperty(value = "响应状态")
    private boolean success;

    /** 响应时间戳 */
    @ApiModelProperty(value = "响应时间戳")
    private LocalDateTime timestamp;
}
