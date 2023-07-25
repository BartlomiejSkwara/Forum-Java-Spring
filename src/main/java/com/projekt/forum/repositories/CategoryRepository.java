package com.projekt.forum.repositories;

import com.projekt.forum.entity.Category;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;


public interface CategoryRepository extends ListCrudRepository<Category,String> {
    List<Category> findAll();
}
