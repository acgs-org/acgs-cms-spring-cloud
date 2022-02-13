package io.github.acgs.cms.vo;

import lombok.Data;

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
public class ResponseVO<T> {

    /** 状态码 */
    private Integer code;

    /** 响应信息 */
    private String message;

    /** 返回结果 */
    private T result;

    private boolean success;
}
