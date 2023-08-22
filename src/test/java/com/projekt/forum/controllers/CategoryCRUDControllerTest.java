package com.projekt.forum.controllers;

import com.projekt.forum.services.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = CategoryCRUDController.class)
@AutoConfigureMockMvc()
class CategoryCRUDControllerTest {

    @MockBean()
    private CategoryService categoryService;

    @Autowired()
    MockMvc mockMvc;

    @Test
    void deleteCategory() throws Exception {
        String url = "muzyka";

        mockMvc.perform(MockMvcRequestBuilders.get("/deleteCategory/{url}",url))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"))
                ;
//
//        mockMvc.perform(get("/deleteCategory/{url}", "url2"))
//                .andExpect(status().is);

        //.andExpect(status())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"creditCardNumber\":\"4532756279624064\"}")
//                        .andExpect(status().isCreated())
//                        .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/order/1/receipt"))
    }

    @Test
    void editCategoryGet() {
    }

    @Test
    void editCategoryPost() {
    }

    @Test
    void addCategoryGet() {
    }

    @Test
    void addCategoryPost() {
    }
}