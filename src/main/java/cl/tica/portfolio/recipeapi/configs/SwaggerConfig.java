package cl.tica.portfolio.recipeapi.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Value("${config.swagger.server.developer.url}")
    private String developerOpenApiUrl;

    @Value("${config.swagger.server.staging.url}")
    private String stagingOpenApiUrl;

    @Value("${config.swagger.server.production.url}")
    private String productionOpenApiUrl;

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

        Server developerServer = new Server();
        developerServer.setUrl(developerOpenApiUrl);
        developerServer.setDescription("Developer environment");

        Server stagingServer = new Server();
        stagingServer.setUrl(stagingOpenApiUrl);
        stagingServer.setDescription("Staging environment");

        Server productionServer = new Server();
        productionServer.setUrl(productionOpenApiUrl);
        productionServer.setDescription("Production environment");

        return new OpenAPI()
                .info(new Info().title("Recipe API")
                        .description("API for managing recipes")
                        .version("1.0.0")
                        .contact(contact)
                        .license(apacheLicence)
                )

                .servers(List.of(developerServer, stagingServer, productionServer))
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
