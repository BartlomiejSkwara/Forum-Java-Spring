package com.projekt.forum.controllers;


import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.dataTypes.forms.CategoryCUForm;
import com.projekt.forum.entity.CategoryEntity;
import com.projekt.forum.repositories.CategoryRepository;
import com.projekt.forum.services.CategoryService;
import com.projekt.forum.utility.ValidationUtility;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Optional;

@Controller
public class CategoryCRUDController {

    AlertManager alertManager;
    ValidationUtility validationUtility;
    CategoryService categoryService;
    HttpServletResponse httpServletResponse;
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryCRUDController(AlertManager alertManager, ValidationUtility validationUtility, CategoryService categoryService, HttpServletResponse httpServletResponse, CategoryRepository categoryRepository ){
        this.alertManager = alertManager;
        this.validationUtility = validationUtility;
        this.categoryService = categoryService;
        this.httpServletResponse = httpServletResponse;
        this.categoryRepository = categoryRepository;
    }





    @GetMapping(path={"/deleteCategory/{categoryID}","/deleteCategory","/deleteCategory/"})
    public String deleteCategory(@PathVariable(required = false) String categoryID ) throws IOException {

        categoryService.deleteCategory(categoryID);
        return "redirect:/";
    }


    @GetMapping(path = {"/editCategory","/editCategory/","/editCategory/{categoryID}"})
    public String editCategoryGet(Model model, @PathVariable(required = false) String categoryID){

        if (categoryID==null||categoryID.isEmpty()){
            alertManager.addAlert(new Alert("Nie sprecyzowano ID kategorii do edycji !!!", Alert.AlertType.WARNING));

        }
        else {

            Optional<CategoryEntity> categoryEntity = categoryRepository.findByIdcategory(categoryID);
            if (categoryEntity.isEmpty()){
                alertManager.addAlert(new Alert("Nie można edytować nieistniejącej kategorii !!!", Alert.AlertType.WARNING));

            }
            else{
                model.addAttribute("atr_title","Edycja Kategorii: "+categoryEntity.get().getName());

                model.addAttribute("atr_previousForm",new CategoryCUForm(categoryEntity.get().getName(),categoryEntity.get().getDescription()));
                model.addAttribute("atr_editedCategoryID",categoryID);

                return "CategoryEditing";
            }

        }
        return "redirect:/";
    }

    @PostMapping(path = {"/editCategory","/editCategory/","/editCategory/{categoryID}"})
    public String editCategoryPost(@Valid @ModelAttribute() CategoryCUForm categoryCUForm,BindingResult bindingResult,
                                   @PathVariable(required = false) String categoryID,
                                   HttpServletResponse httpServletResponse, Model model ){

        if (categoryID==null||categoryID.isEmpty()){
            alertManager.addAlert(new Alert("Nie sprecyzowano ID kategorii do edycji !!!", Alert.AlertType.WARNING));
            return "redirect:/?";
        }
        else {

            if (validationUtility.ConvertValidationErrors(bindingResult, alertManager)) {
                model.addAttribute("atr_alertManager", alertManager);
                if (categoryService.editCategory(categoryCUForm, categoryID)) {
                    httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
                } else {
                    httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }

                return "redirect:/?";
            } else {
                model.addAttribute("atr_editedCategoryID", categoryID);
                model.addAttribute("atr_title", "Edycja Kategorii: " + categoryID);
                model.addAttribute("atr_previousForm", categoryCUForm);
                model.addAttribute("atr_alertManager", alertManager);
                return "CategoryEditing :: content";
            }
        }
    }


    @GetMapping("/addCategory")
    public String addCategoryGet(Model model){

        model.addAttribute("atr_title", "Dodawanie Kategorii");
        return "CategoryCreation";
    }

    @PostMapping("/addCategory")
    public String addCategoryPost(@Valid @ModelAttribute() CategoryCUForm categoryCUForm, BindingResult bindingResult,
                                   Model model) throws IOException {
        model.addAttribute("atr_title", "Dodawanie Kategorii");
        if(validationUtility.ConvertValidationErrors(bindingResult,this.alertManager)){
            if (!categoryService.addCategory(categoryCUForm)){
                model.addAttribute("atr_previousForm",categoryCUForm);
            }
        }
        else {
            model.addAttribute("atr_previousForm",categoryCUForm);
        }

        model.addAttribute("atr_alertManager",alertManager);
        return "CategoryCreation :: content";


    }

}
