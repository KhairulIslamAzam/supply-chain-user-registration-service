package com.bs23.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    private String apiVersion = "1.0.0";

    @Bean
    public GroupedOpenApi api(){
        return GroupedOpenApi.builder()
                .group("Registration Service Service APIs")
                .pathsToExclude("/")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
//                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
//                .components(getSecurityComponents())
                .info(getApiInfo());
    }

//    private Components getSecurityComponents(){
//        return new Components()
//                .addSecuritySchemes(SECURITY_SCHEME_NAME,
//                        new SecurityScheme()
//                                .type(SecurityScheme.Type.HTTP)
//                                .scheme("bearer")
//                                .bearerFormat("JWT")
//                );
//    }

    private Info getApiInfo(){
        return new Info()
                .title("Registration Service API")
                .version(apiVersion)
                .description("A RESTful API Service for Registration  Service Application")
                .contact(getContactInfo())
                .termsOfService("http://www.brainstation-23.com/terms/")
                .license(new License().name("BR 2.0").url("http://www.brainstation-23.com"));
    }

    private Contact getContactInfo(){
        return new Contact()
                .name("Brain Station 23 Ltd.")
                .email("info@brainstation23.com")
                .url("https://www.brainstation23.com");
    }
}
