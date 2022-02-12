package io.github.acgs.cms.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     全局 Swagger 文档配置
 * </p>
 *
 * @author TierneyJohn@ACGS
 * <p>
 * file created on 2022/2/12
 * </p>
 */
@EnableOpenApi
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build()
                .globalResponses(HttpMethod.GET, getGlobalResponseMessage())
                .globalResponses(HttpMethod.POST, getGlobalResponseMessage());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ACGS-CMS API 接口文档")
                .description("GitHub: https://github.com/acgs-org/")
                .contact(new Contact("TierneyJohn", "https://github.com/tierneyjohn", "Tierney-John@hotmail.com"))
                .version("1.0.0")
                .build();
    }

    private List<Response> getGlobalResponseMessage() {
        List<Response> list = new ArrayList<>();
        list.add(new ResponseBuilder().code("404").description("找不到资源").build());
        return list;
    }
}
