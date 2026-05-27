package se.iths.jakob.projektorderservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import se.iths.jakob.projektorderservice.dto.ProductInfo;
import se.iths.jakob.projektorderservice.dto.ProductStockRequest;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestClient productRestClient;

    public List<ProductInfo> decreaseStock(List<ProductStockRequest> items, String bearerToken) {
        return productRestClient.post()
                .uri("/products/stock/decrease")
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .body(items)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
