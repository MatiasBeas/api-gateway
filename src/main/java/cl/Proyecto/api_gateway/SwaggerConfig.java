package cl.Proyecto.api_gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

@Configuration
public class SwaggerConfig {

    // Redirige /v3/api-docs/prevision → http://localhost:8081/v3/api-docs
    // El gateway ya enruta los microservicios por lb://, así que usamos las rutas del gateway mismo

    @Bean
    public RouterFunction<ServerResponse> swaggerRoutes() {
        return RouterFunctions.route()
                .GET("/v3/api-docs/prevision", req -> ServerResponse.temporaryRedirect(URI.create("/previsiones/v3/api-docs")).build())
                .GET("/v3/api-docs/paciente", req -> ServerResponse.temporaryRedirect(URI.create("/pacientes/v3/api-docs")).build())
                .GET("/v3/api-docs/historial", req -> ServerResponse.temporaryRedirect(URI.create("/historialMedico/v3/api-docs")).build())
                .GET("/v3/api-docs/staff", req -> ServerResponse.temporaryRedirect(URI.create("/api/v2/staff/v3/api-docs")).build())
                .GET("/v3/api-docs/factura", req -> ServerResponse.temporaryRedirect(URI.create("/api/facturas/v3/api-docs")).build())
                .build();
    }
}