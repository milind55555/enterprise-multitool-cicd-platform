package com.example.platform;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class StatusControllerTest {
    @Autowired private MockMvc mvc;

    @Test
    void helloEndpointReturnsMessage() throws Exception {
        mvc.perform(get("/api/hello"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(
                "Hello from the Enterprise Multi-Tool CI/CD Platform"));
    }

    @Test
    void rootEndpointReturnsServiceMetadata() throws Exception {
        mvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.service").value("enterprise-demo"));
    }
}
