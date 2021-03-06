package io.github.acgs.cms.util;

import java.util.Date;

/**
 * <p>
 *     日期时间工具类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/1/30
 * </p>
 */
public class DateUtil {

    /** 构造方法私有化 */
    private DateUtil() {}

    /**
     * 获取到期时间
     *
     * @param duration 延时时间， 单位 s
     * @return 延时后到期时间
     */
    public static Date getDurationDate(Long duration) {
        return new Date(System.currentTimeMillis() + duration * 1000);
    }
}
