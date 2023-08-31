package com.projekt.forum.repositories;

import com.projekt.forum.entity.ThreadEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ThreadRepository extends ListCrudRepository<ThreadEntity,Integer> {
    List<ThreadEntity> findAllByCategoryUrl(@Param("CategoryUrl") String categoryUrl);

}
