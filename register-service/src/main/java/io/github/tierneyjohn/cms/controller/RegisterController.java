package io.github.tierneyjohn.cms.controller;

import io.github.tierneyjohn.cms.entity.User;
import io.github.tierneyjohn.cms.exception.ValidatedException;
import io.github.tierneyjohn.cms.repository.UserRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *     用户注册控制器
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/2
 * </p>
 */
@RestController
public class RegisterController {

    /** 导入用户信息仓储对象 */
    private final UserRepository userRepository;

    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * <p>
     *     用户注册接口
     * </p>
     *
     * @param user 用户注册信息
     * @param result 参数校验结果
     * @return 注册结果 true | false
     */
    @PostMapping("/register")
    public Boolean register(@RequestBody @Validated User user, BindingResult result) {
        if (result.getErrorCount() != 0) {
            throw new ValidatedException(result.getAllErrors());
        }
        return true;
    }
}
