package com.projekt.forum.repositories;

import com.projekt.forum.entity.CategoryEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends ListCrudRepository<CategoryEntity,Integer> {
    List<CategoryEntity> findAll();
    Optional<CategoryEntity> findByName(String name);
    Optional<CategoryEntity> findByUrl(String url);


}
