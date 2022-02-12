package io.github.acgs.cms.configuration;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;

/**
 * <p>
 *     FeignClient 自动装配文件
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/12
 * </p>
 */
@Order(-1)
@Configuration
@EnableFeignClients(basePackages = {"io.github.acgs.cms.client"})
public class FeignConfig {

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }
}
