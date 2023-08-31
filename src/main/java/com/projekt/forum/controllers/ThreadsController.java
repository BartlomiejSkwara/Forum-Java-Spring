package com.projekt.forum.controllers;

import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.entity.CategoryEntity;
import com.projekt.forum.repositories.CategoryRepository;
import com.projekt.forum.repositories.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller()
public class ThreadsController {

    private final ThreadRepository threadRepository;
    private final AlertManager alertManager;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ThreadsController(ThreadRepository threadRepository, AlertManager alertManager, CategoryRepository categoryRepository){

        this.threadRepository = threadRepository;
        this.alertManager = alertManager;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping(path = {"/category","/category/","/category/{categoryUrl}"})
    public String displayThreads(Model model, @PathVariable(required = false, name = "categoryUrl") String categoryUrl){
        if(categoryUrl==null||categoryUrl.isEmpty()){
            model.addAttribute("atr_errMSG","Drogi użytkowniku kategoria której szukasz nie istnieje :<");
            return "Error";
        }
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByUrl(categoryUrl);
        if(categoryEntity.isEmpty()){
            model.addAttribute("atr_errMSG","Drogi użytkowniku kategoria której szukasz nie istnieje :<");
            return "Error";
        }
        model.addAttribute("atr_threads",threadRepository.findAllByCategoryUrl(categoryUrl));
        model.addAttribute("atr_alertManager",alertManager);
        model.addAttribute("atr_title",categoryEntity.get().getName());

        return "Threads";
    }

}
