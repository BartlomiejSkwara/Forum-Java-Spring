package com.projekt.forum.services;


import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.dataTypes.forms.CategoryCUForm;
import com.projekt.forum.entity.CategoryEntity;
import com.projekt.forum.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Optional;

import static org.mockito.Mockito.*;


public class CategoryServiceTest {


    private TestRestTemplate testRestTemplate;
    private WebMvcTest webMvcTest;
    private CategoryService categoryService;
    private CategoryRepository categoryRepository;
    private AlertManager alertManager;

    private final String categoryURL = "muzyka-i-inne";
    private final CategoryEntity categoryEntity = new CategoryEntity("Muzyka i Inne","tutaj mowa o muzyce");
    private final CategoryCUForm categoryCUFormEdit = new CategoryCUForm(333,"Muzyka", "tutaj mowa o muzyce");
    private final CategoryCUForm categoryCUFormAdd = new CategoryCUForm(null,"Muzyka", "tutaj mowa o muzyce");

    @BeforeEach
    void setup(){
        categoryRepository = mock(CategoryRepository.class);
        alertManager = mock(AlertManager.class);
        categoryService = new CategoryService(categoryRepository,alertManager);
    }

    @Test()
    public void deleteCategoryParamNull() {
        categoryService.deleteCategory(null);
        verify(alertManager).addAlert(new Alert("Nie sprecyzowano ID kategorii do usunięcia !!!", Alert.AlertType.WARNING));
    }


    @Test()
    public void deleteCategory_WhenNotFoundInDB() {
        when(categoryRepository.findByUrl(categoryURL)).thenReturn(Optional.empty());

        categoryService.deleteCategory(categoryURL);
        verify(alertManager).addAlert(new Alert("Nie powiodło się usunięcie kategorii: "+categoryURL, Alert.AlertType.DANGER));
    }

    @Test
    public void deleteCategory_WhenFoundInDB(){
        when(categoryRepository.findByUrl(categoryURL)).thenReturn(Optional.of(categoryEntity));

        categoryService.deleteCategory(categoryURL);
        verify(alertManager).addAlert(new Alert("Poprawnie usunięto kategorię: "+categoryURL, Alert.AlertType.SUCCESS));
    }


    @Test()
    public void editCategory_WhenNotFoundInDB() {
        when(categoryRepository.findById(categoryCUFormEdit.getCategoryID())).thenReturn(Optional.empty());
        Assertions.assertFalse(categoryService.editCategory(categoryCUFormEdit));
        verify(alertManager).addAlert(new Alert("Próbowałeś zmienić nie istniejącą kategorię !!!", Alert.AlertType.DANGER));

    }

    @Test
    public void editCategory_WhenFoundInDB(){
        when(categoryRepository.findById(categoryCUFormEdit.getCategoryID())).thenReturn(Optional.of(categoryEntity));
        Assertions.assertTrue(categoryService.editCategory(categoryCUFormEdit));
        verify(alertManager).addAlert(new Alert("Edycja kategorii " + categoryCUFormEdit.getCategoryID() + " zakończona sukcesem", Alert.AlertType.SUCCESS));
    }


    @Test()
    public void addCategory_WithNameThatAlreadyExists() {
        when(categoryRepository.findByName(categoryCUFormAdd.getCategoryName())).thenReturn(Optional.of(categoryEntity));
        Assertions.assertFalse(categoryService.addCategory(categoryCUFormAdd));
        verify(alertManager).addAlert(new Alert("Kategoria o podanej nazwie już istnieje !!! ", Alert.AlertType.WARNING));
    }

    @Test()
    public void addCategory_WithUnusedName() {
        when(categoryRepository.findByName(categoryCUFormAdd.getCategoryName())).thenReturn(Optional.empty());
        Assertions.assertTrue(categoryService.addCategory(categoryCUFormAdd));
        verify(alertManager).addAlert(new Alert("Poprawnie dodano kategorię "+categoryCUFormAdd.getCategoryName(), Alert.AlertType.SUCCESS));
    }

}
