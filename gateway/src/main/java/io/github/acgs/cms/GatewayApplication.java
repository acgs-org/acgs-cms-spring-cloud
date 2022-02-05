package io.github.acgs.cms;

import io.github.acgs.cms.client.AuthorizationClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>
 *     网关 gateway 服务启动类
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/1/30
 * </p>
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(clients = {AuthorizationClient.class})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
