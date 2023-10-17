package cl.tenpo.rest.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI biuldOpenApi() {

        var apache = new License().name("Apache License Version 2.0").url("https://www.apache.org/licenses/LICENSE-2.0");

        var info = new Info()
                .title("Tenpo Challenge API")
                .version("1.0")
                .description("This API was created to the Tenpo challenge.")
                .license(apache);

        return new OpenAPI().info(info);
    }
}