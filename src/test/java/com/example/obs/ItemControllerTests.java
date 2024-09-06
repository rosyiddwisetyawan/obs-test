package com.example.obs;

import com.example.obs.controller.ItemController;
import com.example.obs.model.Item;
import com.example.obs.service.ItemService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

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

@WebMvcTest(ItemController.class)
public class ItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService service;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testSaveSuccess() throws Exception {
        Item itemRequest = new Item(); // Set appropriate fields if needed
        itemRequest.setName("pen");
        itemRequest.setPrice(50);
        Item savedItem = new Item(); // Set appropriate fields if needed
        savedItem.setName("pen");
        savedItem.setPrice(50);
        savedItem.setId(1L); // Example field

        when(service.save(itemRequest)).thenReturn(savedItem);

        // Act & Assert
        mockMvc.perform(post("/item/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Add data success")))
                .andExpect(jsonPath("$.status", is(200)))
                .andExpect(jsonPath("$.data.name", is("pen")))
                .andExpect(jsonPath("$.data.id", is(1)));
    }
}
