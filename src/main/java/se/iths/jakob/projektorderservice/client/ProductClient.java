package se.iths.jakob.projektorderservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import se.iths.jakob.projektorderservice.dto.ProductInfo;
import se.iths.jakob.projektorderservice.dto.ProductStockRequest;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ProductClient {

    private RestClient restClient;


    public ProductClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<ProductInfo> decreaseStock(List<ProductStockRequest> items) {
        return restClient.post()
                .uri("/products/stock/decrease")
                .body(items)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

    }

}
