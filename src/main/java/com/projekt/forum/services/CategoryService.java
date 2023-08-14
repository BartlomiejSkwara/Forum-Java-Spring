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
    EntityManager entityManager;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, AlertManager alertManager, EntityManager entityManager) {
        this.categoryRepository = categoryRepository;
        this.alertManager = alertManager;
        this.entityManager = entityManager;
    }


    //TODO wprowdz zmiany w strukturze bd
    @Transactional
    public boolean editCategory(CategoryCUForm categoryCUForm, Integer categoryID){

        Optional<CategoryEntity> existingEntity = categoryRepository.findByCategoryID(categoryID);
        if (existingEntity.isPresent()){
            categoryRepository.save(new CategoryEntity(categoryCUForm));
            alertManager.addAlert(new Alert("Edycja kategorii " + categoryID + " zakończona sukcesem", Alert.AlertType.SUCCESS));
            return true;
        }
        else {
            alertManager.addAlert(new Alert("Próbowałeś zmienić nie istniejącą kategorię !!!", Alert.AlertType.DANGER));
            return false;
        }


    }

    private boolean deleteWithResponse(String categoryURL){
        Optional<CategoryEntity> categoryEntity = categoryRepository.findByUrl(categoryURL);
        if (categoryEntity.isPresent()){
            categoryRepository.delete(categoryEntity.get());
            return true;
        }

        return false;
    }
    public void deleteCategory(String categoryURL){
        if (categoryURL!=null)
        {
            if(deleteWithResponse(categoryURL))
            {
                alertManager.addAlert(new Alert("Poprawnie usunięto kategorię: "+categoryURL, Alert.AlertType.SUCCESS));
            }else
            {
                alertManager.addAlert(new Alert("Nie powiodło się usunięcie kategorii: "+categoryURL, Alert.AlertType.DANGER));
            }
        }
        else
        {
            alertManager.addAlert(new Alert("Nie sprecyzowano ID kategorii do usunięcia !!!", Alert.AlertType.WARNING));
        }
    }

    public boolean addCategory(CategoryCUForm categoryCUForm) {

        if(categoryRepository.findByName(categoryCUForm.getCategoryName()).isEmpty())
        {

                categoryRepository.save(new CategoryEntity(categoryCUForm));
                alertManager.addAlert(new Alert("Poprawnie dodano kategorię "+categoryCUForm.getCategoryName(), Alert.AlertType.SUCCESS));


        }
        else
        {
            alertManager.addAlert(new Alert("Kategoria o podanej nazwie już istnieje !!! ", Alert.AlertType.WARNING));
            return false;
        }



        return true;

        //return this.categoryRepository.save(new CategoryEntity(categoryCUForm))!=null;
    }
}
