package com.example.customerclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import java.awt.PageAttributes.MediaType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = CustomerClientApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(port = 8081)
@AutoConfigureJson
public class CustomerClientTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerClient client;

    String jsonForCustomer(Customer ... customers) throws JsonProcessingException {
        List<Customer> customerList = Arrays.asList(customers);
        return this.objectMapper.writeValueAsString(customerList);
    }

    @Test
    public void clientShouldReturnAllCustomers() throws Exception{

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/customers"))
            .willReturn(
                WireMock.aResponse()
                .withStatus(200)
                .withBody(jsonForCustomer(new Customer(1L, "Jane"),
                    new Customer(2L, "Bob")))));

        Collection<Customer> customers = this.client.getAllCustomers();
        BDDAssertions.then(customers).size().isEqualTo(2);
        BDDAssertions.then(customers.iterator().next().getId()).isEqualTo(1L);
        BDDAssertions.then(customers.iterator().next().getSurname()).isEqualTo("Jane");
    }
}
