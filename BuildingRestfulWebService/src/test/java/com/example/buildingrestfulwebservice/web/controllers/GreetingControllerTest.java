package com.example.buildingrestfulwebservice.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testGreeting() throws Exception {

        mockMvc.perform(get("/greeting"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content").value("Hello, World!"));

    }

    @Test
    void testGreetingWithName() throws Exception {

        mockMvc.perform(get("/greeting").param("name", "JSH"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content").value("Hello, JSH!"));

    }
}