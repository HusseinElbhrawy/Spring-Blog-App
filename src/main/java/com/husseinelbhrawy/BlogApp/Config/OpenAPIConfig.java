package com.husseinelbhrawy.BlogApp.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(

        info =@Info(
                title = "Blog API ",
                version = "1.0",
                description = "API documentation for Blog API Project",
                summary = "Blog API Summary",
                license = @License(name = "Hussein Elbhrawy", url = "https://github.com/husseinelbhrawy"),
                extensions = @Extension(name = "x-logo", properties = {}),
                termsOfService = "https://github.com/husseinelbhrawy",
                contact = @Contact(email = "hussein.elbhrway74@gmail.com", name = "Hussein Elbhrawy", url = "https://github.com/husseinelbhrawy")
        ),
        servers = {
                @io.swagger.v3.oas.annotations.servers.Server(description = "Local ENV", url = "http://localhost:8080"),
                @io.swagger.v3.oas.annotations.servers.Server(description = "PROD ENV" ,  url = "https://github.com/husseinelbhrawy"),
        },
        security = @SecurityRequirement(
                name = "bearerAuth",
                scopes = "read, write"
        )




)
@SecuritySchemes(
        {
                @SecurityScheme(
                        name = "bearerAuth",
                        description = "Enter JWT Bearer token",
                        type = SecuritySchemeType.HTTP,
                        scheme = "bearer",
                        bearerFormat = "JWT",
                        in = SecuritySchemeIn.HEADER
                ),
        }

)
public class OpenAPIConfig {
}
