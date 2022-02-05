package io.github.acgs.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *     身份验证服务启动类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/5
 * </p>
 */
@SpringBootApplication
public class AuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }
}
