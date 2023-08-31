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
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = CategoryCRUDController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CategoryCRUDControllerTest {

    @MockBean()
    private CategoryService categoryService;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean()
    private AlertManager alertManager;
    @MockBean()
    private ValidationUtility validationUtility;


    @Autowired()
    private MockMvc mockMvc;
    final private String nameParamName = "categoryName";
    final private String descParamName = "categoryDescription";
    final private String idParamName = "categoryID";
    final private String testUrlID = "muzyka";
    final private String nameValue = "muzyka";
    final private String descValue ="tutaj mowa o muzyce";
    final private String testID = "5";


    @Test
    void addCategory_get() throws Exception{

        mockMvc.perform(get("/addCategory").with(csrf()))
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='"+nameParamName+"']").exists())
                .andExpect(MockMvcResultMatchers.xpath("//textarea[@name='"+descParamName+"']").exists())
                .andExpect(MockMvcResultMatchers.view().name("CategoryCreation"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void addCategory_post_SUCCESS() throws Exception {

        CategoryCUForm categoryCUForm = new CategoryCUForm(null,nameValue,descValue);


        MockHttpServletRequestBuilder message = post("/addCategory").with(csrf())
                .param(nameParamName, nameValue)
                .param(descParamName, descValue);

        when(validationUtility.ConvertValidationErrors(ArgumentMatchers.any(BindingResult.class),ArgumentMatchers.notNull(AlertManager.class))).thenReturn(true);
        when(categoryService.addCategory(ArgumentMatchers.any(CategoryCUForm.class))).thenReturn(true);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists(RequestUtility.AjaxInsertParam))
                .andExpect(MockMvcResultMatchers.header().string(RequestUtility.AjaxInsertParam,RequestUtility.OperationInsert))
                .andExpect(MockMvcResultMatchers.model().attributeExists("atr_alertManager"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("atr_previousForm"))
                .andExpect(MockMvcResultMatchers.view().name("CategoryCreation :: content"))

                ;


    }


    @Test
    void addCategory_post_FAIL_AlreadyExists() throws Exception {

        CategoryCUForm categoryCUForm = new CategoryCUForm(null,nameValue,descValue);


        MockHttpServletRequestBuilder message = post("/addCategory").with(csrf())
                .param(nameParamName, nameValue)
                .param(descParamName, descValue);

        when(validationUtility.ConvertValidationErrors(ArgumentMatchers.any(BindingResult.class),ArgumentMatchers.any(AlertManager.class))).thenReturn(true);
        when(categoryService.addCategory(any(CategoryCUForm.class))).thenReturn(false);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.header().exists(RequestUtility.AjaxInsertParam))
                .andExpect(MockMvcResultMatchers.header().string(RequestUtility.AjaxInsertParam,RequestUtility.OperationInsert))
                .andExpect(MockMvcResultMatchers.model().attributeExists("atr_alertManager"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("atr_previousForm"))
                .andExpect(MockMvcResultMatchers.view().name("CategoryCreation :: content"))
        ;


    }

    @Test
    void addCategory_post_FAIL_ValidationError() throws Exception {

        CategoryCUForm categoryCUForm = new CategoryCUForm(null,nameValue,descValue);


        MockHttpServletRequestBuilder message = post("/addCategory").with(csrf())
                .param(nameParamName, nameValue)
                .param(descParamName, descValue);

        when(validationUtility.ConvertValidationErrors(ArgumentMatchers.any(BindingResult.class),ArgumentMatchers.any(AlertManager.class))).thenReturn(false);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.header().exists(RequestUtility.AjaxInsertParam))
                .andExpect(MockMvcResultMatchers.header().string(RequestUtility.AjaxInsertParam,RequestUtility.OperationInsert))
                .andExpect(MockMvcResultMatchers.model().attributeExists("atr_alertManager"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("atr_previousForm"))
                .andExpect(MockMvcResultMatchers.view().name("CategoryCreation :: content"))
        ;


    }

    @Test
    void editCategory_get_EmptyUrl() throws Exception{
        mockMvc.perform(get("/editCategory").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
        verify(alertManager).addAlert(new Alert("Nie wybrano w poprawny sposób kategorii do edycji !!!", Alert.AlertType.WARNING));

    }

    @Test
    void editCategory_get_WrongId() throws Exception{
        when(categoryRepository.findById(Integer.getInteger(testID))).thenReturn(Optional.empty());
        mockMvc.perform(get("/editCategory/"+ testUrlID+"?id="+testID).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
        verify(alertManager).addAlert(new Alert("Nie można edytować nie istniejącej kategorii !!!", Alert.AlertType.WARNING));

    }

    @Test
    void editCategory_get_CategoryFound() throws Exception{
        CategoryEntity testEntity = new CategoryEntity(testUrlID,"opis");
        testEntity.setCategoryID(2);
        when(categoryRepository.findById(testEntity.getCategoryID())).thenReturn(Optional.of(testEntity));
        mockMvc.perform(get("/editCategory/"+testUrlID+"?id="+testEntity.getCategoryID()).with(csrf()))
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='"+nameParamName+"']").exists())
                .andExpect(MockMvcResultMatchers.xpath("//textarea[@name='"+descParamName+"']").exists())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='"+idParamName+"']").exists())
                .andExpect(MockMvcResultMatchers.model().attributeExists("atr_title"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("atr_previousForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("atr_editedCategoryURL"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("CategoryEditing"));
    }


    @Test
    void editCategory_post_NoID() throws Exception{

        MockHttpServletRequestBuilder message = post("/editCategory")
                .with(csrf())
                .param(nameParamName,nameValue)
                .param(descParamName,descValue)
                ;


        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.view().name("Blank"))
                .andExpect(MockMvcResultMatchers.header().exists(RequestUtility.AjaxInsertParam))
                .andExpect(MockMvcResultMatchers.header().string(RequestUtility.AjaxInsertParam,RequestUtility.OperationReplace))
                .andExpect(MockMvcResultMatchers.header().exists(RequestUtility.AjaxNewPageUrlParamName))

        ;


        verify(alertManager).addAlert(new Alert("Nie sprecyzowano kategorii do edycji !!!", Alert.AlertType.WARNING));


    }

    @Test
    void editCategory_post_WrongParams() throws Exception{

        MockHttpServletRequestBuilder message = post("/editCategory/"+testUrlID)
                .with(csrf())
                .param(nameParamName,nameValue)
                .param(descParamName,descValue)
                .param(idParamName,testID);
        when(validationUtility.ConvertValidationErrors(ArgumentMatchers.any(BindingResult.class),ArgumentMatchers.any(AlertManager.class))).thenReturn(false);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.view().name("CategoryEditing :: content"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("atr_editedCategoryURL"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("atr_title"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("atr_previousForm"))
                .andExpect(MockMvcResultMatchers.header().exists(RequestUtility.AjaxInsertParam))
                .andExpect(MockMvcResultMatchers.header().string(RequestUtility.AjaxInsertParam,RequestUtility.OperationInsert))


        ;


    }
    @Test
    void editCategory_post_SUCCESS() throws Exception {

        //CategoryCUForm categoryCUForm = new CategoryCUForm(null,"muzyka","blahblahblah");


        MockHttpServletRequestBuilder message = post("/editCategory/"+testUrlID)
                .with(csrf())
                .param(nameParamName,nameValue)
                .param(descParamName,descValue)
                .param(idParamName,testID);

        when(validationUtility.ConvertValidationErrors(ArgumentMatchers.any(BindingResult.class),ArgumentMatchers.any(AlertManager.class))).thenReturn(true);
        when(categoryService.editCategory(ArgumentMatchers.any(CategoryCUForm.class))).thenReturn(true);

        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("Blank"))
                .andExpect(MockMvcResultMatchers.header().exists(RequestUtility.AjaxInsertParam))
                .andExpect(MockMvcResultMatchers.header().string(RequestUtility.AjaxInsertParam,RequestUtility.OperationReplace))
                .andExpect(MockMvcResultMatchers.header().exists(RequestUtility.AjaxNewPageUrlParamName))
        ;


    }

    @Test
    void deleteCategory_Redirection() throws Exception{

        mockMvc.perform(get("/deleteCategory/"+testUrlID).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"))
              ;
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void category_smokeTest(){
        Assertions.assertNotNull(this.mockMvc);
        Assertions.assertNotNull(this.alertManager);
        Assertions.assertNotNull(this.categoryRepository);
        Assertions.assertNotNull(this.categoryService);
        Assertions.assertNotNull(this.validationUtility);
    }
}