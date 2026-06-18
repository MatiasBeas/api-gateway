package cl.Proyecto.api_gateway.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Configuration
public class SwaggerConfig {

    @Bean
    public RouterFunction<ServerResponse> swaggerRoutes() {
        return RouterFunctions.route()
                .GET("/v3/api-docs/prevision",  req -> proxyDocs("http://localhost:8081/v3/api-docs"))
                .GET("/v3/api-docs/paciente",   req -> proxyDocs("http://localhost:8082/v3/api-docs"))
                .GET("/v3/api-docs/historial",  req -> proxyDocs("http://localhost:8083/v3/api-docs"))
                .GET("/v3/api-docs/factura",    req -> proxyDocs("http://localhost:8084/v3/api-docs"))
                .GET("v3/api-docs/examenes",req -> proxyDocs("http://localhost:8203/v3/api-docs"))
                .build();
    }

    private ServerResponse proxyDocs(String targetUrl) {
        try {
            URL url = new URL(targetUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            InputStream is = conn.getInputStream();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            is.close();

            return ServerResponse.ok()
                    .header("Content-Type", "application/json")
                    .body(body);
        } catch (Exception e) {
            return ServerResponse.status(500)
                    .body("Error: " + e.getMessage());
        }
    }
}