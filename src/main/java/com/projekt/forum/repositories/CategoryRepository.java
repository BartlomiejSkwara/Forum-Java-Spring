package com.projekt.forum.repositories;

import com.projekt.forum.entity.CategoryEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;


public interface CategoryRepository extends ListCrudRepository<CategoryEntity,String> {
    List<CategoryEntity> findAll();


}
