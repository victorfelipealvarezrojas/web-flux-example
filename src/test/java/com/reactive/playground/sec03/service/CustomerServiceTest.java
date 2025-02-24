package com.reactive.playground.sec03.service;

import com.reactive.playground.sec03.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;


@AutoConfigureWebTestClient
@SpringBootTest(properties = {
        "sec=sec03",
})
class CustomerServiceTest {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceTest.class);

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CustomerService customerService;

    @Test
    void testGetAllCustomers() {
        this.webTestClient.get()
                .uri("/customer")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CustomerDto.class)
                .value(list -> list.forEach(customerDto -> log.info("CustomerDto: {}", customerDto)))
                .hasSize(10);
    }

    @Test
    void paginationCustomers() {
        this.webTestClient.get()
                .uri("/customer/pageable?page=3&size=2")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(response -> log.info("Response: {}", response))
                .jsonPath("$.length()").isEqualTo(2);
    }

}