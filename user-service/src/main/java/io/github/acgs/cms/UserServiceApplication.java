package io.github.acgs.cms;

import io.github.acgs.cms.client.AuthorizationClient;
import io.github.acgs.cms.client.RoleServiceClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>
 *     用户服务启动类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/5
 * </p>
 */
@SpringBootApplication
@EnableFeignClients(clients = {AuthorizationClient.class, RoleServiceClient.class})
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
