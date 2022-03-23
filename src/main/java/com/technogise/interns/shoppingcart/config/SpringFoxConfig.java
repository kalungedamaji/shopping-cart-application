package com.technogise.interns.shoppingcart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;


@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.technogise.interns.shoppingcart"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Technogise Shopping Cart API",
                "Sample API for Technogise tutorial",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact("VISHAL RAJ", "http://technogise.com", "a@b.com"),
                "API Licence",
                "http://technogise.com",
                Collections.emptyList());
    }
}