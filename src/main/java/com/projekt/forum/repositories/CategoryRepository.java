package com.projekt.forum.repositories;

import com.projekt.forum.entity.CategoryEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends ListCrudRepository<CategoryEntity,String> {
    List<CategoryEntity> findAll();
    Optional<CategoryEntity> findByName(String name);
    Optional<CategoryEntity> findByIdcategory(String idcategory);



}
