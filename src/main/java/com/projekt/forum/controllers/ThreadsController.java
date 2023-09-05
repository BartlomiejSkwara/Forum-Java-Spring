package com.projekt.forum.controllers;

import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.entity.CategoryEntity;
import com.projekt.forum.repositories.CategoryRepository;
import com.projekt.forum.repositories.ThreadRepository;
import com.projekt.forum.services.ThreadsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller()
public class ThreadsController {

    private final ThreadRepository threadRepository;
    private final AlertManager alertManager;
    private final CategoryRepository categoryRepository;
    private final ThreadsService threadsService;

    @Autowired
    public ThreadsController(ThreadRepository threadRepository, AlertManager alertManager, CategoryRepository categoryRepository, ThreadsService threadsService){

        this.threadRepository = threadRepository;
        this.alertManager = alertManager;
        this.categoryRepository = categoryRepository;
        this.threadsService = threadsService;
    }

    @GetMapping(path = {"/category","/category/","/category/{categoryUrl}"})
    public String displayThreads(Model model,
                                 @PathVariable(required = false, name = "categoryUrl") String categoryUrl,
                                 @RequestParam(defaultValue = "0", name = "page", required = false) Integer pageNumber)
    {
        if(categoryUrl==null||categoryUrl.isEmpty()){
            model.addAttribute("atr_errMSG","Drogi użytkowniku kategoria której szukasz nie istnieje :<");
            return "Error";
        }
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByUrl(categoryUrl);
        if(categoryEntity.isEmpty()){
            model.addAttribute("atr_errMSG","Drogi użytkowniku kategoria której szukasz nie istnieje :<");
            return "Error";
        }

        model.addAttribute("atr_title",categoryEntity.get().getName());
        model.addAttribute("atr_threads", threadsService.getThreadsByCategory(categoryUrl,pageNumber));
        model.addAttribute("atr_alertManager",alertManager);
        model.addAttribute("atr_category",categoryEntity.get());


        return "ThreadsInCategory";
    }

}
