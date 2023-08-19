package com.projekt.forum.controllers;

import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.entity.CategoryEntity;
import com.projekt.forum.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    private final CategoryRepository categoryRepository;
    private final AlertManager alertManager;
    @Autowired
    public HomeController(CategoryRepository categoryRepository, AlertManager alertManager){
        this.categoryRepository = categoryRepository;
        this.alertManager = alertManager;
    }

    @ResponseBody
    @GetMapping("/list")
    public List<CategoryEntity> listThemAll (){

        return categoryRepository.findAll();
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("categories",categoryRepository.findAll());
        model.addAttribute("atr_alertManager",alertManager);
        return "Home";
    }

}
