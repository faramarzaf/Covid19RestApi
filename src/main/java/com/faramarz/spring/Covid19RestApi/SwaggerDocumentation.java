package com.faramarz.spring.Covid19RestApi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Configuration
@EnableSwagger2WebMvc
public class SwaggerDocumentation {

    public static final Contact CONTACT = new Contact("Faramarz", "https://faramarzaf.github.io/", "faramarzafzali@gmail.com");
    public static final ApiInfo DEFAULT_API = new ApiInfo("Covid19 Restful API documentation", "Swagger Documentation", "1.0", "urn:tos", CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());
    public static final Set<String> consumes = new HashSet<>(Arrays.asList("application/json"));
    public static final Set<String> produces = new HashSet<>(Arrays.asList("application/json"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API).consumes(consumes).produces(produces);
    }

}