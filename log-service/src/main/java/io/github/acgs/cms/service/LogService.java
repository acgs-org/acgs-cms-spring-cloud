package io.github.acgs.cms.service;

import java.util.List;

/**
 * <p>
 *     日志相关服务抽象类
 * </p>
 * <p>
 *     封装通用日志相关方法
 * </p>
 * @param <T> 日志处理实体类
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/3/4
 * </p>
 */
public interface LogService<T> {

    void saveLog(T log);

    List<T> checkLogs();

    void exportLogs();

}
