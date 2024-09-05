package com.example.obs;

import com.example.obs.controller.ItemController;
import com.example.obs.model.Item;
import com.example.obs.service.ItemService;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(ItemController.class)
public class ItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ItemService service;

    @InjectMocks
    private ItemController itemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    void testSaveSuccess() throws Exception {
        Item itemRequest = new Item(); // Set appropriate fields if needed
        Item savedItem = new Item(); // Set appropriate fields if needed
        savedItem.setId(1L); // Example field

        when(service.save(itemRequest)).thenReturn(savedItem);

        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"Add data success\",\"status\":200,\"data\":{\"id\":1}}"));
    }
}
