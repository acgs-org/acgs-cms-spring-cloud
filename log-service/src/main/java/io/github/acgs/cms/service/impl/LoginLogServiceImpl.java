package io.github.acgs.cms.service.impl;

import io.github.acgs.cms.entity.LoginLog;
import io.github.acgs.cms.repository.LoginLogRepository;
import io.github.acgs.cms.service.LoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *     登录日志服务实现类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/3/4
 * </p>
 */
@Slf4j
@Service
public class LoginLogServiceImpl implements LoginLogService {

    private final LoginLogRepository loginLogRepository;

    public LoginLogServiceImpl(LoginLogRepository loginLogRepository) {
        this.loginLogRepository = loginLogRepository;
    }

    @Override
    public void saveLog(LoginLog loginLog) {
        loginLogRepository.save(loginLog);
    }

    @Override
    public List<LoginLog> checkLogs() {
        return loginLogRepository.findAll();
    }

    @Override
    public void exportLogs() {
    }
}
