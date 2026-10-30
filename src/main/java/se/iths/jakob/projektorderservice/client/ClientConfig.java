package se.iths.jakob.projektorderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Konfigurerar HTTP-klienten som order-service använder för att anropa product-service.
 */
@Configuration
public class ClientConfig {

    /**
     * Skapar en RestClient med URL till product-service.
     */
    @Bean
    public RestClient productRestClient(
            @Value("${app.product-service-url}")
            String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}




