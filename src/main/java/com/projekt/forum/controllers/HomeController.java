package com.projekt.forum.controllers;

import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.repositories.CategoryRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    private final CategoryRepository categoryRepository;
    private final AlertManager alertManager;
    private final HttpServletResponse httpServletResponse;

    @Autowired
    public HomeController(CategoryRepository categoryRepository, AlertManager alertManager, HttpServletResponse httpServletResponse){
        this.categoryRepository = categoryRepository;
        this.alertManager = alertManager;
        this.httpServletResponse = httpServletResponse;
    }

//    @ResponseBody
//    @GetMapping("/list")
//    public List<CategoryEntity> listThemAll (){
//
//        return categoryRepository.findAll();
//    }


    @GetMapping("/")
    public String home(Model model){

        model.addAttribute("categories",categoryRepository.findAll());
        model.addAttribute("atr_alertManager",alertManager);

        return "Home";
    }

}
