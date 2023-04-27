package com.example.actuator;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
class HelloControllerTest {

    @LocalServerPort
    private int port;

    @Value("${local.management.port}")
    private int mgt;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @SuppressWarnings("unchecked")
    void shouldReturn200WhenSendingRequestToController() {

        ResponseEntity<Map> response = this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/hello-world", Map.class);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).containsKey("id");
        then(response.getBody()).containsKey("content");
    }

    @Test
    void shouldReturn200WhenSendingRequestToManagementEndpoint() {
        ResponseEntity<Map> response = this.testRestTemplate.getForEntity("http://localhost:" + this.mgt + "/actuator", Map.class);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}