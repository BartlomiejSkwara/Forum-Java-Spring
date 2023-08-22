package com.projekt.forum.services;

import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.dataTypes.forms.CategoryCUForm;
import com.projekt.forum.entity.CategoryEntity;
import com.projekt.forum.repositories.CategoryRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;
    AlertManager alertManager;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, AlertManager alertManager) {
        this.categoryRepository = categoryRepository;
        this.alertManager = alertManager;
    }


    @Transactional
    public boolean editCategory(CategoryCUForm categoryCUForm){

        Optional<CategoryEntity> existingEntity = categoryRepository.findById(categoryCUForm.getCategoryID());
        if (existingEntity.isPresent()){
            categoryRepository.save(new CategoryEntity(categoryCUForm));
            alertManager.addAlert(new Alert("Edycja kategorii " + categoryCUForm.getCategoryID() + " zakończona sukcesem", Alert.AlertType.SUCCESS));
            return true;
        }
        else {
            alertManager.addAlert(new Alert("Próbowałeś zmienić nie istniejącą kategorię !!!", Alert.AlertType.DANGER));
            return false;
        }


    }


    public boolean deleteCategory(String categoryURL){
        if (categoryURL!=null)
        {

            Optional<CategoryEntity> categoryEntity = categoryRepository.findByUrl(categoryURL);

            if(categoryEntity.isPresent())
            {
                categoryRepository.delete(categoryEntity.get());
                alertManager.addAlert(new Alert("Poprawnie usunięto kategorię: "+categoryURL, Alert.AlertType.SUCCESS));
                return true;
            }else
            {
                alertManager.addAlert(new Alert("Nie powiodło się usunięcie kategorii: "+categoryURL, Alert.AlertType.DANGER));
            }
        }
        else
        {
            alertManager.addAlert(new Alert("Nie sprecyzowano ID kategorii do usunięcia !!!", Alert.AlertType.WARNING));
        }
        return false;
    }

    public boolean addCategory(CategoryCUForm categoryCUForm) {

        if(categoryRepository.findByName(categoryCUForm.getCategoryName()).isEmpty())
        {
            categoryRepository.save(new CategoryEntity(categoryCUForm));
            alertManager.addAlert(new Alert("Poprawnie dodano kategorię "+categoryCUForm.getCategoryName(), Alert.AlertType.SUCCESS));
            return true;
        }
        else
        {
            alertManager.addAlert(new Alert("Kategoria o podanej nazwie już istnieje !!! ", Alert.AlertType.WARNING));
            return false;
        }
    }
}
