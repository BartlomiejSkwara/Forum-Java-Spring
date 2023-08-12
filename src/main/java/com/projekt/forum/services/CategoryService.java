package com.projekt.forum.services;

import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.dataTypes.forms.CategoryCUForm;
import com.projekt.forum.entity.CategoryEntity;
import com.projekt.forum.repositories.CategoryRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    public boolean editCategory(CategoryCUForm categoryCUForm, String categoryID){
        CategoryEntity categoryEntity = new CategoryEntity(categoryCUForm);
        Optional<CategoryEntity> existingEntity = categoryRepository.findById(categoryEntity.getIdcategory());

        if (categoryEntity.getIdcategory().equals(categoryID)){
            if (existingEntity.isPresent()){
//                existingEntity.get().se
//                entityManager.merge()
                alertManager.addAlert(new Alert("Edycja kategorii " + categoryID + " zakończona sukcesem", Alert.AlertType.SUCCESS));
                return true;
            }
            else {
                alertManager.addAlert(new Alert("Próbowałeś zmienić nie istniejącą kategorię !!!", Alert.AlertType.DANGER));
                return false;
            }
        }
        else {
            if (existingEntity.isPresent()){
                existingEntity = categoryRepository.findById(categoryID);
                if (existingEntity.isPresent()){
                    //zapis nadpis
                    alertManager.addAlert(new Alert("Edycja kategorii " + categoryID + " zakończona sukcesem", Alert.AlertType.SUCCESS));
                    return true;
                }
                else{
                    alertManager.addAlert(new Alert("Próbowałeś zmienić nie istniejącą kategorię !!!", Alert.AlertType.DANGER));
                    return false;
                }
            }
            else {
                alertManager.addAlert(new Alert("Istnieje już inna kategoria o takiej nazwie", Alert.AlertType.WARNING));
                return false;
            }
        }

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
