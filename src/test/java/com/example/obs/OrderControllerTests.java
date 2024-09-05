package com.example.obs;

import com.example.obs.controller.OrderController;
import com.example.obs.model.Order;
import com.example.obs.service.InventoryService;
import com.example.obs.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(OrderController.class)
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService service;

    @InjectMocks
    private OrderController orderController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testSaveSuccess() throws Exception {
        // Arrange
        Order request = new Order(); // Set appropriate fields if needed
        request.setItemId(Long.parseLong("1"));
        request.setQty(Long.parseLong("50"));
        Order savedOrder = new Order(); // Set appropriate fields if needed
        savedOrder.setId(1L); // Example field
        savedOrder.setItemId(Long.parseLong("1"));
        savedOrder.setQty(Long.parseLong("50"));

        when(service.save(request)).thenReturn(savedOrder);

        // Act & Assert
        mockMvc.perform(post("/order/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Add data success")))
                .andExpect(jsonPath("$.status", is(200)))
                .andExpect(jsonPath("$.data.itemId", is(1)))
                .andExpect(jsonPath("$.data.id", is(1)));
    }
}
