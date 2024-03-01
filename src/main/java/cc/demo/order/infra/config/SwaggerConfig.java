package cc.demo.order.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(
    ) {
        Info info = new Info()
                .title("payment")
                .description("RESTful API")
                .version("0.0.1");
        return new OpenAPI()
                .info(info)
                .components(new Components());
    }

    private ApiResponse createResponse(String description, Schema<?> schema) {
        return new ApiResponse()
                .description(description)
                .content(
                        new Content().addMediaType(
                                org.springframework.http.MediaType.APPLICATION_JSON_VALUE
                                , new MediaType().schema(schema)
                        )
                );
    }

}
