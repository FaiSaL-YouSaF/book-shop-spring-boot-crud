package com.faisalyousaf777.BookStore.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ITIndexController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("HomePage - Should Return Welcome Message")
    void testHomePage_shouldReturnWelcomeMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/v1/"))
                            .andExpect(status().isOk())
                            .andExpect(content().string("Welcome to the Home Page."));
    }
}
