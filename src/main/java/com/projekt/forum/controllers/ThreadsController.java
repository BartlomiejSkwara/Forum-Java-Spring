package com.projekt.forum.controllers;

import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.dataTypes.forms.CategoryFilterForm;
import com.projekt.forum.entity.CategoryEntity;
import com.projekt.forum.entity.UserEntity;
import com.projekt.forum.repositories.CategoryRepository;
import com.projekt.forum.repositories.ThreadRepository;
import com.projekt.forum.services.ThreadsService;
import com.projekt.forum.utility.RequestUtility;
import com.projekt.forum.utility.ValidationUtility;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Controller()
public class ThreadsController {

    private final AlertManager alertManager;
    private final CategoryRepository categoryRepository;
    private final ThreadsService threadsService;
    private final HttpServletResponse httpServletResponse;
    private final Integer defaultStartingPage = 0;

    @Autowired
    public ThreadsController( AlertManager alertManager, CategoryRepository categoryRepository, ThreadsService threadsService, HttpServletResponse httpServletResponse){

        this.alertManager = alertManager;
        this.categoryRepository = categoryRepository;
        this.threadsService = threadsService;
        this.httpServletResponse = httpServletResponse;
    }

    @GetMapping(path = {"/category","/category/","/category/{categoryUrl}"})
    public String displayThreads(Model model,
                                 @PathVariable(required = false, name = "categoryUrl") String categoryUrl)
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
        model.addAttribute("atr_threads", threadsService.getThreadsByCategory(categoryUrl,defaultStartingPage));
        model.addAttribute("atr_alertManager",alertManager);
        model.addAttribute("atr_category",categoryEntity.get());


        return "ThreadsInCategory";
    }

    @PostMapping(path = {"/category","/category/","/category/{categoryUrl}"})
    public String filterThreads(Model model,
                                @PathVariable(required = false, name = "categoryUrl") String categoryUrl,
                                @Valid @ModelAttribute() CategoryFilterForm categoryFilterForm, BindingResult bindingResult,
                                ValidationUtility validationUtility)
    {
        //TODO popraw działanie dodawania atrybutów do kolejneg przekierowania przy ajaxie , obecnie wogóle nie działą (chodzie o alerty)
        if(categoryUrl==null||categoryUrl.isEmpty()){
            model.addAttribute("atr_errMSG","Drogi użytkowniku kategoria której szukasz nie istnieje :<");
            RequestUtility.setupAjaxRedirectionHeaders(httpServletResponse,"Error");

            return "Blank";
        }
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByUrl(categoryUrl);
        if(categoryEntity.isEmpty()){
            model.addAttribute("atr_errMSG","Drogi użytkowniku kategoria której szukasz nie istnieje :<");
            RequestUtility.setupAjaxRedirectionHeaders(httpServletResponse,"Error");

            return "Blank";
        }

        validationUtility.ConvertValidationErrors(bindingResult,alertManager);
        //model.addAttribute("atr_title",categoryEntity.get().getName());
        model.addAttribute("atr_threads", threadsService.getThreadsByCategory(categoryUrl,categoryFilterForm));
        model.addAttribute("atr_alertManager", alertManager);
        model.addAttribute("atr_category", categoryEntity.get());
        RequestUtility.setupAjaxInsertionHeaders(httpServletResponse);

        return "ThreadsInCategory :: content-list";
    }


    @GetMapping("/deleteThread")
    public String deleteThread( @RequestParam(name = "id", required = false) Integer threadId,
                                @RequestParam(name = "category",required = false) String category,
                                @AuthenticationPrincipal UserDetails user) throws IOException {

        threadsService.deleteThread(threadId,user);
        if (category!=null)
            return "redirect:/category/".concat(category);
        return "redirect:/";
    }


}
