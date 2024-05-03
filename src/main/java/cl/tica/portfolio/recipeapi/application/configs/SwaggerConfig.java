package cl.tica.portfolio.recipeapi.application.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    OpenAPI openApi() {
        License apacheLicence = new License()
                .name("Apache 2.0 License")
                .url("https://choosealicense.com/licenses/apache-2.0/");

        Contact contact = new Contact();
        contact.name("Recipe API");
        contact.setUrl("https://github.com/rgdevment/RecipeApi");

        return new OpenAPI()
                .info(new Info().title("Recipe API")
                        .description("API for managing recipes")
                        .version("1.0.0")
                        .contact(contact)
                        .license(apacheLicence)
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(
                        new Components()
                                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                );
    }
}
