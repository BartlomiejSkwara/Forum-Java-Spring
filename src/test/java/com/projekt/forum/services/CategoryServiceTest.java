package com.projekt.forum.services;


import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.dataTypes.forms.CategoryCUForm;
import com.projekt.forum.entity.CategoryEntity;
import com.projekt.forum.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {


    private TestRestTemplate testRestTemplate;
    private WebMvcTest webMvcTest;
    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private AlertManager alertManager;

    private final String categoryURL = "muzyka-i-inne";
    private final Integer categoryID = 333;
    private final CategoryEntity categoryEntity = new CategoryEntity(333,"Muzyka i Inne","tutaj mowa o muzyce","muzyka-i-inne");
    private final CategoryCUForm categoryCUFormEdit = new CategoryCUForm(333,"Muzyka", "tutaj mowa o muzyce");
    private final CategoryEntity categoryEntityDifferentIdSameName = new CategoryEntity(331,"Muzyka i Inne","tutaj mowa o muzyce","muzyka-i-inne");

    private final CategoryCUForm categoryCUFormAdd = new CategoryCUForm(null,"Muzyka", "tutaj mowa o muzyce");

//    @BeforeEach
//    void setup(){
//        categoryRepository = mock(CategoryRepository.class);
//        alertManager = mock(AlertManager.class);
//        categoryService = new CategoryService(categoryRepository,alertManager);
//    }

    @Test()
    public void deleteCategoryParamNull() {
        categoryService.deleteCategory(null);
        verify(alertManager).addAlert(new Alert("Nie sprecyzowano ID kategorii do usunięcia !!!", Alert.AlertType.WARNING));
    }


    @Test()
    public void deleteCategory_WhenNotFoundInDB() {
        when(categoryRepository.findById(categoryID)).thenReturn(Optional.empty());

        categoryService.deleteCategory(categoryID);
        verify(alertManager).addAlert(new Alert("Usunięcie kategorii nie powiodło się !!! ", Alert.AlertType.DANGER));
    }

    @Test
    public void deleteCategory_WhenFoundInDB(){
        when(categoryRepository.findById(categoryID)).thenReturn(Optional.of(categoryEntity));

        categoryService.deleteCategory(categoryID);
        verify(alertManager).addAlert(new Alert("Poprawnie usunięto kategorię: "+categoryEntity.getName(), Alert.AlertType.SUCCESS));
    }


    @Test()
    public void editCategory_WhenNotFoundInDB() {
        when(categoryRepository.findByName(categoryCUFormEdit.getCategoryName())).thenReturn(Optional.of(categoryEntity));
        when(categoryRepository.findById(categoryCUFormEdit.getCategoryID())).thenReturn(Optional.empty());
        Assertions.assertFalse(categoryService.editCategory(categoryCUFormEdit));
        verify(alertManager).addAlert(new Alert("Próbowałeś zmienić nie istniejącą kategorię !!!", Alert.AlertType.DANGER));

    }

    @Test
    public void editCategory_Success(){
        when(categoryRepository.findByName(categoryCUFormEdit.getCategoryName())).thenReturn(Optional.of(categoryEntity));
        when(categoryRepository.findById(categoryCUFormEdit.getCategoryID())).thenReturn(Optional.of(categoryEntity));
        Assertions.assertTrue(categoryService.editCategory(categoryCUFormEdit));
        verify(alertManager).addAlert(new Alert("Edycja kategorii " + categoryCUFormEdit.getCategoryName() + " zakończona sukcesem", Alert.AlertType.SUCCESS));

    }
    @Test
    public void editCategory_WhenNameIsADuplicate(){
        when(categoryRepository.findByName(categoryCUFormEdit.getCategoryName())).thenReturn(Optional.of(categoryEntityDifferentIdSameName));
        Assertions.assertFalse(categoryService.editCategory(categoryCUFormEdit));
        verify(alertManager).addAlert(new Alert("Istnieje już kategoria o takiej nazwie !!!", Alert.AlertType.WARNING));


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
