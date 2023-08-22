package com.projekt.forum.repositories;

import com.projekt.forum.entity.CategoryEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@DataJpaTest()
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional()
public class CategoryRepositoryTests {

    private  final  CategoryEntity categoryEntity1 = new CategoryEntity("muzyczka","opis muzyczki");
    private  final  CategoryEntity categoryEntity2 = new CategoryEntity("filmy","filmy itd");


    @Autowired CategoryRepository categoryRepository;

    @Autowired
    public CategoryRepositoryTests(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Test
    public void saveAll_returnSavedCategories(){

        CategoryEntity savedEntity = categoryRepository.save(categoryEntity1);

        Assertions.assertNotNull(savedEntity);
        Assertions.assertTrue(savedEntity.getCategoryID()>0);
    }

    @Test
    public void getAll_ReturnMultipleCategories(){

        categoryRepository.save(categoryEntity1);
        categoryRepository.save(categoryEntity2);

        List<CategoryEntity> results = categoryRepository.findAll();
        Assertions.assertNotNull(results);
        Assertions.assertEquals(2, results.size());

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getByID_ReturnsCategoryWithSpecificID(){
        categoryRepository.save(categoryEntity1);
        categoryRepository.save(categoryEntity2);

        Optional<CategoryEntity> results = categoryRepository.findById(2);
        Assertions.assertTrue(results.isPresent());

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void updating_ReturnsUpdatedCategoryWithNewName(){
        categoryRepository.save(categoryEntity1);
        categoryRepository.save(categoryEntity2);
        String newDesc = "zmieniona deskrypcja";
        String newName = "Kategoria Bruh";

        Optional<CategoryEntity> found = categoryRepository.findById(2);
        Assertions.assertTrue(found.isPresent());
        found.get().setDescription(newDesc);
        found.get().setName("Kategoria Bruh");

        CategoryEntity saved = categoryRepository.save(found.get());
        Assertions.assertNotNull(saved);
        Assertions.assertTrue(saved.getCategoryID()>0);
        Assertions.assertEquals(newName,saved.getName());
        Assertions.assertEquals(newDesc,saved.getDescription());
        Assertions.assertEquals("kategoria-bruh",saved.getUrl());

    }



    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void deleting_ThenCheckingIfItIsGone(){
        categoryRepository.save(categoryEntity1);

        categoryRepository.deleteById(1);
        Optional<CategoryEntity> found = categoryRepository.findById(1);
        Assertions.assertFalse(found.isPresent());

    }

}
