package com.projekt.forum.controllers;

import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;


@WebMvcTest(controllers = HomeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private AlertManager alertManager;
    @Autowired
    private MockMvc mockMvc;
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void home_smokeTest(){
        Assertions.assertNotNull(this.categoryRepository);
        Assertions.assertNotNull(this.alertManager);
        Assertions.assertNotNull(this.mockMvc);


    }

    @Test
    void home_get() throws Exception{
        mockMvc.perform(get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("categories"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("atr_alertManager"))
                .andExpect(MockMvcResultMatchers.view().name("Home"));
    }



}