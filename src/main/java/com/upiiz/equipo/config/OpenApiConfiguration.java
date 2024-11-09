package com.upiiz.equipo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Futbol",
                description = "Esta API proporciona acceso a los recursos de la API de un Equipo de Futbol",
                version = "1.0.0",
                contact = @Contact(
                        name = "Alonso Dominguez Lopez",
                        email = "adominguezl2100@alumno.ipn.mx",
                        url = "http://localhost:8081/contacto"
                ),
                license = @License(),
                termsOfService = "Este programa es publico ajeno a cualquier partido político"
        ),
        servers = {
                @Server(
                        description = "Servidor de pruebas",
                        url = "http://localhost:8081"
                ),
                @Server(
                        description = "Servidor de Produccion",
                        url = "https://adl-eje-08-crud-modelo-richardson.onrender.com"
                )
        },
        tags = {
                @Tag(
                        name = "Equipo",
                        description = "Endpoints para los recursos del equipo"
                ),
                @Tag(
                        name = "Jugadores",
                        description = "Endpoints para los recursos de jugadores"
                ),
                @Tag(
                        name = "Entrenadores",
                        description = "Endpoints para los recursos del entrenador"
                ),
                @Tag(
                        name = "Liga",
                        description = "Endpoints para los recursos de la liga"
                ),
                @Tag(
                        name = "Competencias",
                        description = "Endpoints para los recursos de las competencias"
                )
        }
)
public class OpenApiConfiguration {

}
