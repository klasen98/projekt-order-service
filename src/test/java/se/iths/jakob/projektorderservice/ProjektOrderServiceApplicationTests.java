package se.iths.jakob.projektorderservice;


import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class ProjektOrderServiceApplicationTests {

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private ConnectionFactory connectionFactory;

    @Test
    void contextLoads() {
    }

}
