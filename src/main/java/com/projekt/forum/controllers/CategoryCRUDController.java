package com.projekt.forum.controllers;


import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.dataTypes.forms.CategoryCUForm;
import com.projekt.forum.entity.CategoryEntity;
import com.projekt.forum.repositories.CategoryRepository;
import com.projekt.forum.services.CategoryService;
import com.projekt.forum.utility.RequestUtility;
import com.projekt.forum.utility.ValidationUtility;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Optional;

@Controller
public class CategoryCRUDController {

    private final AlertManager alertManager;
    private final ValidationUtility validationUtility;
    private final CategoryService categoryService;
    private final HttpServletResponse httpServletResponse;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryCRUDController(AlertManager alertManager, ValidationUtility validationUtility, CategoryService categoryService, HttpServletResponse httpServletResponse, CategoryRepository categoryRepository ){
        this.alertManager = alertManager;
        this.validationUtility = validationUtility;
        this.categoryService = categoryService;
        this.httpServletResponse = httpServletResponse;
        this.categoryRepository = categoryRepository;
    }





    @GetMapping(path={"/deleteCategory/{categoryURL}","/deleteCategory","/deleteCategory/"})
    public String deleteCategory(@PathVariable(required = false) String categoryURL ) throws IOException {

        categoryService.deleteCategory(categoryURL);

        return "redirect:/";
    }


    @GetMapping(path = {"/editCategory","/editCategory/","/editCategory/{categoryURL}"})
    public String editCategoryGet(Model model, @PathVariable(required = false) String categoryURL){

        if (categoryURL==null||categoryURL.isEmpty()){
            alertManager.addAlert(new Alert("Nie wybrano kategorii do edycji !!!", Alert.AlertType.WARNING));
        }
        else {

            Optional<CategoryEntity> categoryEntity = categoryRepository.findByUrl(categoryURL);
            if (categoryEntity.isEmpty()){
                alertManager.addAlert(new Alert("Nie można edytować nie istniejącej kategorii !!!", Alert.AlertType.WARNING));
            }
            else{
                model.addAttribute("atr_title","Edycja Kategorii: "+categoryEntity.get().getName());
                model.addAttribute("atr_previousForm",new CategoryCUForm(categoryEntity.get().getCategoryID(),categoryEntity.get().getName(),categoryEntity.get().getDescription()));
                model.addAttribute("atr_editedCategoryURL",categoryURL);
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);

                return "CategoryEditing";
            }

        }
        return "redirect:/";
    }

    @PostMapping(path = {"/editCategory","/editCategory/","/editCategory/{categoryURL}"})
    public String editCategoryPost(@Valid @ModelAttribute() CategoryCUForm categoryCUForm,BindingResult bindingResult,
                                   @PathVariable(required = false) String categoryURL,
                                   Model model ){

        httpServletResponse.addHeader(RequestUtility.AjaxInsertParam, RequestUtility.OperationReplace);

        if (categoryURL==null||categoryURL.isEmpty()||categoryCUForm.getCategoryID()==null){
            alertManager.addAlert(new Alert("Nie sprecyzowano kategorii do edycji !!!", Alert.AlertType.WARNING));
            return "redirect:/";
        }
        else {
            if (validationUtility.ConvertValidationErrors(bindingResult, alertManager)) {
                categoryService.editCategory(categoryCUForm);
                return "redirect:/";

            } else {
                httpServletResponse.setHeader(RequestUtility.AjaxInsertParam, RequestUtility.OperationInsert);
                model.addAttribute("atr_editedCategoryURL", categoryURL);
                model.addAttribute("atr_title", "Edycja Kategorii: " + categoryURL);
                model.addAttribute("atr_previousForm", categoryCUForm);
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return "CategoryEditing :: content";
            }
        }
    }


    @GetMapping("/addCategory")
    public String addCategoryGet(Model model){

        model.addAttribute("atr_title", "Dodawanie Kategorii");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        return "CategoryCreation";
    }

    @PostMapping("/addCategory")
    public String addCategoryPost(@Valid @ModelAttribute() CategoryCUForm categoryCUForm, BindingResult bindingResult,
                                   Model model) throws IOException {
        model.addAttribute("atr_title", "Dodawanie Kategorii");
        if(validationUtility.ConvertValidationErrors(bindingResult,this.alertManager)){
            if (!categoryService.addCategory(categoryCUForm)){
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                model.addAttribute("atr_previousForm",categoryCUForm);
            }else {
                httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);

            }
        }
        else {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            model.addAttribute("atr_previousForm",categoryCUForm);

        }

        httpServletResponse.addHeader(RequestUtility.AjaxInsertParam, RequestUtility.OperationInsert);

        model.addAttribute("atr_alertManager",alertManager);
        return "CategoryCreation :: content";


    }

}
