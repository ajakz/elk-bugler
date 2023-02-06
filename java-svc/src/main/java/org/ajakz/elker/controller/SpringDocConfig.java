package org.ajakz.elker.controller;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Value("${app.project.version:Unknown}")
    private String appVersion;

    @Bean
    public OpenAPI serviceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Elk Bugler Service API")
                        .description("This service provides access to Elkers and their Bugles.")
                        .version(appVersion));
    }
}
