package com.projekt.forum.controllers;

import com.projekt.forum.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CategoryCRUDController.class)
class CategoryCRUDControllerTest {

    @MockBean()
    private CategoryService categoryService;

    @Autowired()
    MockMvc mockMvc;

    @Test
    void deleteCategory() {
        //mockMvc.perform()
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