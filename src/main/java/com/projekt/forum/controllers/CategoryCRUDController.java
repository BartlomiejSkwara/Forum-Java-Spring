package com.projekt.forum.controllers;


import com.projekt.forum.repositories.CategoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class CategoryCRUDController {

    @Autowired
    CategoryRepository categoryRepository;
    @GetMapping("/deleteCategory/{categoryID}")
    public void deleteCategory(@PathVariable() String categoryID, HttpServletResponse httpServletResponse) throws IOException {
        categoryRepository.deleteById(categoryID);
        httpServletResponse.sendRedirect("/");
    }
}
