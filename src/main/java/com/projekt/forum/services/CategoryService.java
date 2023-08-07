package com.projekt.forum.services;

import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.dataTypes.forms.CategoryCUForm;
import com.projekt.forum.entity.CategoryEntity;
import com.projekt.forum.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;
    AlertManager alertManager;

    public CategoryService(CategoryRepository categoryRepository, AlertManager alertManager) {
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
    public void deleteCategory(String categoryID){
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
    }

    public boolean addCategory(CategoryCUForm categoryCUForm){
        return this.categoryRepository.save(new CategoryEntity(categoryCUForm))!=null;
    }
}
