package com.projekt.forum.controllers;


import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.entity.CategoryEntity;
import com.projekt.forum.repositories.CategoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Optional;

@Controller
public class CategoryCRUDController {


    CategoryRepository categoryRepository;
    AlertManager alertManager;

    @Autowired
    public CategoryCRUDController(CategoryRepository categoryRepository, AlertManager alertManager){
        this.categoryRepository = categoryRepository;
        this.alertManager = alertManager;
    }


    private boolean deleteWithResponse(String categoryID){
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryID);
        if (categoryEntity.isPresent()){
            categoryRepository.delete(categoryEntity.get());
            return true;
        }

        return false;
    }


    @GetMapping(path={"/deleteCategory/{categoryID}","/deleteCategory","/deleteCategory/"})
    public void deleteCategory(@PathVariable(required = false) String categoryID, HttpServletResponse httpServletResponse ) throws IOException {

        if (categoryID!=null)
        {
            if(deleteWithResponse(categoryID))
            {
                alertManager.addAlert(new Alert("Poprawnie usunięto kategorię: "+categoryID, Alert.AlertType.SUCCESS));
            }else
            {
                alertManager.addAlert(new Alert("Nie powiodło się usunięcie kategorii: "+categoryID, Alert.AlertType.DANGER));
            }
        }
        else
        {
            alertManager.addAlert(new Alert("Nie sprecyzowano ID kategorii do usunięcia !!!", Alert.AlertType.WARNING));
        }

        httpServletResponse.sendRedirect("/");
    }

    @GetMapping("/addCategory")
    public String addCategory(Model model){

        model.addAttribute("atr_title", "Nowa kategoria");
        return "CategoryCU";
    }

}
