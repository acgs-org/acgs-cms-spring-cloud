package io.github.acgs.cms.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

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
public class ResponseVO {

    /** 返回状态码 */
    private Integer code;

    /** 返回结果 */
    private Object result;

    /** 异常信息封装 */
    private List<String> errors;

    public ResponseVO() {
        this.code = 200;
    }
}
